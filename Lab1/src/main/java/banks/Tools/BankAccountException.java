package banks.Tools;

/**
 * BankAccountException is an exception class that can be thrown when working with bank accounts
 */
public class BankAccountException extends Exception {
    /**
     * Constructs a new BankAccountException with the specified detail message
     * @param message the error message
     */
    private BankAccountException(String message) {
        super(message);
    }

    /**
     * Returns a new BankAccountException indicating that a bank account is null
     * @return a BankAccountException indicating that a bank account is null
     */
    public static BankAccountException bankAccountIsNullException() {
        return new BankAccountException("Bank account is null!");
    }

    /**
     * Returns a new BankAccountException indicating that a bank account already exists in a list of bank accounts
     * @return a BankAccountException indicating that a bank account already exists in a list of bank accounts
     */
    public static BankAccountException bankAccountAlreadyExistsException() {
        return new BankAccountException("Bank account already exists in list of bank accounts!");
    }

    /**
     * Returns a new BankAccountException indicating that a bank account doesn't exist in a list of bank accounts
     * @return a BankAccountException indicating that a bank account doesn't exist in a list of bank accounts
     */
    public static BankAccountException bankAccountNotExistsException() {
        return new BankAccountException("Bank account doesn't exist in list of bank accounts!");
    }

    /**
     * Returns a new BankAccountException indicating that an operation limit for a doubtful bank account has been exceeded
     * @return a BankAccountException indicating that an operation limit for a doubtful bank account has been exceeded
     */
    public static BankAccountException operationLimitForDoubtfulExceeded() {
        return new BankAccountException("Operation limit for doubt bank account exceeded!");
    }

    /**
     * Returns a new BankAccountException indicating that an operation limit has been exceeded
     * @return a BankAccountException indicating that an operation limit has been exceeded
     */
    public static BankAccountException operationLimitExceeded() {
        return new BankAccountException("Operation limit exceeded!");
    }

    /**
     * Returns a new BankAccountException indicating that a percent already exists
     * @return a BankAccountException indicating that a percent already exists
     */
    public static BankAccountException percentAlreadyContains() {
        return new BankAccountException("Percent already exists!");
    }

    /**
     * Returns a new BankAccountException indicating that a percent doesn't exist
     * @return a BankAccountException indicating that a percent doesn't exist
     */
    public static BankAccountException percentNotExist() {
        return new BankAccountException("Percent doesn't exist!");
    }
}
