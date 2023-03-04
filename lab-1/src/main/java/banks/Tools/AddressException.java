package banks.Tools;

/**
 * The AddressException class is an exception that is thrown when an invalid address is used.
 */
public class AddressException extends Exception {
    /**
     * Constructs a new AddressException object with the specified error message.
     * @param message the error message
     */
    private AddressException(String message) {
        super(message);
    }

    /**
     * Returns a new AddressException object with an error message indicating that the address is null.
     * @return an AddressException with a message indicating that the address is null
     */
    public static AddressException addressIsNullException() {
        return new AddressException("Address is null!");
    }

    /**
     * Returns a new AddressException object with an error message indicating that the address is not complete.
     * @return an AddressException with a message indicating that the address is not complete
     */
    public static AddressException addressIsNotFullException() {
        return new AddressException("Address isn't full!");
    }
}
