package seedu.address.logic.commands.admin;

import static org.junit.Assert.assertEquals;

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
import seedu.address.model.admin.Username;
import seedu.address.model.job.Job;
import seedu.address.model.job.JobName;
import seedu.address.model.machine.Machine;
import seedu.address.model.person.Person;

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
        modelStub.setLogin(new Username("dummyUsername"));
        CommandResult commandResult = new LogoutCommand().execute(modelStub, commandHistory);

        assertEquals(commandResult.feedbackToUser, LogoutCommand.MESSAGE_SUCCESS);
        assertEquals(modelStub.isLoggedIn(), false);
    }

    //TODO: did not test equals

    /**
     * A default model stub that have some methods
     */
    private class ModelStub implements Model {
        private boolean loginStatus = false;

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
        public void setLogin(Username username) {
            this.loginStatus = true;
        }

        @Override
        public void clearLogin() {
            this.loginStatus = false;
        }

        @Override
        public boolean isLoggedIn() {
            return this.loginStatus;
        }

        @Override
        public Username currentlyLoggedIn() {
            return null;
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
