package banks.Entities;

import banks.Models.BankPercents;
import banks.Models.Transaction;
import banks.Tools.BankAccountException;
import banks.Tools.BankException;
import banks.Tools.ClientException;
import banks.Tools.TransactionException;
import java.math.BigDecimal;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Represents a Bank object that stores a collection of clients, their bank accounts, and the bank's settings such as the credit limit, transaction limit, and commission percentage.
 */
public class Bank {
    private final String name;

    private final UUID id;

    private BigDecimal money;

    private BigDecimal creditLimit;

    private BigDecimal limitForDoubtful;

    private BigDecimal limit;

    private final List<Client> clients;

    private final List<Client> subscribedToNotify;

    private final List<DebitAccount> debitAccounts;

    private final List<DepositAccount> depositAccounts;

    private final List<CreditAccount> creditAccounts;

    private final BankPercents bankPercents;

    /**
     * Creates a Bank object with the specified name and initializes its fields.
     * @param name the name of the Bank to be created
     * @throws BankException if the specified name is empty
     */
    public Bank(String name) throws BankException {
        if (name.isEmpty()) {
            throw BankException.bankAlreadyCreatedException();
        }

        this.name = name;
        id = UUID.randomUUID();
        clients = new ArrayList<Client>();
        subscribedToNotify = new ArrayList<Client>();
        debitAccounts = new ArrayList<DebitAccount>();
        depositAccounts = new ArrayList<DepositAccount>();
        creditAccounts = new ArrayList<CreditAccount>();
        bankPercents = new BankPercents();
        money = BigDecimal.valueOf(0);
    }

    /**
     * Returns an unmodifiable list of all clients associated with this Bank.
     * @return an unmodifiable list of all clients associated with this Bank
     */
    public List<Client> getClients() {
        return Collections.unmodifiableList(clients);
    }

    /**
     * Returns the name of this Bank.
     * @return the name of this Bank
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the UUID of this Bank.
     * @return the UUID of this Bank
     */
    public UUID getId() {
        return id;
    }

    /**
     * Returns the current amount of money in this Bank.
     * @return the current amount of money in this Bank
     */
    public BigDecimal getMoney() {
        return money;
    }

    /**
     * Sets the current amount of money in this Bank.
     * @param money the new amount of money to be set in this Bank
     */
    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    /**
     * Returns the maximum amount of credit allowed for this Bank.
     * @return the maximum amount of credit allowed for this Bank
     */
    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    /**
     * Sets the maximum amount of credit allowed for this Bank.
     * @param creditLimit the new maximum amount of credit to be set
     * @throws TransactionException if the specified credit limit is less than or equal to 0
     */
    public void setCreditLimit(BigDecimal creditLimit) throws TransactionException {
        if (creditLimit.compareTo(BigDecimal.valueOf(0)) <= 0) {
            throw TransactionException.invalidMoneyException();
        }

        this.creditLimit = creditLimit;
        var subscribed = creditAccounts.stream().filter(creditAccount -> creditAccount.getClient().getSubscribedToNotifications()).map(CreditAccount::getClient).toList();
        notifySubscribers(subscribed, "Credit limit was updated!");
    }

    /**
     * Returns the limit for doubtful transactions.
     * @return the limit for doubtful transactions
     */
    public BigDecimal getLimitForDoubtful() {
        return limitForDoubtful;
    }

    /**
     * Sets the limit for doubtful transactions.
     * @param limitForDoubtful the new limit for doubtful transactions
     * @throws TransactionException if the new limit is not greater than zero
     */
    public void setLimitForDoubtful(BigDecimal limitForDoubtful) throws TransactionException {
        if (limitForDoubtful.compareTo(BigDecimal.valueOf(0)) <= 0) {
            throw TransactionException.invalidMoneyException();
        }

        this.limitForDoubtful = limitForDoubtful;
    }

    /**
     * Returns the global limit for all bank accounts.
     * @return the global limit for all bank accounts
     */
    public BigDecimal getLimit() {
        return limit;
    }

    /**
     * Sets the global limit for all bank accounts.
     * @param limit the new global limit for all bank accounts
     * @throws TransactionException if the new limit is not greater than zero
     */
    public void setLimit(BigDecimal limit) throws TransactionException {
        if (limit.compareTo(BigDecimal.valueOf(0)) <= 0) {
            throw TransactionException.invalidMoneyException();
        }

        this.limit = limit;
        notifySubscribers(subscribedToNotify, "Transfer limit was updated!");
    }

    /**
     * Returns the bank percents object that contains all the percents for the bank.
     * @return the bank percents object that contains all the percents for the bank
     */
    public BankPercents getBankPercents() {
        return bankPercents;
    }

    /**
     * Sets the commission percent for the bank.
     * @param newCommission the new commission percent
     * @throws TransactionException if the new commission percent is not greater than zero
     */
    public void setCommission(BigDecimal newCommission) throws TransactionException {
        if (newCommission.compareTo(BigDecimal.valueOf(0)) <= 0) {
            throw TransactionException.invalidMoneyException();
        }

        bankPercents.setCommission(newCommission);
        var subscribed = creditAccounts.stream().filter(creditAccount -> creditAccount.getClient().getSubscribedToNotifications()).map(CreditAccount::getClient).toList();
        notifySubscribers(subscribed, "Commission was updated!");
    }

    /**
     * Sets the debit percent for the bank.
     * @param newPercent the new debit percent
     * @throws TransactionException if the new debit percent is not greater than zero
     */
    public void setDebitPercent(BigDecimal newPercent) throws TransactionException {
        if (newPercent.compareTo(BigDecimal.valueOf(0)) <= 0) {
            throw TransactionException.invalidMoneyException();
        }

        bankPercents.setDebitPercent(newPercent);
        var subscribed = debitAccounts.stream().filter(debitAccount -> debitAccount.getClient() != null && debitAccount.getClient().getSubscribedToNotifications()).map(DebitAccount::getClient).toList();
        notifySubscribers(subscribed, "Debit percent was updated!");
    }

    /**
     * Sets the deposit percent for a specified range of money values for deposit accounts.
     * @param startDepositMoney the start of the range of deposit money values
     * @param endDepositMoney the end of the range of deposit money values
     * @param newPercent the new deposit percent to be set
     * @throws TransactionException if the newPercent is less than or equal to 0
     * @throws BankAccountException if there is an issue with the bank accounts
     */
    public void setDepositPercent(BigDecimal startDepositMoney, BigDecimal endDepositMoney, BigDecimal newPercent) throws TransactionException, BankAccountException {
        if (newPercent.compareTo(BigDecimal.valueOf(0)) <= 0) {
            throw TransactionException.invalidMoneyException();
        }

        var percentForDepositRange = bankPercents.getDepositPercents().entrySet().stream().filter(p -> p.getKey().getKey().compareTo(startDepositMoney) == 0 && p.getKey().getValue().compareTo(endDepositMoney) == 0).findFirst().orElse(null);
        var newRange = new AbstractMap.SimpleEntry<BigDecimal, BigDecimal>(startDepositMoney, endDepositMoney);
        if (percentForDepositRange.getKey().getKey().compareTo(BigDecimal.valueOf(0)) == 0 && percentForDepositRange.getKey().getValue().compareTo(BigDecimal.valueOf(0)) == 0 && percentForDepositRange.getValue().compareTo(BigDecimal.valueOf(0)) == 0) {
            bankPercents.addDepositPercent(newRange, newPercent);
        } else {
            var depositRange = percentForDepositRange.getKey();
            bankPercents.removeDepositPercent(depositRange);
            bankPercents.addDepositPercent(newRange, newPercent);
        }

        var subscribed = depositAccounts.stream().filter(depositAccount -> depositAccount.getClient() != null && depositAccount.getClient().getSubscribedToNotifications()).map(DepositAccount::getClient).toList();
        notifySubscribers(subscribed, "Deposit percent was updated!");
    }

    /**
     * Adds a new client to the bank.
     * @param client the client to be added
     * @return the added client object
     * @throws ClientException if there is an issue with the client
     */
    public Client addClient(Client client) throws ClientException {
        if (client == null) {
            throw ClientException.clientIsNullException();
        }

        if (clients.contains(client)) {
            throw ClientException.clientAlreadyExistsException();
        }

        clients.add(client);
        return client;
    }

    /**
     * Removes a client from the bank.
     * @param client the client to be removed
     * @throws ClientException if there is an issue with the client
     */
    public void removeClient(Client client) throws ClientException {
        if (client == null) {
            throw ClientException.clientIsNullException();
        }

        if (!clients.contains(client)) {
            throw ClientException.clientNotExistsException();
        }

        clients.remove(client);
    }

    /**
     * Creates a new DebitAccount for a client.
     * @param client the client to create the DebitAccount for
     * @param startMoney the initial amount of money to be deposited
     * @return the created DebitAccount object
     * @throws ClientException if there is an issue with the client
     * @throws TransactionException if the startMoney is less than or equal to 0
     * @throws BankAccountException if there is an issue with the bank accounts
     * @throws BankException if there is an issue with the bank
     */
    public DebitAccount createDebitAccount(Client client, BigDecimal startMoney) throws ClientException, TransactionException, BankAccountException, BankException {
        if (client == null) {
            throw ClientException.clientIsNullException();
        }

        if (startMoney.compareTo(BigDecimal.valueOf(0)) <= 0) {
            throw TransactionException.invalidMoneyException();
        }

        DebitAccount debitAccount = new DebitAccount(this, client, startMoney);
        addClient(client);
        client.addBankAccount(debitAccount);
        debitAccounts.add(debitAccount);
        return debitAccount;
    }

    /**
     * Creates a new DepositAccount for a client.
     * @param client the client to create the DepositAccount for
     * @param startMoney the initial amount of money to be deposited
     * @return the created DepositAccount object
     * @throws ClientException if there is an issue with the client
     * @throws TransactionException if the startMoney is less than or equal to 0
     * @throws BankAccountException if there is an issue with the bank accounts
     * @throws BankException if there is an issue with the bank
     */
    public DepositAccount createDepositAccount(Client client, BigDecimal startMoney) throws ClientException, TransactionException, BankAccountException, BankException {
        if (client == null) {
            throw ClientException.clientIsNullException();
        }

        if (startMoney.compareTo(BigDecimal.valueOf(0)) <= 0) {
            throw TransactionException.invalidMoneyException();
        }

        DepositAccount depositAccount = new DepositAccount(this, client, startMoney);
        addClient(client);
        client.addBankAccount(depositAccount);
        depositAccounts.add(depositAccount);
        return depositAccount;
    }

    /**
     * Creates a credit account for the given client with the specified starting amount of money.
     * @param client the client for whom to create the account
     * @param startMoney the starting amount of money for the account
     * @return the newly created credit account
     * @throws ClientException if the client is null
     * @throws TransactionException if the starting amount of money is not positive
     * @throws BankAccountException if there is an error creating the account
     * @throws BankException if there is an error adding the client to the bank
     */
    public CreditAccount createCreditAccount(Client client, BigDecimal startMoney) throws ClientException, TransactionException, BankAccountException, BankException {
        if (client == null) {
            throw ClientException.clientIsNullException();
        }

        if (startMoney.compareTo(BigDecimal.valueOf(0)) <= 0) {
            throw TransactionException.invalidMoneyException();
        }

        CreditAccount creditAccount = new CreditAccount(this, client, startMoney);
        addClient(client);
        client.addBankAccount(creditAccount);
        creditAccounts.add(creditAccount);
        return creditAccount;
    }

    /**
     * Cancels the specified transaction and returns the money and commission to the sender's account.
     * @param transaction the transaction to cancel
     * @throws TransactionException if the transaction is null or has already been cancelled
     * @throws BankAccountException if there is an error transferring the money or commission between accounts
     */
    public void cancelTransaction(Transaction transaction) throws TransactionException, BankAccountException {
        if (transaction == null) {
            throw TransactionException.transactionIsNullException();
        }

        if (transaction.getIsCancelled()) {
            throw TransactionException.transactionIsAlreadyCancelledException();
        }

        if (transaction.getSender() != transaction.getRecipient()) {
            var senderBankAccount = clients.stream().flatMap(c -> c.getBankAccounts().stream()).filter(b -> b.getId() == transaction.getSender()).findFirst().orElse(null);
            var recipientBankAccount = clients.stream().flatMap(c -> c.getBankAccounts().stream()).filter(b -> b.getId() == transaction.getRecipient()).findFirst().orElse(null);
            senderBankAccount.replenish(transaction.getMoney().add(transaction.getCommission()));
            recipientBankAccount.withdraw(transaction.getMoney().add(transaction.getCommission()));
            transaction.changeIsCancelledFlag();
            var recipientTransaction = recipientBankAccount.getTransactions().stream().filter(t -> t.getTransactionTime() == transaction.getTransactionTime()).findFirst().orElse(null);
            recipientTransaction.changeIsCancelledFlag();
        } else {
            var senderBankAccount = clients.stream().flatMap(c -> c.getBankAccounts().stream()).filter(b -> b.getId() == transaction.getSender()).findFirst().orElse(null);
            if (transaction.getTransactionType().equals("replenish")) {
                senderBankAccount.withdraw(transaction.getMoney().add(transaction.getCommission()));
            } else {
                senderBankAccount.replenish(transaction.getMoney().add(transaction.getCommission()));
            }

            transaction.changeIsCancelledFlag();
        }
    }

    /**
     * Increases the amount of money in the bank by the specified amount.
     * @param money the amount of money to add
     * @throws TransactionException if the amount of money is not positive
     */
    public void incrementMoney(BigDecimal money) throws TransactionException {
        if (money.compareTo(BigDecimal.valueOf(0)) <= 0) {
            throw TransactionException.invalidMoneyException();
        }

        this.money = this.money.add(money);
    }

    /**
     * Decreases the amount of money in the bank by the specified amount.
     * @param money the amount of money to subtract
     * @throws TransactionException if the amount of money is not positive or there is not enough money in the bank
     */
    public void decrementMoney(BigDecimal money) throws TransactionException {
        if (money.compareTo(BigDecimal.valueOf(0)) <= 0) {
            throw TransactionException.invalidMoneyException();
        }

        if (this.money.compareTo(money) < 0) {
            throw TransactionException.noEnoughMoneyException();
        }

        this.money = this.money.subtract(money);
    }

    /**
     * Adds the specified client to the list of clients who are subscribed to receive notifications from the bank.
     * @param client the client to add
     * @return the added client
     * @throws ClientException if the client is null
     */
    public Client addSubscribedClient(Client client) throws ClientException {
        if (client == null) {
            throw ClientException.clientIsNullException();
        }

        if (!subscribedToNotify.contains(client)) {
            subscribedToNotify.add(client);
        }

        return client;
    }

    /**
     * Notifies the specified clients with the given message using their notificators.
     * @param clients the clients to notify
     * @param message the message to send
     */
    public void notifySubscribers(List<Client> clients, String message) {
        clients.forEach(client -> client.getNotificator().notifySubscriber(message));
    }

    /**
     * Accrues interest to all debit and deposit accounts.
     */
    public void accruePercentsToAccounts() {
        debitAccounts.forEach(DebitAccount::accruePercents);
        depositAccounts.forEach(DepositAccount::accruePercents);
    }

    /**
     * Adds the accrued interest to all debit and deposit accounts.
     */
    public void addPercentsToAccountsBalance() {
        debitAccounts.forEach(DebitAccount::addPercentsToBalance);
        depositAccounts.forEach(DepositAccount::addPercentsToBalance);
    }

    /**
     * Notifies the bank's subscribers that interest has been added to their accounts.
     */
    public void notifyBank() {
        addPercentsToAccountsBalance();
    }

}
