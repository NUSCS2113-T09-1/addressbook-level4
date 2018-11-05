package seedu.address.logic.commands.admin;

import static org.junit.Assert.assertEquals;

import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.commons.core.JobMachineTuple;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.admin.Admin;
import seedu.address.model.admin.AdminSession;
import seedu.address.model.admin.Password;
import seedu.address.model.admin.Username;
import seedu.address.model.job.Job;
import seedu.address.model.job.JobName;
import seedu.address.model.machine.Machine;
import seedu.address.model.machine.MachineName;

public class LogoutCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_logoutWithoutLogin_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStub();
        LogoutCommand logoutCommand = new LogoutCommand();

        thrown.expect(CommandException.class);
        thrown.expectMessage(LogoutCommand.MESSAGE_NO_CURRENT_SESSION);
        logoutCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void execute_logout_successful() throws Exception {
        ModelStub modelStub = new ModelStub();
        Admin admin = new Admin(new Username("dummy"), new Password("oldPW"));
        modelStub.setLogin(admin);
        CommandResult commandResult = new LogoutCommand().execute(modelStub, commandHistory);

        assertEquals(commandResult.feedbackToUser, LogoutCommand.MESSAGE_SUCCESS);
        assertEquals(modelStub.isLoggedIn(), false);
    }

    /**
     * A default model stub that have some methods
     */
    private class ModelStub implements Model {
        private final AdminSession adminSession = new AdminSession();

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
        public void removeMachine(Machine machine) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public boolean hasMachine(Machine machine) {
            return false;
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
        public void updateMachine(Machine target, Machine editedMachine) {

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
            return;
        }

        @Override
        public void adminLoginCommitAddressBook() {
            return;
        }

        @Override
        public void adminLogoutCommitAddressBook() {
            return;
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
        public void setLogin(Admin admin) {
            adminSession.setLogin(admin);
        }

        @Override
        public void clearLogin() {
            adminSession.clearLogin();
        }

        @Override
        public boolean isLoggedIn() {
            return adminSession.isAdminLoggedIn();
        }

        @Override
        public Machine findMachine(MachineName machinename) {
            throw new AssertionError("This method should not be called.");
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
