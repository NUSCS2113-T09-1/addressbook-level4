package seedu.address.logic.commands.job;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.commons.core.JobMachineTuple;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.admin.Admin;
import seedu.address.model.admin.Username;
import seedu.address.model.job.Job;
import seedu.address.model.job.JobName;
import seedu.address.model.machine.Machine;
import seedu.address.model.machine.MachineName;
import seedu.address.testutil.builders.JobBuilder;


public class AddJobCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullJob_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddJobCommand(null);
    }

    @Test
    public void execute_jobAcceptedByModel_addSuccessful() throws Exception {
        AddJobCommandTest.ModelStubAcceptingJobAdded modelStub = new AddJobCommandTest.ModelStubAcceptingJobAdded();
        Job validJob = new JobBuilder().build();

        CommandResult commandResult = new AddJobCommand(validJob).execute(modelStub, commandHistory);

        assertEquals(String.format(AddJobCommand.MESSAGE_SUCCESS, validJob.getJobName()), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validJob), modelStub.jobsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateJob_throwsCommandException() throws Exception {
        Job validJob = new JobBuilder().build();
        AddJobCommand addJobCommand = new AddJobCommand(validJob);
        AddJobCommandTest.ModelStub modelStub = new AddJobCommandTest.ModelStubWithJob(validJob);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddJobCommand.MESSAGE_DUPLICATE_JOB);
        addJobCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Job m1 = new JobBuilder().withName("Job1").build();
        Job m2 = new JobBuilder().withName("Job2").build();
        AddJobCommand addm1Command = new AddJobCommand(m1);
        AddJobCommand addm2Command = new AddJobCommand(m2);

        // same object -> returns true
        assertTrue(addm1Command.equals(addm1Command));

        // same values -> returns true
        AddJobCommand addm1CommandCopy = new AddJobCommand(m1);
        assertTrue(addm1Command.equals(addm1CommandCopy));

        // different types -> returns false
        assertFalse(addm1Command.equals(1));

        // null -> returns false
        assertFalse(addm1Command.equals(null));

        // different person -> returns false
        assertFalse(addm1Command.equals(addm2Command));
    }

    /**
     * A Model stub that contains a single Job.
     */
    private class ModelStubWithJob extends ModelStub {
        private final Job job;

        ModelStubWithJob(Job job) {
            requireNonNull(job);
            this.job = job;
        }

        @Override
        public boolean hasJob(Job job) {
            requireNonNull(job);
            return this.job.isSameJob(job);
        }

        @Override
        public JobMachineTuple findJob(JobName jobName) {
            requireNonNull(jobName);
            if (this.job.getJobName().equals(jobName)) {
                return new JobMachineTuple(job, findMachine(job.getMachineName()));
            }
            return null;
        }

        @Override
        public boolean isLoggedIn() {
            return true;
        }
    }

    /**
     * A Model stub that is logged in.
     */
    private class ModelStubNonAdmin extends ModelStub {
        ModelStubNonAdmin() {
        }

        @Override
        public boolean isLoggedIn() {
            return true;
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingJobAdded extends ModelStub {
        final ArrayList<Job> jobsAdded = new ArrayList<>();

        @Override
        public boolean hasJob(Job job) {
            requireNonNull(job);
            return jobsAdded.stream().anyMatch(job::isSameJob);
        }

        @Override
        public JobMachineTuple findJob(JobName jobName) {
            requireNonNull(jobName);
            for (Job job : jobsAdded) {
                if (job.getJobName().equals(jobName)) {
                    return new JobMachineTuple(job, findMachine(job.getMachineName()));
                }
            }
            return null;
        }

        @Override
        public void addJob(Job job) {
            requireNonNull(job);
            jobsAdded.add(job);
        }

        @Override
        public void commitAddressBook() {
            // called by {@code AddCommand#execute()}
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }

        @Override
        public boolean isLoggedIn() {
            return true;
        }
    }


    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {

        @Override
        public void resetData(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasJob(Job job) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addJob(Job job) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteJob(JobName job) {

        }

        public void startJob(JobName name) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void cancelJob(JobName name) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void restartJob(JobName name) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void swapJobs(JobName jobname1, JobName jobName2) {
        }

        @Override
        public void finishJob(JobMachineTuple job) {

        }

        @Override
        public void requestDeletion(JobName jobName) {

        }

        @Override
        public int getTotalNumberOfJobsDisplayed() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isTopJob(JobName job) {
            return false;
        }

        public void moveJobToMachine(Job job, Machine targetMachine) {

        }

        @Override
        public void autoMoveJobsDuringFlush(Machine currentMachine) {

        }

        @Override
        public Machine findMachine(MachineName machinename) {
            return null;
        }

        @Override
        public void updateJob(Job oldJob, Job updatedJob) {

        }

        @Override
        public JobMachineTuple findJob(JobName name) {
            return null;
        }

        @Override
        public void addMachine(Machine machine) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void updateMachine(Machine target, Machine editedMachine) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public Machine getMostFreeMachine() {
            return null;
        }

        @Override
        public Machine getMostFreeMachine(Machine otherThanMe) {
            return null;
        }

        @Override
        public void removeMachine(Machine machine) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public boolean hasMachine(Machine machine) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public boolean hasSameMachineName(Machine machine) {
            return false;
        }

        @Override
        public void flushMachine(Machine toFlushMachine) {

        }

        @Override
        public void cleanMachine(Machine toCleanMachine) {

        }

        @Override
        public void addAdmin(Admin admin) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeAdmin(Admin admin) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateAdmin(Admin admin, Admin updatedAdmin) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setLogin(Admin admin) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void clearLogin() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isLoggedIn() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Admin findAdmin(Username username) {
            return null;
        }

        @Override
        public int numAdmins() {
            return 0;
        }

        @Override
        public ObservableList<Admin> getFilteredAdminList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredAdminList(Predicate<Admin> predicate) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public ObservableList<Machine> getFilteredMachineList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredMachineList(Predicate<Machine> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredJobListInAllMachines(Predicate<Job> predicate) {
        }

        @Override
        public boolean canUndoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void adminLoginCommitAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void adminLogoutCommitAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isRedoLogin() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isUndoLogout() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Admin currentlyLoggedIn() {
            return null;
        }

        @Override
        public boolean isUndoLogin() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void shiftJob(JobName jobName, int shiftBy) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void moveJob(JobName jobName, MachineName targetMachineName) {
            throw new AssertionError("This method should not be called.");
        }
    }
}
