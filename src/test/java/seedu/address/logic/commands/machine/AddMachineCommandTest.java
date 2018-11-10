package seedu.address.logic.commands.machine;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.testdata.ValidMachines.getMachinesData;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.admin.Admin;
import seedu.address.model.admin.Password;
import seedu.address.model.admin.Username;
import seedu.address.model.machine.Machine;
import seedu.address.testutil.MachineBuilder;
import seedu.address.testutil.testdata.ValidMachines;

public class AddMachineCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getMachinesData(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setup() {
        model.setLogin(new Admin(new Username("dummyUsername"), new Password("aaaAAA123$")));
    }

    @Test
    public void constructor_nullMachine_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddMachineCommand(null);
    }

    @Test
    public void execute_machineAcceptedByModel_successful() throws Exception {
        Machine toBeAdded = new MachineBuilder().build();
        AddMachineCommand addMachineCommand = new AddMachineCommand(toBeAdded);
        String expectedMessage = String.format(AddMachineCommand.MESSAGE_SUCCESS, toBeAdded);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.addMachine(toBeAdded);
        expectedModel.commitAddressBook();

        assertCommandSuccess(addMachineCommand, model, commandHistory, expectedMessage, expectedModel);

        // undo -> reverts makerManager back to previous state
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first Machine edited again
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);

    }

    @Test
    public void execute_exactDuplicateMachine_failure() throws Exception {
        Machine toBeAdded = model.getFilteredMachineList().get(0);
        AddMachineCommand addMachineCommand = new AddMachineCommand(toBeAdded);
        String expectedMessage = String.format(AddMachineCommand.MESSAGE_DUPLICATE_MACHINE, toBeAdded);

        assertCommandFailure(addMachineCommand, model, commandHistory, expectedMessage);

        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void execute_sameNameMachine_failure() throws Exception {
        Machine toBeAdded =
            new MachineBuilder().withName(model.getFilteredMachineList().get(0).getName().toString()).build();
        AddMachineCommand addMachineCommand = new AddMachineCommand(toBeAdded);
        String expectedMessage = String.format(AddMachineCommand.MESSAGE_DUPLICATE_MACHINE, toBeAdded);

        assertCommandFailure(addMachineCommand, model, commandHistory, expectedMessage);

        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void execute_notLoggedIn_failure() {
        model.clearLogin();
        Machine toBeAdded = new MachineBuilder().build();
        AddMachineCommand addMachineCommand = new AddMachineCommand(toBeAdded);
        String expectedMessage = String.format(AddMachineCommand.MESSAGE_ACCESS_DENIED, toBeAdded);

        assertCommandFailure(addMachineCommand, model, commandHistory, expectedMessage);

        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);

    }

    @Test
    public void equals() {
        final AddMachineCommand standardCommand = new AddMachineCommand(ValidMachines.JJPRINTER);

        AddMachineCommand commandWithSameValues = new AddMachineCommand(ValidMachines.JJPRINTER);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different targetName -> returns false
        assertFalse(standardCommand.equals(new AddMachineCommand(ValidMachines.TYPRINTER)));

    }

}
