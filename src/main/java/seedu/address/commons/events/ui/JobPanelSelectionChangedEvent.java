package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.job.Job;

/**
 * Represents a selection change in the Job List Panel
 */
public class JobPanelSelectionChangedEvent extends BaseEvent {


    private final Job newSelection;

    public JobPanelSelectionChangedEvent(Job newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public Job getNewSelection() {
        return newSelection;
    }
}
