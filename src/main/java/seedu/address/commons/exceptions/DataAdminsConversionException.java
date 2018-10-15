package seedu.address.commons.exceptions;
/**
 * Represents an error during conversion of admin data from xml
 */
public class DataAdminsConversionException extends Exception {
    public DataAdminsConversionException(Exception cause) {
        super(cause);
    }
}