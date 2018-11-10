package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";
    private static final String MESSAGE_ACCESS_DENIED =
        "Non-admin user is not allowed to clear the maker manager";


    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.isLoggedIn()) {
            throw new CommandException(MESSAGE_ACCESS_DENIED);
        }

        AddressBook clearedBook = new AddressBook();
        clearedBook.setAdmins(model.getAddressBook().getAdminList());
        clearedBook.setAdminsSession(model.getAddressBook().getAdminSession());
        model.resetData(clearedBook);
        model.commitAddressBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
