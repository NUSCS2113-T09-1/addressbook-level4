package seedu.address.logic.commands.admin;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
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

public class RemoveAdminCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullUsername_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new RemoveAdminCommand(null);
    }

    @Test
    public void execute_notLoggedIn_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStub();

        thrown.expect(CommandException.class);
        thrown.expectMessage(RemoveAdminCommand.MESSAGE_NO_ACCESS);
        new RemoveAdminCommand(new Username("dummy")).execute(modelStub, commandHistory);
    }

    @Test
    public void execute_doesNotExists_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStub();
        Admin admin = new Admin(new Username("dummy"), new Password("oldPW"));
        modelStub.setLogin(admin);

        thrown.expect(CommandException.class);
        thrown.expectMessage(RemoveAdminCommand.MESSAGE_NO_SUCH_ADMIN);
        new RemoveAdminCommand(new Username("noSuchUsername")).execute(modelStub, commandHistory);
    }

    @Test
    public void execute_removeOnlyAdmin_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStub();
        Admin admin = new Admin(new Username("dummy"), new Password("oldPW"));
        modelStub.setLogin(admin);
        modelStub.addAdmin(new Admin(new Username("dummyLogin"), new Password("dummyPassword")));

        thrown.expect(CommandException.class);
        thrown.expectMessage(RemoveAdminCommand.MESSAGE_CANT_DELETE_LAST_ADMIN);
        new RemoveAdminCommand(new Username("dummyLogin")).execute(modelStub, commandHistory);
    }

    @Test
    public void execute_removeNotLoggedIn_success() throws Exception {
        ModelStub modelStub = new ModelStub();
        Admin admin = new Admin(new Username("dummy"), new Password("oldPW"));
        modelStub.setLogin(admin);
        modelStub.addAdmin(new Admin(new Username("dummyLogin"), new Password("dummyPW")));
        modelStub.addAdmin(new Admin(new Username("dummyRemove"), new Password("dummyPW")));

        CommandResult commandResult =
            new RemoveAdminCommand(new Username("dummyRemove")).execute(modelStub, commandHistory);

        assertEquals(commandResult.feedbackToUser, RemoveAdminCommand.MESSAGE_SUCCESS);
        assertEquals(modelStub.currentlyLoggedIn(), admin);
        assertEquals(modelStub.isLoggedIn(), true);
    }

    @Test
    public void execute_removeLoggedIn_success() throws Exception {
        ModelStub modelStub = new ModelStub();
        Admin admin = new Admin(new Username("dummyLogin"), new Password("dummyPW"));
        modelStub.setLogin(admin);
        modelStub.addAdmin(admin);
        modelStub.addAdmin(new Admin(new Username("dummyOther"), new Password("dummyPW")));

        CommandResult commandResult =
            new RemoveAdminCommand(new Username("dummyLogin")).execute(modelStub, commandHistory);

        assertEquals(commandResult.feedbackToUser, RemoveAdminCommand.MESSAGE_SUCCESS);
        assertEquals(modelStub.currentlyLoggedIn(), null);
        assertEquals(modelStub.isLoggedIn(), false);
    }

    /**
     * A default model stub that has some methods failing
     */
    private class ModelStub implements Model {
        final ArrayList<Admin> adminList = new ArrayList<>();
        final AdminSession adminSession = new AdminSession();

        @Override
        public void resetData(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addJob(Job job) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteJob(JobName job) {

        }

        @Override
        public boolean hasJob(Job job) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
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
            return;
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
            throw new AssertionError("This method should not be called.");
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
            adminList.add(admin);
        }

        @Override
        public void removeAdmin(Admin admin) {
            adminList.remove(admin);
        }

        @Override
        public void updateAdmin(Admin admin, Admin updatedAdmin) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Admin findAdmin(Username username) {
            for (Admin admin : adminList) {
                if (admin.getUsername().equals(username)) {
                    return admin;
                }
            }
            return null;
        }

        @Override
        public int numAdmins() {
            return adminList.size();
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
            throw new AssertionError("This method should not be called.");
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
            return adminSession.getLoggedInAdmin();
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
