package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyAddressBook;

/**
 * Indicates a changed in the model addressBook for machine list
 */
public class MachineListChangedEvent extends BaseEvent {

    public final ReadOnlyAddressBook data;

    public MachineListChangedEvent(ReadOnlyAddressBook data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of machines " + data.getMachineList().size();
    }
}