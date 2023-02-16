package banks.Entities;

import banks.Models.*;
import banks.Tools.*;
import java.math.BigDecimal;
import java.util.*;

/**
 * This class represents a Deposit Account that implements the BankAccount interface. It is a deposit account that allows its holder to withdraw or transfer an amount of money that exceeds its balance
 */
public class DepositAccount implements BankAccount {
    private static final int DAYS_IN_YEAR = 365;
    private static final int PERCENT_COEFFICIENT = 100;
    private final List<Transaction> transactions;
    private BigDecimal percentBalance;
    private final Client client;
    private final Bank bank;
    private final BigDecimal startMoney;
    private BigDecimal money;
    private final UUID id;
    private Boolean isDoubtful;
    private final Calendar creationDateTime;
    private final Calendar finishDateTime;

    /**
     * Constructs a new Deposit Account
     * @param bank the bank that this account belongs to
     * @param client the client that owns this account
     * @param startMoney the initial amount of money deposited to this account
     * @throws BankException if the given bank is null
     * @throws ClientException if the given client is null
     */
    public DepositAccount(Bank bank, Client client, BigDecimal startMoney) throws BankException, ClientException, TransactionException {
        if (bank == null) {
            throw BankException.bankIsNullException();
        }

        if (client == null){
            throw ClientException.clientIsNullException();
        }

        if (startMoney.compareTo(BigDecimal.valueOf(0)) <= 0) {
            throw TransactionException.invalidMoneyException();
        }

        this.client = client;
        this.bank = bank;
        transactions = new ArrayList<Transaction>();
        id = UUID.randomUUID();
        this.startMoney = startMoney;
        money = startMoney;
        percentBalance = BigDecimal.valueOf(0);
        isDoubtful = false;
        creationDateTime = new GregorianCalendar();
        finishDateTime = new GregorianCalendar();
        finishDateTime.add(Calendar.YEAR, 1);
    }

    /**
     * Returns an unmodifiable list of all transactions of this account
     * @return list of all transactions of this account
     */
    public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(transactions);
    }

    /**
     *
     * Returns the client that owns this account
     * @return the client that owns this account
     */
    public Client getClient() {
        return client;
    }

    /**
     * Returns the bank that this account belongs to
     * @return the bank that this account belongs to
     */
    public Bank getBank() {
        return bank;
    }

    /**
     * Returns the initial amount of money deposited to this account
     * @return the initial amount of money deposited to this account
     */
    public BigDecimal getStartMoney() {
        return startMoney;
    }

    /**
     * Returns the current amount of money in this account
     * @return the current amount of money in this account
     */
    public BigDecimal getMoney() {
        return money;
    }

    /**
     * Sets the current amount of money in this account
     * @param money the current amount of money in this account
     */
    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    /**
     * Returns the unique identifier for the bank account
     * @return the unique identifier for the bank account
     */
    public UUID getId() {
        return id;
    }

    /**
     * Returns a Boolean indicating if the account is marked as doubtful
     * @return true if the account is marked as doubtful, false otherwise
     */
    public Boolean getIsDoubtful() {
        return isDoubtful;
    }

    /**
     * Sets the account status to doubtful or confirmed
     * @param doubtful true to mark the account as doubtful, false to mark it as confirmed
     */
    public void setIsDoubtful(Boolean doubtful) {
        isDoubtful = doubtful;
    }

    /**
     * Returns the date and time the account was created
     * @return the date and time the account was created
     */
    public Calendar getCreationDateTime() {
        return creationDateTime;
    }

    /**
     * Returns the date and time the account will be closed
     * @return the date and time the account will be closed
     */
    public Calendar getFinishDateTime() {
        return finishDateTime;
    }

    /**
     * Adds money to the account
     * @param money the amount of money to add
     * @throws TransactionException if the money value is zero or negative
     */
    public void replenish(BigDecimal money) throws TransactionException {
        if (money.compareTo(BigDecimal.valueOf(0)) <= 0) {
            throw TransactionException.invalidMoneyException();
        }

        this.money = this.money.add(money);
        var newTransaction = new Transaction(new GregorianCalendar().getTime(), id, id, money, BigDecimal.valueOf(0), "replenish");
        transactions.add(newTransaction);
    }

    /**
     * Withdraws money from the account
     * @param money the amount of money to withdraw
     * @throws TransactionException if the money value is zero or negative, or if the account does not have enough money to perform the withdrawal
     * @throws BankAccountException if the withdrawal limit for a doubtful account is exceeded
     */
    public void withdraw(BigDecimal money) throws TransactionException, BankAccountException {
        if (new GregorianCalendar().getTime().before(finishDateTime.getTime())){
            throw TransactionException.timeHasNotExpired();
        }

        if (money.compareTo(BigDecimal.valueOf(0)) <= 0) {
            throw TransactionException.invalidMoneyException();
        }

        if (this.money.compareTo(money) < 0) {
            throw TransactionException.noEnoughMoneyException();
        }

        if (checkAccountLimit(money)) {
            throw BankAccountException.operationLimitForDoubtfulExceeded();
        }

        this.money = this.money.subtract(money);
        var newTransaction = new Transaction(new GregorianCalendar().getTime(), id, id, money, BigDecimal.valueOf(0), "withdraw");
        transactions.add(newTransaction);
    }

    /**
     * Transfers money from this account to another account
     * @param recipient the ID of the recipient account
     * @param money the amount of money to transfer
     * @throws TransactionException if the money value is zero or negative, or if the account does not have enough money to perform the transfer
     * @throws BankAccountException if the transfer limit is exceeded or the withdrawal limit for a doubtful account is exceeded
     */
    public void transfer(UUID recipient, BigDecimal money) throws TransactionException, BankAccountException {
        if (new GregorianCalendar().getTime().before(finishDateTime.getTime())){
            throw TransactionException.timeHasNotExpired();
        }

        if (money.compareTo(BigDecimal.valueOf(0)) <= 0) {
            throw TransactionException.invalidMoneyException();
        }

        if (this.money.compareTo(money) < 0) {
            throw TransactionException.noEnoughMoneyException();
        }

        if (checkAccountLimit(money)) {
            throw BankAccountException.operationLimitForDoubtfulExceeded();
        }

        if (checkTransferLimit(money)) {
            throw BankAccountException.operationLimitExceeded();
        }

        var recipientBankAccount = bank.getClients().stream().flatMap(c -> c.getBankAccounts().stream()).filter(b -> b.getId() == recipient).findFirst().orElse(null);
        withdraw(money);
        recipientBankAccount.replenish(money);
        var newTransaction = new Transaction(new GregorianCalendar().getTime(), id, recipient, money, BigDecimal.valueOf(0), "transfer");
        transactions.add(newTransaction);
    }

    /**
     * Changes a doubtful account to a confirmed account
     */
    public void changeDoubtToConfirmed() {
        isDoubtful = false;
    }

    /**
     * Accrues interest to bank account
     */
    public void accruePercents() {
        var percent = bank.getBankPercents().getDepositPercents().entrySet().stream().filter(p ->  startMoney.compareTo(p.getKey().getKey()) >= 0 && startMoney.compareTo(p.getKey().getValue()) <= 0).findFirst().orElse(null).getValue();
        percentBalance  = percentBalance.add(money.multiply(percent).divide(BigDecimal.valueOf(DAYS_IN_YEAR * PERCENT_COEFFICIENT)));
    }

    /**
     * Adds the accrued interest to account
     */
    public void addPercentsToBalance() {
        money = money.add(percentBalance);
        percentBalance = BigDecimal.valueOf(0);
    }

    /**
     * Returns a string containing information about the account
     * @return a string containing information about the account
     */
    public String printAccountData() {
        return "Money: " + money + ", StartMoney: " + startMoney + ", CreationDateTime: " + creationDateTime + ", FinishDateTime: " + finishDateTime + ", Id: " + id + ", Bank: " + bank.getName();
    }

    /**
     * Checks whether the withdrawal limit for a doubtful account is exceeded
     * @param money the amount of money to withdraw
     * @return true if the limit is exceeded, false otherwise
     */
    private Boolean checkAccountLimit(BigDecimal money) {
        return isDoubtful && money.compareTo(bank.getLimitForDoubtful()) > 0;
    }

    /**
     * Checks whether the transfer limit is exceeded
     * @param money the amount of money to transfer
     * @return true if the limit is exceeded, false otherwise
     */
    private Boolean checkTransferLimit(BigDecimal money) {
        return money.compareTo(bank.getLimit()) > 0;
    }
}
