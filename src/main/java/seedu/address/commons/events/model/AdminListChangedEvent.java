package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyAddressBook;

/**
 * Indicates a changed in the admins list in the models addressbook
 */
public class AdminListChangedEvent extends BaseEvent {

    public final ReadOnlyAddressBook data;

    public AdminListChangedEvent(ReadOnlyAddressBook data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of admins  " + data.getAdminList().size();
    }
}
