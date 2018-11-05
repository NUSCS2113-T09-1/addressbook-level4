package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.JobMachineTuple;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.admin.Admin;
import seedu.address.model.admin.Username;
import seedu.address.model.job.Job;
import seedu.address.model.job.JobName;
import seedu.address.model.machine.Machine;
import seedu.address.model.machine.MachineName;

/**
 * The API of the Model component.
 */
public interface Model {

    //TODO Explain reason for using this code
    /** {@code Predicate} that always eveluate to true for machines */
    Predicate<Machine> PREDICATE_SHOW_ALL_MACHINES = unused -> true;

    /** {@code Predicate} that always eveluate to true for jobs */
    Predicate<Job> PREDICATE_SHOW_ALL_JOBS = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyAddressBook newData);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();


    // ============================== Job methods ======================================= //

    void addJob(Job job);

    void deleteJob(JobName job);

    void updateJob(Job oldJob, Job updatedJob);

    JobMachineTuple findJob(JobName name);

    boolean hasJob(Job job);

    /**
     * Starts the job given
     */
    void startJob(JobName name);

    /**
     * Cancels the job given
     */
    void cancelJob(JobName name);

    /**
     * Restarts the job given
     */
    void restartJob(JobName name);

    /**
     * Swaps two jobs based on the given names
     */

    void swapJobs(JobName jobname1, JobName jobName2);

    void moveJob(JobName jobName, MachineName targetMachineName);

    void shiftJob(JobName jobName, int shiftBy);

    void finishJob(JobMachineTuple target);

    void requestDeletion(JobName jobName);

    int getTotalNumberOfJobsDisplayed();

    void moveJobToMachine(Job job, Machine targetMachine);

    void autoMoveJobsDuringFlush(Machine currentMachine) throws CommandException;

    // ============================== Machine methods ======================================= //

    boolean isTopJob(JobName job);

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
     * Returns true if a Machine with the same identity as {@code machine} exists in the address book.
     */
    boolean hasMachine(Machine machine);

    /**
     * Returns true if the same Machine name exists in the address book.
     */

    boolean hasSameMachineName(Machine machine);

    /**
     * Flushes all the jobs in the machine
     */

    void flushMachine(Machine toFlushMachine);

    /**
     * Cleans all the jobs in the machine that fufills the predicate in uniqueJobList
     * for cleanJobPredicate
     */

    void cleanMachine(Machine toCleanMachine);
    /**
     * Replaces the given machine {@code target} with {@code editedMachine}.
     * {@code target} must exist in the Model.
     * The machine identity of {@code editedMachine} must not be the same as another existing machine in the model.
     */
    void updateMachine(Machine target, Machine editedMachine);
    /**
     * returns the machine if machine name is found
     * @param machinename
     */
    Machine findMachine(MachineName machinename);


    // ============================== Admin methods ======================================= //

    /**
     * Returns the machine that is most free, in terms fo time left
     * @return Machine
     */
    Machine getMostFreeMachine();
    /**
     * Returns the machine that is most free and not {@code otherThanMe}, in terms of time left
     * @param otherThanMe
     * @return Machine
     */
    Machine getMostFreeMachine(Machine otherThanMe);

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
    void setLogin(Admin admin);
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
    Admin currentlyLoggedIn();
    /**
     * returns the admin if username is found
     * @param username
     */
    Admin findAdmin(Username username);
    /**
     * returns number of admins in the makerManager
     */
    int numAdmins();



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

    void updateFilteredJobListInAllMachines(Predicate<Job> predicate);

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
    /**
     * Saves the current address book state for undo/redo. Indicates saved state is a login.
     */
    void adminLoginCommitAddressBook();
    /**
     * Saves the current address book state for undo/redo. Indicates saved state is a logout.
     */
    void adminLogoutCommitAddressBook();
    /**
     * Returns true if next state is a login.
     */
    boolean isRedoLogin();
    /**
     * Returns true if previous state is a logout.
     */
    boolean isUndoLogout();
    /**
     * Returns True if previos state was a login
     * @return boolean
     */
    boolean isUndoLogin();

}
