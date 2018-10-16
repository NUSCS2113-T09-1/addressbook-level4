package seedu.address.logic.commands.admin;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.admin.Admin;
import seedu.address.model.admin.Password;
import seedu.address.model.admin.Username;
import seedu.address.model.job.Job;
import seedu.address.model.job.JobName;
import seedu.address.model.machine.Machine;
import seedu.address.model.person.Person;

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
        new UpdatePasswordCommand(new Username("username"),
                null, new Password("newPW"), new Password("newPW"));
    }

    @Test
    public void constructor_nullNewPassword_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new UpdatePasswordCommand(new Username("username"), new Password("oldPW"),
                null, new Password("newPW"));
    }

    @Test
    public void constructor_nullNewPasswordVerify_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new UpdatePasswordCommand(new Username("username"),
                new Password("oldPW"), new Password("newPW"), null);
    }

    @Test
    public void execute_notLoggedIn_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStub();

        thrown.expect(CommandException.class);
        thrown.expectMessage(UpdatePasswordCommand.MESSAGE_NO_ACCESS);
        new UpdatePasswordCommand(new Username("username"),
                new Password("oldPW"), new Password("newPW"), new Password("newPW")).execute(modelStub, commandHistory);
    }

    @Test
    public void execute_passwordsDoNotMatch_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStub();
        modelStub.setLogin(new Username("dummy"));

        thrown.expect(CommandException.class);
        thrown.expectMessage(UpdatePasswordCommand.MESSAGE_PASSWORDS_DONT_MATCH);
        new UpdatePasswordCommand(new Username("username"),
                new Password("oldPW"), new Password("newPW"), new Password("asdhb")).execute(modelStub, commandHistory);
    }

    @Test
    public void execute_notOwnAccount_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStub();
        modelStub.setLogin(new Username("dummy"));

        thrown.expect(CommandException.class);
        thrown.expectMessage(UpdatePasswordCommand.MESSAGE_ONLY_CHANGE_YOUR_OWN_PW);
        new UpdatePasswordCommand(new Username("username"),
                new Password("oldPW"), new Password("newPW"), new Password("newPW")).execute(modelStub, commandHistory);
    }

    @Test
    public void execute_updateAdmin_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStub();
        modelStub.setLogin(new Username("dummy"));
        modelStub.addAdmin(new Admin(new Username("dummy"),
                new Password("$2a$10$Cj1nZuVAdZIysLK24P8zBe9gRBK.hagqzZJ0zF7i0UFlxlplRCI7e")));
        //weird string is hash for "admin2"

        thrown.expect(CommandException.class);
        thrown.expectMessage(UpdatePasswordCommand.MESSAGE_NOT_VALID_PASSWORD);
        new UpdatePasswordCommand(new Username("dummy"),
                new Password("admin2"), new Password("invalidPW"),
                new Password("invalidPW")).execute(modelStub, commandHistory);
    }

    @Test
    public void execute_updateAdmin_success() throws Exception {
        ModelStub modelStub = new ModelStub();
        modelStub.setLogin(new Username("dummy"));
        modelStub.addAdmin(new Admin(new Username("dummy"),
                new Password("$2a$10$Cj1nZuVAdZIysLK24P8zBe9gRBK.hagqzZJ0zF7i0UFlxlplRCI7e")));
        //weird string is hash for "admin2"

        CommandResult commandResult = new UpdatePasswordCommand(new Username("dummy"),
                new Password("admin2"), new Password("aaaAAA1$"),
                new Password("aaaAAA1$")).execute(modelStub, commandHistory);

        assertEquals(commandResult.feedbackToUser, UpdatePasswordCommand.MESSAGE_SUCCESS);
        assertEquals(modelStub.isLoggedIn(), true);
        assertEquals(modelStub.currentlyLoggedIn(), new Username("dummy"));
        assertEquals(modelStub.adminList, Arrays.asList(new Admin(new Username("dummy"), new Password("aaaAAA1$"))));
    }

    //NOTE: can't test wrong old password because jBcrypt

    /**
     * A default model stub that has some methods failing
     */
    private class ModelStub implements Model {
        final ArrayList<Admin> adminList = new ArrayList<>();

        private boolean loginStatus = false;
        private Username loggedInAdmin = null;

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updatePerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addJob(Job job) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeJob(Job job) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateJob(Job oldJob, Job updatedJob) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Job findJob(JobName name) {
            return null;
        }

        @Override
        public boolean hasJob(Job job) {
            throw new AssertionError("This method should not be called.");
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
        public void updateMachine(Machine target, Machine editedMachine) {

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
        public void setLogin(Username username) {
            this.loggedInAdmin = username;
            this.loginStatus = true;
        }

        @Override
        public void clearLogin() {
            this.loginStatus = false;
            this.loggedInAdmin = null;
        }

        @Override
        public boolean isLoggedIn() {
            return this.loginStatus;
        }

        @Override
        public Username currentlyLoggedIn() {
            return loggedInAdmin;
        }

        @Override
        public Admin findAdmin(Username username) {
            for (Admin admin: adminList) {
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
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
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
        public ObservableList<Job> getFilteredJobList() {
            return null;
        }

        @Override
        public void updateFilteredJobList(Predicate<Job> predicate) {
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
    }
}
