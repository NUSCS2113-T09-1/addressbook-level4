package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.commands.machine.ManageMachineCommand.MESSAGE_NO_MORE_MACHINES;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.JobMachineTuple;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.model.AdminListChangedEvent;
import seedu.address.commons.events.model.MachineListChangedEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.admin.Admin;
import seedu.address.model.admin.Username;
import seedu.address.model.job.Job;
import seedu.address.model.job.JobName;
import seedu.address.model.job.Status;
import seedu.address.model.job.exceptions.JobNotStartedException;
import seedu.address.model.job.exceptions.JobOngoingException;
import seedu.address.model.machine.Machine;
import seedu.address.model.machine.MachineName;


/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedAddressBook versionedAddressBook;
    private final FilteredList<Admin> filteredAdmins;
    private final FilteredList<Machine> filteredMachines;


    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine(
            "Initializing with address book: " + addressBook + " and user prefs " + userPrefs + "and initial admin");

        versionedAddressBook = new VersionedAddressBook(addressBook);
        filteredMachines = new FilteredList<>(versionedAddressBook.getMachineList());
        filteredAdmins = new FilteredList<>(versionedAddressBook.getAdminList());

        Timer refreshTimer = refreshUiTimer();

    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    /**
     * Helps refresh the Ui and show changes instantaneously
     * @return
     */
    private Timer refreshUiTimer() {
        // Timer for auto print cleanup

        // credit: https://dzone.com/articles/how-schedule-task-run-interval
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                for (Machine machine : versionedAddressBook.getMachineList()) {
                    for (Job job : machine.getJobs()) {
                        try {
                            if (job.getStatus() == Status.ONGOING && job.isFinished()) {
                                finishJob(new JobMachineTuple(job, machine));
                            }
                        } catch (JobNotStartedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };

        Timer timer = new Timer();
        long delay = 1000;
        long intervalPeriod = 1000;
        timer.scheduleAtFixedRate(task, delay, intervalPeriod);

        return timer;
    }

    @Override
    public void resetData(ReadOnlyAddressBook newData) {
        versionedAddressBook.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return versionedAddressBook;
    }

    /**
     * Raises an event to indicate the model has changed
     */
    private void indicateAddressBookChanged() {
        raise(new AddressBookChangedEvent(versionedAddressBook));
    }

    /**
     * Raises an event to indicate the model has changed
     */
    private void indicateAdminListChanged() {
        raise(new AdminListChangedEvent(versionedAddressBook));
    }

    /**
     * Raises an event to indicate the model has changed
     */
    private void indicateMachineListChanged() {
        raise(new MachineListChangedEvent(versionedAddressBook));
    }

    // TODO: 11/3/2018 REMOVE UNUSED METHOD

    /**
     * Raises an event to indicate the model has changed
     */
    private void indicateJobListChanged() {
        raise(new JobListChangedEvent(versionedAddressBook));
        /**
         * Since when job changes, it implicitly implies that machine list will change too
         */
        raise(new MachineListChangedEvent(versionedAddressBook));
    }

    // ============================== Job related methods ======================================= //

    @Override
    public boolean hasJob(Job job) {
        requireNonNull(job);
        return versionedAddressBook.hasJob(job);
    }

    @Override
    public void addJob(Job job) {
        requireAllNonNull(job);
        versionedAddressBook.addJobToMachineList(job);
        indicateMachineListChanged();
    }

    @Override
    public void deleteJob(JobName job) {
        requireAllNonNull(job);
        versionedAddressBook.removeJob(job);
        indicateMachineListChanged();
    }

    @Override
    public void updateJob(Job oldJob, Job updatedJob) {
        requireAllNonNull(oldJob, updatedJob);
        versionedAddressBook.updateJob(oldJob, updatedJob);
        indicateMachineListChanged();
    }


    @Override
    public JobMachineTuple findJob(JobName name) {
        requireAllNonNull(name);
        JobMachineTuple query = versionedAddressBook.findJob(name);

        if (query != null) {
            return query;
        } else {
            return null;
        }
    }

    @Override
    public void startJob(JobName name) {
        requireNonNull(name);
        versionedAddressBook.startJob(name);
        indicateMachineListChanged();
    }

    @Override
    public void cancelJob(JobName name) {
        requireAllNonNull();
        versionedAddressBook.cancelJob(name);
        indicateMachineListChanged();
    }

    @Override
    public void restartJob(JobName name) {
        requireAllNonNull();
        versionedAddressBook.restartJob(name);
        indicateMachineListChanged();
    }

    @Override
    public void swapJobs(JobName jobName1, JobName jobName2) {
        versionedAddressBook.swapJobs(jobName1, jobName2);
        indicateMachineListChanged();
    }

    @Override
    public void moveJob(JobName jobName, MachineName targetMachineName) {
        versionedAddressBook.moveJob(jobName, targetMachineName);
        indicateMachineListChanged();
    }

    @Override
    public void shiftJob(JobName jobName, int shiftBy) {
        versionedAddressBook.shiftJob(jobName, shiftBy);
        indicateMachineListChanged();
    }

    @Override
    public void finishJob(JobMachineTuple target) {
        versionedAddressBook.finishJob(target);
        indicateMachineListChanged();
    }

    @Override
    public void requestDeletion(JobName jobName) {
        versionedAddressBook.requestDeletion(jobName);
        indicateMachineListChanged();
    }

    @Override
    public void updateFilteredJobListInAllMachines(Predicate<Job> predicate) {
        for (Machine machine : getFilteredMachineList()) {
            machine.updateFilteredJobList(predicate);
        }
    }

    @Override
    public int getTotalNumberOfJobsDisplayed() {
        return getFilteredMachineList().stream().mapToInt(m -> m.getJobsAsFilteredObservableList().size()).sum();
    }

    @Override
    public boolean isTopJob(JobName job) {
        return versionedAddressBook.isTopJob(job);
    }

    /**
     * used for moving Job to Machine
     *
     * @param job
     * @param targetMachine
     */
    public void moveJobToMachine(Job job, Machine targetMachine) {
        job.setMachine(targetMachine.getName());
        targetMachine.addJob(job);
    }

    @Override
    public void autoMoveJobsDuringFlush(Machine currentMachine) throws CommandException {
        if (currentMachine.getJobs().stream().anyMatch(job -> job.getStatus() == Status.ONGOING)) {
            throw new JobOngoingException();
        }
        for (Job j : new ArrayList<>(currentMachine.getJobs())) {
            System.out.println(j.getJobName().fullName);
            try {
                Machine mostFreeMachine = getMostFreeMachine(currentMachine);
                moveJob(j.getJobName(), mostFreeMachine.getName());
            } catch (MachineNotFoundException mfe) {
                throw new CommandException(MESSAGE_NO_MORE_MACHINES);
            }
        }
        flushMachine(currentMachine);
        indicateMachineListChanged();
    }


    // ============================== Machine methods ======================================= //

    @Override
    public void addMachine(Machine machine) {
        versionedAddressBook.addMachine(machine);
        updateFilteredMachineList(PREDICATE_SHOW_ALL_MACHINES);
        indicateMachineListChanged();
    }

    @Override
    public void removeMachine(Machine toRemove) {
        versionedAddressBook.removeMachine(toRemove);
        indicateMachineListChanged();
    }

    @Override
    public boolean hasMachine(Machine machine) {
        requireNonNull(machine);
        return versionedAddressBook.hasMachine(machine);
    }

    @Override
    public boolean hasSameMachineName(Machine machine) {
        return versionedAddressBook.hasSameMachineName(machine);
    }

    @Override
    public void flushMachine(Machine toFlushMachine) {
        requireNonNull(toFlushMachine);
        versionedAddressBook.flushMachine(toFlushMachine);
        indicateMachineListChanged();
    }

    @Override
    public void cleanMachine(Machine toCleanMachine) {
        requireNonNull(toCleanMachine);
        versionedAddressBook.cleanMachine(toCleanMachine);
        indicateMachineListChanged();
    }


    @Override
    public void updateMachine(Machine target, Machine editedMachine) {
        requireAllNonNull(target, editedMachine);
        versionedAddressBook.updateMachine(target, editedMachine);
        indicateMachineListChanged();
    }

    @Override
    public Machine getMostFreeMachine() {
        return versionedAddressBook.getMostFreeMachine();
    }

    @Override
    public Machine getMostFreeMachine(Machine otherThanMe) {
        return versionedAddressBook.getMostFreeMachine(otherThanMe);
    }

    @Override
    public Machine findMachine(MachineName machineName) {
        requireNonNull(machineName);
        return versionedAddressBook.findMachine(machineName);
    }

    // ============================== Admin methods ======================================= //

    @Override
    public void addAdmin(Admin admin) {
        versionedAddressBook.addAdmin(admin);
        indicateAdminListChanged();
    }

    //TODO: add tests
    @Override
    public void removeAdmin(Admin admin) {
        versionedAddressBook.removeAdmin(admin);
        indicateAdminListChanged();
    }

    //TODO: add tests
    @Override
    public void updateAdmin(Admin admin, Admin updatedAdmin) {
        versionedAddressBook.addAdmin(updatedAdmin);
        versionedAddressBook.removeAdmin(admin);
        indicateAdminListChanged();
    }

    @Override
    public void setLogin(Admin admin) {
        versionedAddressBook.setLoggedInAdmin(admin);
    }

    @Override
    public void clearLogin() {
        versionedAddressBook.clearLogin();
    }

    @Override
    public boolean isLoggedIn() {
        return versionedAddressBook.isLoggedIn();
    }

    @Override
    public Admin currentlyLoggedIn() {
        return versionedAddressBook.currentlyLoggedIn();
    }

    @Override
    public Admin findAdmin(Username username) {
        requireNonNull(username);
        return versionedAddressBook.findAdmin(username);
    }

    @Override
    public int numAdmins() {
        return versionedAddressBook.numAdmins();
    }

    //=========== Filtered Machine List Accessors ============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Machine} backed by the internal list of
     * {@code versionedAddressBook}
     */

    @Override
    public ObservableList<Machine> getFilteredMachineList() {
        return FXCollections.unmodifiableObservableList(filteredMachines);
    }

    @Override
    public void updateFilteredMachineList(Predicate<Machine> predicate) {
        requireNonNull(predicate);
        filteredMachines.setPredicate(predicate);
    }


    //=========== Filtered Admins List Accessors ============================================================

    @Override
    public ObservableList<Admin> getFilteredAdminList() {
        return FXCollections.unmodifiableObservableList(filteredAdmins);
    }

    @Override
    public void updateFilteredAdminList(Predicate<Admin> predicate) {
        requireNonNull(predicate);
        filteredAdmins.setPredicate(predicate);
    }


    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoAddressBook() {
        return versionedAddressBook.canUndo();
    }

    @Override
    public boolean canRedoAddressBook() {
        return versionedAddressBook.canRedo();
    }

    @Override
    public boolean isUndoLogout() {
        return versionedAddressBook.isUndoLogout();
    }

    @Override
    public boolean isRedoLogin() {
        return versionedAddressBook.isRedoLogin();
    }

    @Override
    public boolean isUndoLogin() {
        return versionedAddressBook.isUndoLogin();
    }

    @Override
    public void undoAddressBook() {
        versionedAddressBook.undo();
        indicateAddressBookChanged();
        indicateMachineListChanged();
        indicateAdminListChanged();
    }

    @Override
    public void redoAddressBook() {
        versionedAddressBook.redo();
        indicateAddressBookChanged();
        indicateMachineListChanged();
        indicateAdminListChanged();
    }

    @Override
    public void commitAddressBook() {
        versionedAddressBook.commit();
        indicateAddressBookChanged();
        indicateMachineListChanged();
        indicateAdminListChanged();
    }

    @Override
    public void adminLoginCommitAddressBook() {
        versionedAddressBook.adminLoginCommit();
        indicateAddressBookChanged();
        indicateMachineListChanged();
        indicateAdminListChanged();
    }

    @Override
    public void adminLogoutCommitAddressBook() {
        versionedAddressBook.adminLogoutCommit();
        indicateAddressBookChanged();
        indicateMachineListChanged();
        indicateAdminListChanged();
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return versionedAddressBook.equals(other.versionedAddressBook)
            || filteredMachines.equals(other.filteredMachines);
    }


}
