package banks.Entities;

import banks.Models.Address;
import banks.Models.Passport;
import banks.Models.PhoneNumber;
import banks.Tools.ClientException;

/**
 * ClientBuilder is a builder class for creating new instances of the Client class. It provides methods for adding the client's name, address, passport data, and phone number, and a method for creating a new Client objectClientBuilder is a builder class for creating new instances of the Client class. It provides methods for adding the client's name, address, passport data, and phone number, and a method for creating a new Client object.
 */
public class ClientBuilder {
    private final String fullName;

    private Address address;

    private Passport passport;

    private PhoneNumber phoneNumber;

    /**
     * Constructs a new ClientBuilder object with the given client name.
     * @param name the client's full name
     * @throws ClientException if the client name is null or empty
     */
    public ClientBuilder(String name) throws ClientException {
        if (name.isEmpty()) {
            throw ClientException.clientNameIsNullException();
        }

        fullName = name;
    }

    /**
     * Sets the client's address.
     * @param address the client's address
     */
    public void addAddress(Address address) {
        this.address = address;
    }

    /**
     * Sets the client's passport data.
     * @param passport the client's passport data
     */
    public void addPassportData(Passport passport) {
        this.passport = passport;
    }

    /**
     * Sets the client's phone number.
     * @param phoneNumber the client's phone number
     */
    public void addPhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Creates a new instance of the Client class with the client's name, address, passport data, and phone number that have been set using the addAddress(), addPassportData(), and addPhoneNumber() methods.
     * @return a new Client object
     * @throws ClientException if any required client data is missing
     */
    public Client createClient() throws ClientException {
        return new Client(fullName, address, passport, phoneNumber);
    }
}
