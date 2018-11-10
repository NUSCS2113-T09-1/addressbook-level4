package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MACHINE_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.job.Priority.NORMAL;
import static seedu.address.model.job.Priority.URGENT;
import static seedu.address.model.job.Status.CANCELLED;
import static seedu.address.model.job.Status.ONGOING;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.machine.EditMachineCommand;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.admin.Admin;
import seedu.address.model.job.Priority;
import seedu.address.model.job.Status;
import seedu.address.model.machine.Machine;
import seedu.address.model.machine.MachineNameContainsKeywordsPredicate;
import seedu.address.model.machine.MachineStatus;
import seedu.address.testutil.builders.EditMachineDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";

    public static final String VALID_MACHINE_NAME_ULTIMAKER = "ULTIMAKER";
    public static final String VALID_MACHINE_NAME_ENDER = "ENDER";

    public static final String VALID_MACHINE_STATUS_ENABLED = "ENABLED";
    public static final String VALID_MACHINE_STATUS_DISABLED = "DISABLED";

    //--------------------------Valid Job Configuration String Parameters------------------------
    public static final String VALID_JOBNAME_PROONE = "proone";
    public static final String VALID_JOBNAME_PROTWO = "protwo";
    public static final String VALID_JOBOWNER_AMY = "Amy";
    public static final String VALID_JOBOWNER_BOB = "Bob";
    public static final String VALID_JOBMACHINE_PTONE = "ptone";
    public static final String VALID_JOBMACHINE_PTTWO = "pttwo";
    //TODO: added time should be added in the future
    public static final String VALID_TAG_CSMODULE = "csmoudule";
    public static final String VALID_TAG_CEGMODULE = "cegmodule";
    public static final Priority VALID_JOBPRIORITY_URGENT = URGENT;
    public static final Priority VALID_JOBPRIORITY_NORMAL = NORMAL;
    public static final String VALID_JOBDURATION_FIVE = "5";
    public static final String VALID_JOBDURATION_TEN = "10";
    public static final String VALID_JOBNOTE_IDCP = "This job is meant for the iDCP project";
    public static final String VALID_JOBNOTE_ANOTHERIDCP = "This job is really meant for the iDCP project";
    public static final Status VALID_JOBSTATUS_CANCELLED = CANCELLED;
    public static final Status VALID_JOBSTATUS_ONGOING = ONGOING;
    public static final String VALID_JOBADDEDTIME_SOMEDAY = "sample day";
    public static final String VALID_JOBADDEDTIME_SOMETIME = "3/10 13:19:20";

    public static final String MACHINE_NAME_DESC_ULTIMAKER = " " + PREFIX_NAME + VALID_MACHINE_NAME_ULTIMAKER;
    public static final String MACHINE_NAME_DESC_ENDER = " " + PREFIX_NAME + VALID_MACHINE_NAME_ULTIMAKER;
    public static final String MACHINESTATUS_DESC_ENABLED = " " + PREFIX_MACHINE_STATUS + VALID_MACHINE_STATUS_ENABLED;
    public static final String MACHINESTATUS_DESC_DISABLED =
        " " + PREFIX_MACHINE_STATUS + VALID_MACHINE_STATUS_DISABLED;

    // '&' not allowed in names
    public static final String INVALID_MACHINENAME_DESC = " " + PREFIX_NAME + "ultimaker&";

    // 'a' not allowed in phones
    public static final String INVALID_MACHINESTATUS_DESC = " " + PREFIX_MACHINE_STATUS + "PEANUTMAN";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the {@code actualModel} matches {@code expectedModel} <br>
     * - the {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
                                            String expectedMessage, Model expectedModel) {
        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);
        try {
            CommandResult result = command.execute(actualModel, actualCommandHistory);
            assertEquals(expectedMessage, result.feedbackToUser);
            assertEquals(expectedModel, actualModel);
            assertEquals(expectedCommandHistory, actualCommandHistory);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    public static final EditMachineCommand.EditMachineDescriptor DESC_ULTIMAKER;
    public static final EditMachineCommand.EditMachineDescriptor DESC_ENDER;

    static {
        DESC_ULTIMAKER =
            new EditMachineDescriptorBuilder().withName(VALID_MACHINE_NAME_ULTIMAKER).withStatus(MachineStatus.ENABLED)
                .build();
        DESC_ENDER =
            new EditMachineDescriptorBuilder().withName(VALID_MACHINE_NAME_ENDER).withStatus(MachineStatus.ENABLED)
                .build();
    }


    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book and the filtered machine and admin list in the {@code actualModel} remain unchanged <br>
     * - {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandFailure(Command command, Model actualModel, CommandHistory actualCommandHistory,
                                            String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Machine> expectedFilteredMahcineList = new ArrayList<>(actualModel.getFilteredMachineList());
        List<Admin> expectedFilteredAdminList = new ArrayList<>(actualModel.getFilteredAdminList());

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            command.execute(actualModel, actualCommandHistory);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedAddressBook, actualModel.getAddressBook());
            assertEquals(expectedFilteredMahcineList, actualModel.getFilteredMachineList());
            assertEquals(expectedFilteredAdminList, actualModel.getFilteredAdminList());
            assertEquals(expectedCommandHistory, actualCommandHistory);
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the machine and admin at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showMachineAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredMachineList().size());
        assertTrue(targetIndex.getZeroBased() < model.getFilteredAdminList().size());


        Machine machine = model.getFilteredMachineList().get(targetIndex.getZeroBased());
        Admin admin = model.getFilteredAdminList().get(targetIndex.getZeroBased());

        final String[] splitMachineName = machine.getName().fullName.split("\\s+");
        final String[] splitAdminName = admin.getUsername().toString().split("\\s+");

        model.updateFilteredMachineList(new MachineNameContainsKeywordsPredicate(Arrays.asList(splitMachineName[0])));

        assertEquals(1, model.getFilteredMachineList().size());
    }

    /**
     * Deletes the first machine in {@code model}'s filtered list from {@code model}'s address book.
     */
    public static void deleteFirstMachine(Model model) {
        Machine firstMachine = model.getFilteredMachineList().get(0);
        model.removeMachine(firstMachine);
        model.commitAddressBook();
    }

    /**
     * Deletes the first admin {@code model}'s filtered list from {@code model}'s address book.
     */
    public static void deleteFirstAdmin(Model model) {
        Admin firstAdmin = model.getFilteredAdminList().get(0);
        model.removeAdmin(firstAdmin);
        model.commitAddressBook();
    }

}
