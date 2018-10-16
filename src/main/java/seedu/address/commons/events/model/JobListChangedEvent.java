package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyAddressBook;

/**
 * Indicates that the job list in the model addressbook has changed
 */
public class JobListChangedEvent extends BaseEvent {

    public final ReadOnlyAddressBook data;

    public JobListChangedEvent(ReadOnlyAddressBook data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of jobs " + data.getJobList().size();
    }
}