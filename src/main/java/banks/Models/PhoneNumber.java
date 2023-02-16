package banks.Models;

import banks.Tools.*;

/**
 * The PhoneNumber class represents a phone number. It checks the validity of the phone number during instantiation and throws a PhoneNumberException if the phone number is invalid
 */
public class PhoneNumber {
    private static final int MIN_PHONE_NUMBER_LENGTH = 11;
    private static final int MAX_PHONE_NUMBER_LENGTH = 12;
    private final String phone;

    /**
     * Constructs a PhoneNumber object with the given phone number string. It throws a PhoneNumberException if the phone number string is null or if it is an invalid phone number
     * @param phoneNumber a string representing a phone number
     * @throws PhoneNumberException if the phone number string is null or if it is an invalid phone number
     */
    public PhoneNumber(String phoneNumber) throws PhoneNumberException {
        if (phoneNumber.isEmpty()) {
            throw PhoneNumberException.phoneNumberIsNullException();
        }

        checkPhoneNumber(phoneNumber);
        phone = phoneNumber;
    }

    /**
     * Returns the phone number string of this PhoneNumber object
     * @return the phone number string of this PhoneNumber object
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Checks whether the given phone number is valid or not. It throws a PhoneNumberException if the phone number is invalid
     * @param phoneNumber a string representing a phone number
     * @throws PhoneNumberException if the phone number is invalid
     */
    private void checkPhoneNumber(String phoneNumber) throws PhoneNumberException {
        if ((phoneNumber.toCharArray()[0] == '+' && phoneNumber.length() != MAX_PHONE_NUMBER_LENGTH) ||
        (phoneNumber.toCharArray()[0] == '8' && phoneNumber.length() != MIN_PHONE_NUMBER_LENGTH) ||
        phoneNumber.length() < MIN_PHONE_NUMBER_LENGTH || phoneNumber.length() > MAX_PHONE_NUMBER_LENGTH){
            throw PhoneNumberException.phoneNumberIsInvalidException();
        }
    }
}
