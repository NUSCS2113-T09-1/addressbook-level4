package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.admin.Admin;
import seedu.address.model.admin.Username;
import seedu.address.model.job.Job;
import seedu.address.model.job.JobName;
import seedu.address.model.machine.Machine;
import seedu.address.model.person.Person;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    //TODO Explain reason for using this code
    /** {@code Predicate} that always eveluate to true for machines */
    Predicate<Machine> PREDICATE_SHOW_ALL_MACHINES = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyAddressBook newData);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    // ============================== Person methods ======================================= //
    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);
    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);
    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);
    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void updatePerson(Person target, Person editedPerson);

    // ============================== Job methods ======================================= //

    void addJob(Job job);
    void removeJob(Job job);
    void updateJob(Job oldJob, Job updatedJob);
    Job findJob(JobName name);

    // ============================== Machine methods ======================================= //
    /**
     * Adds the given machine
     * Machine must not exists
     */
    void addMachine(Machine machine);
    /**
     * Removes the given machine
     * machine must exist in the database
     */
    void removeMachine(Machine machine);
    /**
     * Returns true if a Machine with the same identity as {@code person} exists in the address book.
     */
    boolean hasMachine(Machine machine);
    /**
     * Replaces the given machine {@code target} with {@code editedMachine}.
     * {@code target} must exist in the Model.
     * The machine identity of {@code editedMachine} must not be the same as another existing machine in the model.
     */
    void updateMachine(Machine target, Machine editedMachine);

    // ============================== Admin methods ======================================= //
    /**
     * Adds the given Admin
     * admin must not exist
     * @param admin
     */
    void addAdmin(Admin admin);
    /**
     * Removes the given Admin
     * admin must exist in the database
     * @param admin
     */
    void removeAdmin(Admin admin);
    /**
     * Updates the admin
     * admin must be present in data
     * updatedAdmin must be different from admin
     * @param admin
     * @param updatedAdmin
     */
    void updateAdmin(Admin admin, Admin updatedAdmin);
    /**
     * sets loginStatus to true
     */
    void setLogin(Username username);
    /**
     * sets loginStatus to false
     */
    void clearLogin();
    /**
     * Returns loginStatus
     */
    boolean isLoggedIn();
    /**
     * Returns the current logged in admin
     */
    Username currentlyLoggedIn();
    /**
     * returns the admin if username is found
     * @param username
     */
    Admin findAdmin(Username username);
    /**
     * returns number of admins in the makerManager
     */
    int numAdmins();


    //=========== Filtered Person List Accessors =============================================================
    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();
    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    //=========== Filtered Admin List Accessors =============================================================
    ObservableList<Admin> getFilteredAdminList();
    void updateFilteredAdminList(Predicate<Admin> predicate);

    //=========== Filtered Machine List Accessors =============================================================
    /** Returns an unmodifiable view of the filtered machine list */
    ObservableList<Machine> getFilteredMachineList();
    /**
     * Updates the filtere of the filtered machine list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredMachineList(Predicate<Machine> predicate);

    //=========== Filtered Job List Accessors =============================================================
    ObservableList<Job> getFilteredJobList();
    void updateFilteredJobList(Predicate<Job> predicate);


    //================================= AddressBook methods ===================================//
    /**
     * Returns true if the model has previous address book states to restore.
     */
    boolean canUndoAddressBook();
    /**
     * Returns true if the model has undone address book states to restore.
     */
    boolean canRedoAddressBook();
    /**
     * Restores the model's address book to its previous state.
     */
    void undoAddressBook();
    /**
     * Restores the model's address book to its previously undone state.
     */
    void redoAddressBook();
    /**
     * Saves the current address book state for undo/redo.
     */
    void commitAddressBook();
}