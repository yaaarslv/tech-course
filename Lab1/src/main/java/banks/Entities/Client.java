package banks.Entities;

import banks.Models.*;
import banks.Tools.*;
import banks.NotifyMethods.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The Client class represents a bank client. It contains the client's personal information, such as their full name, address, passport data, phone number, and bank account information
 */
public class Client {
    private static final int FULL_NAME_COMPONENT_COUNT = 3;
    private final String fullName;
    private final String firstName;
    private final String middleName;
    private final String lastName;
    private Address address;
    private Passport passport;
    private PhoneNumber phoneNumber;
    private Notificator notificator;
    private Boolean subscribedToNotifications;
    private final List<BankAccount> bankAccounts;

    /**
     * Constructs a new client with the specified personal information
     * @param name the client's full name
     * @param address the client's address
     * @param passport the client's passport data
     * @param phoneNumber the client's phone number
     * @throws ClientException if the name is empty or incomplete
     */
    public Client(String name, Address address, Passport passport, PhoneNumber phoneNumber) throws ClientException {
        if (name.isEmpty()) {
            throw ClientException.clientNameIsNullException();
        }

        var fullname = name.split(" ");
        if (fullname.length < FULL_NAME_COMPONENT_COUNT) {
            throw ClientException.nameIsNotFullException();
        }

        this.fullName = name;
        this.firstName = name.split(" ")[0];
        this.middleName = name.split(" ")[1];
        this.lastName = name.split(" ")[2];
        bankAccounts = new ArrayList<BankAccount>();
        this.address = address;
        this.passport = passport;
        this.phoneNumber = phoneNumber;
    }

    /**
     * Returns the client's full name
     * @return the client's full name
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Returns the client's first name
     * @return the client's first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Returns the client's middle name
     * @return the client's middle name
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * Returns the client's last name
     * @return the client's last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Returns the client's address
     * @return the client's address
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Sets the client's address
     * @param address the new address to set
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * Returns the client's passport data
     * @return the client's passport data
     */
    public Passport getPassport() {
        return passport;
    }

    /**
     * Sets the client's passport data
     * @param passport the new passport data to set
     */
    public void setPassport(Passport passport) {
        this.passport = passport;
    }

    /**
     * Returns the client's phone number
     * @return the client's phone number
     */
    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the client's phone number
     * @param phoneNumber the client's phone number to set
     */
    public void setPhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Returns the client's notificator
     * @return the client's notificator
     */
    public Notificator getNotificator() {
        return notificator;
    }

    /**
     * Sets the client's notificator
     * @param notificator the client's notificator to set
     */
    public void setNotificator(Notificator notificator) {
        this.notificator = notificator;
    }

    /**
     * Check if the client is subscribed to notifications
     * @return true if the client is subscribed to notifications, false otherwise
     */
    public Boolean getSubscribedToNotifications() {
        return subscribedToNotifications;
    }

    /**
     * Sets whether the client is subscribed to notifications
     * @param subscribedToNotifications true if the client is subscribed to notifications, false otherwise
     */
    public void setSubscribedToNotifications(Boolean subscribedToNotifications) {
        this.subscribedToNotifications = subscribedToNotifications;
    }

    /**
     * Returns an unmodifiable list of the client's bank accounts
     * @return an unmodifiable list of the client's bank accounts
     */
    public List<BankAccount> getBankAccounts() {
        return Collections.unmodifiableList(bankAccounts);
    }

    /**
     * Updates the client's address
     * @param address the new address
     * @throws AddressException if the address is null
     */
    public void updateAddress(Address address) throws AddressException {
        if (address == null){
            throw AddressException.addressIsNullException();
        }

        this.address = address;
        if (checkClientDataFullness()) {
            bankAccounts.forEach(BankAccount::changeDoubtToConfirmed);
        }
    }

    /**
     * Updates the client's passport data
     * @param passport the new passport data
     * @throws PassportException if the passport data is null
     */
    public void UpdatePassportData(Passport passport) throws PassportException {
        if (passport == null) {
            throw PassportException.passportDataIsNullException();
        }

        this.passport = passport;
        if (checkClientDataFullness())
        {
            bankAccounts.forEach(BankAccount::changeDoubtToConfirmed);
        }
    }

    /**
     * Updates the client's phone number
     * @param phoneNumber the new phone number
     */
    public void updatePhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Checks if the client's address and passport data are both non-null
     * @return true if both the address and passport data are non-null, false otherwise
     */
    public Boolean checkClientDataFullness() {
        return !(address == null || passport == null);
    }

    /**
     * Adds a bank account to the list of accounts associated with this client
     * @param bankAccount the bank account to be added
     * @return the bank account that was added
     * @throws BankAccountException if the provided bank account is null or already exists in the list of accounts
     */
    public BankAccount addBankAccount(BankAccount bankAccount) throws BankAccountException {
        if (bankAccount == null) {
            throw BankAccountException.bankAccountIsNullException();
        }

        if (bankAccounts.contains(bankAccount)) {
            throw BankAccountException.bankAccountAlreadyExistsException();
        }

        bankAccounts.add(bankAccount);
        return bankAccount;
    }

    /**
     * Removes a bank account from the list of accounts associated with this client
     * @param bankAccount the bank account to be removed
     * @throws BankAccountException if the provided bank account is null or does not exist in the list of accounts
     */
    public void removeBankAccount(BankAccount bankAccount) throws BankAccountException {
        if (bankAccount == null) {
            throw BankAccountException.bankAccountIsNullException();
        }

        if (!bankAccounts.contains(bankAccount)) {
            throw BankAccountException.bankAccountNotExistsException();
        }

        bankAccounts.remove(bankAccount);
    }

    /**
     * Subscribes this client to receive notifications via the provided notificator for each bank account in their list
     * @param notificator the notificator to subscribe to
     */
    public void subscribeToNotifications(Notificator notificator) {
        this.notificator = notificator;
        subscribedToNotifications = true;
        bankAccounts.forEach(bankAccount -> {
            try {
                bankAccount.getBank().addSubscribedClient(this);
            } catch (ClientException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
