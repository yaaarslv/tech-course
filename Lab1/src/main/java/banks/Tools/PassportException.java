package banks.Tools;

/**
 * Exception related to passport data
 */
public class PassportException extends Exception {
    /**
     * Constructs a new PassportException with the specified detail message
     * @param message the error message
     */
    private PassportException(String message) {
        super(message);
    }

    /**
     * Returns a PassportException indicating that the passport data is null
     * @return a PassportException indicating that the passport data is null
     */
    public static PassportException passportDataIsNullException() {
        return new PassportException("Passport data is null!");
    }

    /**
     * Returns a PassportException indicating that the passport data is not full
     * @return a PassportException indicating that the passport data is not full
     */
    public static PassportException passportDataIsNotFullException() {
        return new PassportException("Passport data isn't full!");
    }
}
