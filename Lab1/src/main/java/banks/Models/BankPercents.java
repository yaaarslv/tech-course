package banks.Models;

import banks.Tools.*;
import java.math.BigDecimal;
import java.util.AbstractMap;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * A class that represents the interest rates and fees for a bank account
 */
public class BankPercents {
    private final Map<AbstractMap.SimpleEntry<BigDecimal, BigDecimal>, BigDecimal> depositPercents;
    private BigDecimal commission;
    private BigDecimal debitPercent;

    /**
     * Creates a new instance of the BankPercents class with an empty deposit percent map
     */
    public BankPercents() {
        depositPercents = new HashMap<AbstractMap.SimpleEntry<BigDecimal, BigDecimal>, BigDecimal>();
    }

    /**
     * Returns an unmodifiable view of the deposit percent map
     * @return an unmodifiable view of the deposit percent map
     */
    public Map<AbstractMap.SimpleEntry<BigDecimal, BigDecimal>, BigDecimal> getDepositPercents() {
        return Collections.unmodifiableMap(depositPercents);
    }

    /**
     * Returns the commission rate charged by the bank
     * @return the commission rate charged by the bank
     */
    public BigDecimal getCommission() {
        return commission;
    }

    /**
     * Sets the commission rate charged by the bank
     * @param commission the new commission rate
     */
    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }

    /**
     * Returns the interest rate applied to the account balance when it is in a debit state
     * @return the debit interest rate
     */
    public BigDecimal getDebitPercent() {
        return debitPercent;
    }

    /**
     * Sets the interest rate applied to the account balance when it is in a debit state
     * @param debitPercent the new debit interest rate
     */
    public void setDebitPercent(BigDecimal debitPercent) {
        this.debitPercent = debitPercent;
    }

    /**
     * Adds a new deposit percent rate for the specified deposit range to the deposit percent map
     * @param newRange the deposit range to which the new rate applies
     * @param newPercent the new deposit percent rate
     * @throws BankAccountException if the specified deposit range already has an associated percent rate
     */
    public void addDepositPercent(AbstractMap.SimpleEntry<BigDecimal, BigDecimal> newRange, BigDecimal newPercent) throws BankAccountException {
        if (checkPercentExistence(newRange)) {
            throw BankAccountException.percentAlreadyContains();
        }

        depositPercents.put(newRange, newPercent);
    }

    /**
     * Removes the deposit percent rate associated with the specified deposit range from the deposit percent map
     * @param depositRange the deposit range for which to remove the associated rate
     * @throws BankAccountException if the specified deposit range does not have an associated percent rate
     */
    public void removeDepositPercent(AbstractMap.SimpleEntry<BigDecimal, BigDecimal> depositRange) throws BankAccountException {
        if (!checkPercentExistence(depositRange)) {
            throw BankAccountException.percentNotExist();
        }

        depositPercents.remove(depositRange);
    }

    /**
     * Returns whether the deposit percent map contains an associated rate for the specified deposit range
     * @param depositRange the deposit range to check
     * @return true if the map contains an associated rate for the deposit range; false otherwise
     */
    private Boolean checkPercentExistence(AbstractMap.SimpleEntry<BigDecimal, BigDecimal> depositRange) {
        return depositPercents.containsKey(depositRange);
    }
}
