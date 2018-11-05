package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.admin.Admin;
import seedu.address.model.admin.AdminSession;
import seedu.address.model.machine.Machine;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {


    /**
     * Returns an unmodifiable view of the admins list.
     * This list will not contain any duplicate admins.
     */
    ObservableList<Admin> getAdminList();


    /**
     * Returns an unmodifiable view of the machines list.
     * This list will not contain any duplicate machines.
     */
    ObservableList<Machine> getMachineList();

    /**
     * Returns an admin Session
     * Session should contain currently logged in Admin, or null if no admin is currently logged in
     */
    AdminSession getAdminSession();

    /**
     * Returns total number of Jobs stored in all Machines.
     */
    int getTotalNumberOfStoredJobs();

}
