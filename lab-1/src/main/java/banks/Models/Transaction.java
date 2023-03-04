package banks.Models;

import banks.Tools.TransactionException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

/**
 * Represents a transaction between two accounts. Contains information about the time, sender, recipient, amount of money, commission, and whether the transaction is cancelled or not.
 */
public class Transaction {
    Date transactionTime;

    UUID sender;

    UUID recipient;

    BigDecimal money;

    BigDecimal commission;

    Boolean isCancelled;

    String transactionType;

    /**
     * Creates a new transaction with the specified parameters.
     * @param transactionTime the time when the transaction was executed
     * @param sender the UUID of the account that sent the money
     * @param recipient the UUID of the account that received the money
     * @param money the amount of money that was sent
     * @param commission the commission that was charged for the transaction
     * @param transactionType the type of transaction (e.g. "debit", "credit", "transfer")
     * @throws TransactionException if the money amount is less than or equal to zero or the commission amount is less than zero
     */
    public Transaction(Date transactionTime, UUID sender, UUID recipient, BigDecimal money, BigDecimal commission, String transactionType) throws TransactionException {
        if (money.compareTo(BigDecimal.valueOf(0)) <= 0 || commission.compareTo(BigDecimal.valueOf(0)) < 0) {
            throw TransactionException.invalidMoneyException();
        }

        this.transactionTime = transactionTime;
        this.sender = sender;
        this.recipient = recipient;
        this.money = money;
        this.commission = commission;
        isCancelled = false;
        this.transactionType = transactionType;
    }

    /**
     * Returns the time when the transaction was executed.
     * @return the transaction time
     */
    public Date getTransactionTime() {
        return transactionTime;
    }

    /**
     * Returns the UUID of the account that sent the money.
     * @return the sender UUID
     */
    public UUID getSender() {
        return sender;
    }

    /**
     * Returns the UUID of the account that received the money.
     * @return the recipient UUID
     */
    public UUID getRecipient() {
        return recipient;
    }

    /**
     * Returns the amount of money that was sent.
     * @return the money amount
     */
    public BigDecimal getMoney() {
        return money;
    }

    /**
     * Returns the commission that was charged for the transaction.
     * @return the commission amount
     */
    public BigDecimal getCommission() {
        return commission;
    }

    /**
     * Returns whether the transaction is cancelled or not.
     * @return true if the transaction is cancelled, false otherwise
     */
    public Boolean getIsCancelled() {
        return isCancelled;
    }

    /**
     * Returns the type of transaction ("debit", "credit" or "transfer").
     * @return the transaction type
     */
    public String getTransactionType() {
        return transactionType;
    }

    /**
     * Changes the value of the isCancelled flag. If the flag was true, it will become false, and vice versa.
     */
    public void changeIsCancelledFlag() {
        isCancelled = !isCancelled;
    }
}
