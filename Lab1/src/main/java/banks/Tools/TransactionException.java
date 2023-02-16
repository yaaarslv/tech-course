package banks.Tools;

/**
 * An exception that indicates an error during a transaction
 */
public class TransactionException extends Exception {
    /**
     * Constructs a new TransactionException with the specified message
     * @param message the error message
     */
    private TransactionException(String message) {
        super(message);
    }

    /**
     * Returns a TransactionException indicating that the amount of money for a transaction is invalid
     * @return a TransactionException indicating that the amount of money for a transaction is invalid
     */
    public static TransactionException invalidMoneyException() {
        return new TransactionException("Money is invalid!");
    }

    /**
     * Returns a TransactionException indicating that a transaction is null
     * @return a TransactionException indicating that a transaction is null
     */
    public static TransactionException transactionIsNullException() {
        return new TransactionException("Transaction is null!");
    }

    /**
     * Returns a TransactionException indicating that a transaction has already been cancelled
     * @return a TransactionException indicating that a transaction has already been cancelled
     */
    public static TransactionException transactionIsAlreadyCancelledException() {
        return new TransactionException("Transaction is already cancelled!");
    }

    /**
     * Returns a TransactionException indicating that there is not enough money in the bank account for a transaction
     * @return a TransactionException indicating that there is not enough money in the bank account for a transaction
     */
    public static TransactionException noEnoughMoneyException() {
        return new TransactionException("There is not enough money on bank account!");
    }

    /**
     * Returns a TransactionException indicating that the finish time for a transaction has not yet expired
     * @return a TransactionException indicating that the finish time for a transaction has not yet expired
     */
    public static TransactionException timeHasNotExpired() {
        return new TransactionException("Finish time hasn't expired yet");
    }
}
