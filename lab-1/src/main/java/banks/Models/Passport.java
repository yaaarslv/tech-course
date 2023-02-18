package banks.Models;

import banks.Tools.PassportException;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Represents a Passport object, which contains information about a passport, including the passport series and number, the organization that issued the passport, and the date of issue.
 */
public class Passport {
    private static final int PASSPORT_COMPONENT_COUNT = 4;

    private final int series;

    private final int number;

    private final String issuedBy;

    private final Date issueDate;

    /**
     * Constructs a new Passport object from a string containing the passport data.
     * @param passportData the string containing the passport data
     * @throws PassportException if the passport data is null or invalid
     * @throws ParseException if there is an error parsing the date
     */
    public Passport(String passportData) throws PassportException, ParseException {
        if (passportData.isEmpty()) {
            throw PassportException.passportDataIsNullException();
        }

        var data = passportData.split(", ");
        if (data.length < PASSPORT_COMPONENT_COUNT) {
            throw PassportException.passportDataIsNotFullException();
        }

        series = Integer.parseInt(data[0]);
        number = Integer.parseInt(data[1]);
        issuedBy = data[2];
        DateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
        issueDate = formatter.parse(data[3]);
    }

    /**
     * Gets the series of the passport.
     * @return the series of the passport
     */
    public int getSeries() {
        return series;
    }

    /**
     * Gets the number of the passport.
     * @return the number of the passport
     */
    public int getNumber() {
        return number;
    }

    /**
     * Gets the organization that issued the passport.
     * @return the organization that issued the passport
     */
    public String getIssuedBy() {
        return issuedBy;
    }

    /**
     * Gets the date on which the passport was issued.
     * @return the date on which the passport was issued
     */
    public Date getIssueDate() {
        return issueDate;
    }

    /**
     * Returns a string representation of the passport data.
     * @return a string representation of the passport data
     */
    public String stringPassportData() {
        return series + ", " + number + ", " + issuedBy + ", " +issueDate;
    }
}
