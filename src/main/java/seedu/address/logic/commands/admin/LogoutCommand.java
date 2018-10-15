package seedu.address.logic.commands.admin;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Ends current login session for admin
 */
public class LogoutCommand extends Command {

    public static final String COMMAND_WORD = "logout";
    public static final String MESSAGE_SUCCESS = "logout successful!";
    public static final String MESSAGE_NO_CURRENT_SESSION = "Logout failed! No one is currently logged in.";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "Logout used to get out of admin mode.\n"
            + "Example: logout\n";



    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        if (!model.isLoggedIn()) {
            throw new CommandException(MESSAGE_NO_CURRENT_SESSION);
        }
        model.clearLogin();
        model.commitAddressBook(); //TODO: Not sure what this does

        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return this == other
                || other instanceof LogoutCommand; // instanceof handles nulls

    }

}
