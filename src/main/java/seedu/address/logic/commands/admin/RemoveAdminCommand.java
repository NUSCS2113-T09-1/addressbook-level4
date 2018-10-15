package seedu.address.logic.commands.admin;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.admin.Admin;
import seedu.address.model.admin.Username;

/**
 * Lets one admin remove another admin
 */
public class RemoveAdminCommand extends Command {
    public static final String COMMAND_WORD = "removeAdmin";
    public static final String MESSAGE_NO_ACCESS = "You must be logged in to remove another admin!";
    public static final String MESSAGE_SUCCESS = "Admin removed successfully!";
    public static final String MESSAGE_NO_SUCH_ADMIN = "No such admins exist. Have you typed the username correctly?";
    public static final String MESSAGE_CANT_DELETE_LAST_ADMIN = "You can't delete the last admin.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + "Used to remove another admin.\n"
            + "Example: removeAdmin USERNAME\n";

    private final Username username;

    public RemoveAdminCommand(Username username) {
        requireNonNull(username);
        this.username = username;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        if (!model.isLoggedIn()) {
            throw new CommandException(MESSAGE_NO_ACCESS);
        }

        Admin toRemove = model.findAdmin(username);
        if (toRemove == null) {
            throw new CommandException(MESSAGE_NO_SUCH_ADMIN);
        }

        if (model.numAdmins() == 1) {
            throw new CommandException(MESSAGE_CANT_DELETE_LAST_ADMIN);
        }

        model.removeAdmin(toRemove);

        //Logout current admin if deleted him/herself
        if (model.currentlyLoggedIn().equals(username)) {
            model.clearLogin();
        }

        model.commitAddressBook();  //TODO: not sure what this does;

        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemoveAdminCommand // instanceof handles nulls
                && username.equals(((RemoveAdminCommand) other).username)); // state check
    }

}
