package seedu.address.logic.commands.admin;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
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
        modelStub.setLogin(new Username("dummyLogin"));

        thrown.expect(CommandException.class);
        thrown.expectMessage(RemoveAdminCommand.MESSAGE_NO_SUCH_ADMIN);
        new RemoveAdminCommand(new Username("noSuchUsername")).execute(modelStub, commandHistory);
    }

    @Test
    public void execute_removeOnlyAdmin_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStub();
        modelStub.setLogin(new Username("dummyLogin"));
        modelStub.addAdmin(new Admin(new Username("dummyLogin"), new Password("dummyPassword")));

        thrown.expect(CommandException.class);
        thrown.expectMessage(RemoveAdminCommand.MESSAGE_CANT_DELETE_LAST_ADMIN);
        new RemoveAdminCommand(new Username("dummyLogin")).execute(modelStub, commandHistory);
    }

    @Test
    public void execute_removeNotLoggedIn_success() throws Exception {
        ModelStub modelStub = new ModelStub();
        modelStub.setLogin(new Username("dummyLogin"));
        modelStub.addAdmin(new Admin(new Username("dummyLogin"), new Password("dummyPW")));
        modelStub.addAdmin(new Admin(new Username("dummyRemove"), new Password("dummyPW")));

        CommandResult commandResult = new RemoveAdminCommand(new Username("dummyRemove"))
                .execute(modelStub, commandHistory);

        assertEquals(commandResult.feedbackToUser, RemoveAdminCommand.MESSAGE_SUCCESS);
        assertEquals(modelStub.loggedInAdmin, new Username("dummyLogin"));
        assertEquals(modelStub.loginStatus, true);
    }

    @Test
    public void execute_removeLoggedIn_success() throws Exception {
        ModelStub modelStub = new ModelStub();
        modelStub.setLogin(new Username("dummyLogin"));
        modelStub.addAdmin(new Admin(new Username("dummyLogin"), new Password("dummyPW")));
        modelStub.addAdmin(new Admin(new Username("dummyOther"), new Password("dummyPW")));

        CommandResult commandResult = new RemoveAdminCommand(new Username("dummyLogin"))
                .execute(modelStub, commandHistory);

        assertEquals(commandResult.feedbackToUser, RemoveAdminCommand.MESSAGE_SUCCESS);
        assertEquals(modelStub.loggedInAdmin, null);
        assertEquals(modelStub.loginStatus, false);
    }

    //TODO: equals not tested

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
            throw new AssertionError("This method should not be called.");
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
