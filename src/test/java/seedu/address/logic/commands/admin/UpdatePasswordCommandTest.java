package seedu.address.logic.commands.admin;

import static org.junit.Assert.assertEquals;

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

public class UpdatePasswordCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullUsername_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new UpdatePasswordCommand(null, new Password("oldPW"), new Password("newPW"), new Password("newPW"));
    }

    @Test
    public void constructor_nullOldPassword_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new UpdatePasswordCommand(new Username("username"), null, new Password("newPW"), new Password("newPW"));
    }

    @Test
    public void constructor_nullNewPassword_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new UpdatePasswordCommand(new Username("username"), new Password("oldPW"), null, new Password("newPW"));
    }

    @Test
    public void constructor_nullNewPasswordVerify_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new UpdatePasswordCommand(new Username("username"), new Password("oldPW"), new Password("newPW"), null);
    }

    @Test
    public void execute_notLoggedIn_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStub();

        thrown.expect(CommandException.class);
        thrown.expectMessage(UpdatePasswordCommand.MESSAGE_NO_ACCESS);
        new UpdatePasswordCommand(new Username("username"), new Password("oldPW"), new Password("newPW"),
            new Password("newPW")).execute(modelStub, commandHistory);
    }

    @Test
    public void execute_passwordsDoNotMatch_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStub();
        Admin admin = new Admin(new Username("dummy"), new Password("oldPW"));
        modelStub.setLogin(admin);
        thrown.expect(CommandException.class);
        thrown.expectMessage(UpdatePasswordCommand.MESSAGE_PASSWORDS_DONT_MATCH);
        new UpdatePasswordCommand(new Username("dummy"), new Password("oldPW"), new Password("newPW"),
            new Password("asdhb")).execute(modelStub, commandHistory);
    }

    @Test
    public void execute_notOwnAccount_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStub();
        Admin admin = new Admin(new Username("dummy"), new Password("oldPW"));
        modelStub.setLogin(admin);

        thrown.expect(CommandException.class);
        thrown.expectMessage(UpdatePasswordCommand.MESSAGE_ONLY_CHANGE_YOUR_OWN_PW);
        new UpdatePasswordCommand(new Username("username"), new Password("oldPW"), new Password("newPW"),
            new Password("newPW")).execute(modelStub, commandHistory);
    }

    @Test
    public void execute_updateAdmin_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStub();
        Admin admin = new Admin(new Username("dummy"), new Password("oldPW"));
        modelStub.setLogin(admin);
        modelStub.addAdmin(new Admin(new Username("dummy"),
            new Password("$2a$10$Cj1nZuVAdZIysLK24P8zBe9gRBK.hagqzZJ0zF7i0UFlxlplRCI7e")));
        //weird string is hash for "admin2"

        thrown.expect(CommandException.class);
        thrown.expectMessage(UpdatePasswordCommand.MESSAGE_NOT_VALID_PASSWORD);
        new UpdatePasswordCommand(new Username("dummy"), new Password("admin2"), new Password("invalidPW"),
            new Password("invalidPW")).execute(modelStub, commandHistory);
    }

    @Test
    public void execute_updateAdmin_success() throws Exception {
        ModelStub modelStub = new ModelStub();
        Admin admin = new Admin(new Username("dummy"),
            new Password("$2a$10$Cj1nZuVAdZIysLK24P8zBe9gRBK.hagqzZJ0zF7i0UFlxlplRCI7e"));
        modelStub.setLogin(admin);
        modelStub.addAdmin(new Admin(new Username("dummy"),
            new Password("$2a$10$Cj1nZuVAdZIysLK24P8zBe9gRBK.hagqzZJ0zF7i0UFlxlplRCI7e")));
        //weird string is hash for "admin2"

        CommandResult commandResult =
            new UpdatePasswordCommand(new Username("dummy"), new Password("admin2"), new Password("aaaAAA1$"),
                new Password("aaaAAA1$")).execute(modelStub, commandHistory);

        assertEquals(commandResult.feedbackToUser, UpdatePasswordCommand.MESSAGE_SUCCESS);
        assertEquals(modelStub.isLoggedIn(), true);
        assertEquals(modelStub.currentlyLoggedIn(), admin);
        assertEquals(modelStub.adminList, Arrays.asList(new Admin(new Username("dummy"), new Password("aaaAAA1$"))));
    }

    //NOTE: can't test wrong old password because jBcrypt

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
            removeAdmin(admin);
            addAdmin(updatedAdmin);
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
