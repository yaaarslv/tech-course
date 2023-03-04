package banks.Entities;

import banks.Tools.BankException;
import banks.Tools.TransactionException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * CentralBank class represents a central bank, which holds a list of banks and manages their operations.
 */
public class CentralBank {
    private static final int MONTHS_IN_YEAR = 12;

    private static CentralBank instance;

    private final List<Bank> banks;

    /**
     * Constructs a new instance of the CentralBank class and initializes the list of banks.
     */
    private CentralBank() {
        banks = new ArrayList<Bank>();
    }

    /**
     * Returns the singleton instance of the CentralBank class.
     * @return the singleton instance of the CentralBank class
     */
    public static CentralBank getInstance() {
        if (instance == null) {
            instance = new CentralBank();
        }

        return instance;
    }

    /**
     * Returns an unmodifiable list of banks managed by the central bank.
     * @return an unmodifiable list of banks managed by the central bank
     */
    public List<Bank> getBanks() {
        return Collections.unmodifiableList(banks);
    }

    /**
     * Creates a new bank with the specified name and adds it to the list of banks.
     * @param name the name of the new bank
     * @return  the newly created bank
     * @throws BankException if the name is empty or if a bank with the same name already exists
     */
    public Bank createBank(String name) throws BankException {
        if (name.isEmpty()) {
            throw BankException.bankNameIsNullException();
        }

        if (checkBankExistence(name)) {
            throw BankException.bankAlreadyCreatedException();
        }

        Bank bank = new Bank(name);
        banks.add(bank);
        return bank;
    }

    /**
     * Transfers money from one bank to another.
     * @param senderBank the bank that sends the money
     * @param recipientBank the bank that receives the money
     * @param money the amount of money to transfer
     * @throws BankException if the sender bank or the recipient bank is null
     * @throws TransactionException if the amount of money to transfer is invalid
     */
    public void transferMoney(Bank senderBank, Bank recipientBank, BigDecimal money) throws BankException, TransactionException {
        if (senderBank == null) {
            throw BankException.bankIsNullException();
        }

        if (recipientBank == null) {
            throw BankException.bankIsNullException();
        }

        if (money.compareTo(BigDecimal.valueOf(0)) <= 0) {
            throw TransactionException.invalidMoneyException();
        }

        senderBank.decrementMoney(money);
        recipientBank.incrementMoney(money);
    }

    /**
     * Notifies all banks managed by the central bank.
     */
    public void notifyBanks() {
        banks.forEach(Bank::notifyBank);
    }

    /**
     * Accelerates time in the system by accruing percents in all banks for each day in the specified calendar date.
     * @param dateToAccelerate the calendar date to accelerate time to
     */
    public void timeAcceleration(Calendar dateToAccelerate) {
        int days = dateToAccelerate.get(Calendar.DAY_OF_MONTH);
        int months = dateToAccelerate.get(Calendar.MONTH);
        int years = dateToAccelerate.get(Calendar.YEAR);
        for (int i = 0; i < months + (years * MONTHS_IN_YEAR); i++) {
            for (int j = 0; j < days; j++) {
                accruePercentsInAllBanks();
            }

            addPercentsInAllBanks();
        }
    }

    /**
     * Accrues interest to all accounts in all banks managed by the central bank.
     */
    private void accruePercentsInAllBanks() {
        banks.forEach(Bank::accruePercentsToAccounts);
    }

    /**
     * Adds interest to all accounts in all banks managed by the central bank.
     */
    private void addPercentsInAllBanks() {
        banks.forEach(Bank::addPercentsToAccountsBalance);
    }

    /**
     * Checks whether a bank with the specified name already exists in the list of banks.
     * @param name a String representing the name of the bank to check
     * @return a Boolean indicating whether a bank with the specified name exists in the list of banks
     */
    private Boolean checkBankExistence(String name) {
        return banks.stream().anyMatch(b -> b.getName().equals(name));
    }
}
