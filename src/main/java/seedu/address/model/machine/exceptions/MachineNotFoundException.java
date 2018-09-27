package seedu.address.model.machine.exceptions;


/**
 * Signals that the operation is unable to find the specific machine
 */
public class MachineNotFoundException extends RuntimeException {
    public MachineNotFoundException() {
        super("Operation would result in failed attempt to find specific machine");
    }

}
