package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.admin.Admin;

/**
 * Inidates there is an admin changed in  maker manager
 */
public class SetAdminChangedEvent extends BaseEvent {

    public final Admin admin;

    public SetAdminChangedEvent(Admin admin) {
        this.admin = admin;
    }

    @Override
    public String toString() {
        return "Admin for maker manager is changed";
    }
}