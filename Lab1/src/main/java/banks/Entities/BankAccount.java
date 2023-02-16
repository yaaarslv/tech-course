package banks.Entities;

import banks.Models.*;
import banks.Tools.*;
import java.math.BigDecimal;
import java.util.*;

/**
 * The BankAccount interface represents a bank account with basic functionality such as deposit, withdrawal, and transfer
 */
public interface BankAccount {

    /**
     * Returns a list of all transactions associated with this account
     * @return a list of Transaction objects
     */
    List<Transaction> getTransactions();

    /**
     * Returns the unique identifier of this bank account
     * @return a UUID object representing the ID of this bank account
     */
    UUID getId();

    /**
     * Returns the Bank object associated with this account
     * @return a Bank object representing the bank associated with this account
     */
    Bank getBank();

    /**
     * Returns the initial balance of this bank account
     * @return a BigDecimal object representing the initial balance of this account
     */
    BigDecimal getStartMoney();

    /**
     * Returns the current balance of this bank account
     * @return a BigDecimal object representing the current balance of this account
     */
    BigDecimal getMoney();

    /**
     * Returns a boolean indicating whether this bank account is doubtful or not
     * @return a Boolean object representing whether this account is doubtful or not
     */
    Boolean getIsDoubtful();

    /**
     * Deposits the specified amount of money into this bank account
     * @param money a BigDecimal object representing the amount of money to be deposited
     * @throws TransactionException if the transaction cannot be completed for any reason
     */
    void replenish(BigDecimal money) throws TransactionException;

    /**
     * Withdraws the specified amount of money from this bank account
     * @param money a BigDecimal object representing the amount of money to be withdrawn
     * @throws TransactionException if the transaction cannot be completed for any reason
     * @throws BankAccountException if there are not enough funds in the account to complete the transaction
     */
    void withdraw(BigDecimal money) throws TransactionException, BankAccountException;

    /**
     * Transfers the specified amount of money from this bank account to the account associated with the specified recipient ID
     * @param recipient a UUID object representing the recipient of the transfer
     * @param money a BigDecimal object representing the amount of money to be transferred
     * @throws TransactionException if the transaction cannot be completed for any reason
     * @throws BankAccountException if there are not enough funds in the account to complete the transaction
     */
    void transfer(UUID recipient, BigDecimal money) throws TransactionException, BankAccountException;

    /**
     * Changes the status of this bank account from doubtful to confirmed
     */
    void changeDoubtToConfirmed();

    /**
     * Returns a String representation of this bank account, including the ID, bank name, start balance, current balance, and doubtful status
     * @return a String representing the account data
     */
    String printAccountData();
}
