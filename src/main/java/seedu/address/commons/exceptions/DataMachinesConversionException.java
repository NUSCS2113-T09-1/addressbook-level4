package seedu.address.commons.exceptions;
/**
 * Represents an error during conversion of machines data from xml
 */
public class DataMachinesConversionException extends Exception {
    public DataMachinesConversionException(Exception cause) {
        super(cause);
    }
}