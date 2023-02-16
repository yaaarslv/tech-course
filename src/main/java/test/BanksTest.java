package test;

import banks.Tools.*;
import banks.Entities.*;
import banks.Models.*;
import banks.NotifyMethods.*;
import java.math.BigDecimal;
import java.text.ParseException;
import org.junit.*;

/**
 * The BanksTest class is used to test the methods of classes
 */
public class BanksTest {
    private final CentralBank centralBank = CentralBank.getInstance();

    /**
     * Tests the createBank and setPercent methods
     * @throws BankException if an error occurs while creating a bank
     * @throws TransactionException if an error occurs while making a transaction
     */
    @Test
    public void createBankAndSetPercents() throws BankException, TransactionException {
        Bank bank = centralBank.createBank("Sberbank");
        bank.setDebitPercent(BigDecimal.valueOf(5));
        Assert.assertTrue(centralBank.getBanks().contains(bank));
        Assert.assertEquals(bank.getBankPercents().getDebitPercent(), BigDecimal.valueOf(5));
    }

    /**
     * Tests the createClient and createAccount methods
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
        Assert.assertTrue(bank.getClients().contains(client));
        Assert.assertEquals(client.getBankAccounts().get(0).getStartMoney(), BigDecimal.valueOf(10000));
    }

    /**
     * Tests the setCommission and accruing commission methods
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
        Assert.assertEquals(creditAccount.getTransactions().get(0).getCommission(), BigDecimal.valueOf(50));
    }

    /**
     * Tests the cancelTransaction method
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
        Assert.assertTrue(debitAccount.getTransactions().get(0).getIsCancelled());
        Assert.assertEquals(debitAccount.getMoney(), BigDecimal.valueOf(10000));
    }

    /**
     * Tests the subscribeToNotifications and setLimit methods
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
        Assert.assertTrue(client.getSubscribedToNotifications());
        Assert.assertTrue(client.getNotificator().getIsNotified());
    }
}
