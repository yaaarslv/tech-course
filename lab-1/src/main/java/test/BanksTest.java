package test;

import banks.Entities.Bank;
import banks.Entities.CentralBank;
import banks.Entities.Client;
import banks.Entities.ClientBuilder;
import banks.Entities.CreditAccount;
import banks.Entities.DebitAccount;
import banks.Models.Address;
import banks.Models.Passport;
import banks.Models.PhoneNumber;
import java.math.BigDecimal;
import java.text.ParseException;
import banks.NotifyMethods.SimpleNotification;
import banks.Tools.AddressException;
import banks.Tools.BankAccountException;
import banks.Tools.BankException;
import banks.Tools.ClientException;
import banks.Tools.PassportException;
import banks.Tools.PhoneNumberException;
import banks.Tools.TransactionException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


/**
 * The BanksTest class is used to test the class methods.
 */
public class BanksTest {
    private final CentralBank centralBank = CentralBank.getInstance();

    /**
     * Tests the createBank and setPercent methods.
     * @throws BankException if an error occurs while creating a bank
     * @throws TransactionException if an error occurs while making a transaction
     */
    @Test
    public void createBankAndSetPercents() throws BankException, TransactionException {
        Bank bank = centralBank.createBank("Sberbank");
        bank.setDebitPercent(BigDecimal.valueOf(5));
        Assertions.assertTrue(centralBank.getBanks().contains(bank));
        Assertions.assertEquals(bank.getBankPercents().getDebitPercent(), BigDecimal.valueOf(5));
    }

    /**
     * Tests the createClient and createAccount methods.
     * @throws BankException if an error occurs while creating a bank
     * @throws ClientException if an error occurs while creating a client
     * @throws AddressException if an error occurs while adding an address to a client
     * @throws PassportException if an error occurs while adding passport data to a client
     * @throws ParseException if an error occurs while parsing a string to a date
     * @throws PhoneNumberException if an error occurs while adding a phone number to a client
     * @throws BankAccountException if an error occurs while creating a bank account
     * @throws TransactionException if an error occurs while making a transaction
     */
    @Test
    public void createClientAndAccount() throws BankException, ClientException, AddressException, PassportException, ParseException, PhoneNumberException, BankAccountException, TransactionException {
        Bank bank = centralBank.createBank("DrLiveseybank");
        var clientBuilder = new ClientBuilder("Ливси Доктор Ахахахахахахахович");
        Address address = new Address("Привет, я бот, Доктор, Ливси, 1, 2");
        clientBuilder.addAddress(address);
        Passport passport = new Passport("1234, 567890, МВД Казахстана, 2003.05.12");
        clientBuilder.addPassportData(passport);
        PhoneNumber phoneNumber = new PhoneNumber("+79123456789");
        clientBuilder.addPhoneNumber(phoneNumber);
        Client client = clientBuilder.createClient();
        bank.createDepositAccount(client, BigDecimal.valueOf(10000));
        Assertions.assertTrue(bank.getClients().contains(client));
        Assertions.assertEquals(client.getBankAccounts().get(0).getStartMoney(), BigDecimal.valueOf(10000));
    }

    /**
     * Tests the setCommission and accruing commission methods.
     * @throws BankException if an error occurs while creating a bank
     * @throws TransactionException if an error occurs while making a transaction
     * @throws ClientException if an error occurs while creating a client
     * @throws BankAccountException if an error occurs while creating a bank account
     */
    @Test
    public void checkCommissionIsAccrued() throws BankException, TransactionException, ClientException, BankAccountException {
        Bank bank = centralBank.createBank("Bebrabank");
        bank.setCommission(BigDecimal.valueOf(50));
        bank.setLimitForDoubtful(BigDecimal.valueOf(15000));
        bank.setCreditLimit(BigDecimal.valueOf(15000));
        var clientBuilder = new ClientBuilder("Ливси Доктор Ахахахахахахахович");
        Client client = clientBuilder.createClient();
        CreditAccount creditAccount = bank.createCreditAccount(client, BigDecimal.valueOf(10000));
        creditAccount.withdraw(BigDecimal.valueOf(200));
        Assertions.assertEquals(creditAccount.getTransactions().get(0).getCommission(), BigDecimal.valueOf(50));
    }

    /**
     * Tests the cancelTransaction method.
     * @throws BankException when a bank-related exception occurs
     * @throws ClientException when a client-related exception occurs
     * @throws BankAccountException when a bank account-related exception occurs
     * @throws TransactionException when a transaction-related exception occurs
     */
    @Test
    public void canselTransaction() throws BankException, ClientException, BankAccountException, TransactionException {
        Bank bank = centralBank.createBank("Sber");
        var clientBuilder = new ClientBuilder("Ливси Доктор Ахахахахахахахович");
        Client client = clientBuilder.createClient();
        DebitAccount debitAccount = bank.createDebitAccount(client, BigDecimal.valueOf(10000));
        debitAccount.withdraw(BigDecimal.valueOf(9999));
        bank.cancelTransaction(debitAccount.getTransactions().get(0));
        Assertions.assertTrue(debitAccount.getTransactions().get(0).getIsCancelled());
        Assertions.assertEquals(debitAccount.getMoney(), BigDecimal.valueOf(10000));
    }

    /**
     * Tests the subscribeToNotifications and setLimit methods.
     * @throws BankException when a bank-related exception occurs
     * @throws ClientException when a client-related exception occurs
     * @throws BankAccountException when a bank account-related exception occurs
     * @throws TransactionException when a transaction-related exception occurs
     */
    @Test
    public void subscribeToNotificationsAndUpdateLimit() throws BankException, ClientException, BankAccountException, TransactionException {
        Bank bank = centralBank.createBank("VTB");
        var clientBuilder = new ClientBuilder("Ливси Доктор Ахахахахахахахович");
        Client client = clientBuilder.createClient();
        DebitAccount debitAccount = bank.createDebitAccount(client, BigDecimal.valueOf(10000));
        client.subscribeToNotifications(new SimpleNotification());
        bank.setLimit(BigDecimal.valueOf(30000));
        Assertions.assertTrue(client.getSubscribedToNotifications());
        Assertions.assertTrue(client.getNotificator().getIsNotified());
    }
}
