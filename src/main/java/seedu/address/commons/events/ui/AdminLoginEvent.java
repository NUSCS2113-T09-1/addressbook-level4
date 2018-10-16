package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * An event for indicating admin login
 */
public class AdminLoginEvent extends BaseEvent {

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}