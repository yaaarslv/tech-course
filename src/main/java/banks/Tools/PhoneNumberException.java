package banks.Tools;

/**
 * Exception for when a phone number is invalid or null
 */
public class PhoneNumberException extends Exception {
    /**
     * Constructs a PhoneNumberException with the given message
     * @param message the error message
     */
    private PhoneNumberException(String message) {
        super(message);
    }

    /**
     * Returns a PhoneNumberException with a message indicating that the phone number is null
     * @return a PhoneNumberException with a message indicating that the phone number is null
     */
    public static PhoneNumberException phoneNumberIsNullException() {
        return new PhoneNumberException("Phone number is null!");
    }

    /**
     * Returns a PhoneNumberException with a message indicating that the phone number is invalid
     * @return a PhoneNumberException with a message indicating that the phone number is invalid
     */
    public static PhoneNumberException phoneNumberIsInvalidException() {
        return new PhoneNumberException("Phone number is invalid!");
    }
}
