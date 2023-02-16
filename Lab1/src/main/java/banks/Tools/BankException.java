package banks.Tools;

/**
 * Custom exception for errors related to banks
 */
public class BankException extends Exception {
    /**
     * Constructs a new BankException with the specified error message
     * @param message the error message
     */
    private BankException(String message) {
        super(message);
    }

    /**
     * Returns a BankException with an error message indicating that the bank is null
     * @return a BankException with an error message indicating that the bank is null
     */
    public static BankException bankIsNullException() {
        return new BankException("Bank is null!");
    }

    /**
     * Returns a BankException with an error message indicating that the bank name is null
     * @return a BankException with an error message indicating that the bank name is null
     */
    public static BankException bankNameIsNullException() {
        return new BankException("Bank name is null!");
    }

    /**
     * Returns a BankException with an error message indicating that the bank has already been created
     * @return a BankException with an error message indicating that the bank has already been created
     */
    public static BankException bankAlreadyCreatedException() {
        return new BankException("Bank is already created!");
    }
}
