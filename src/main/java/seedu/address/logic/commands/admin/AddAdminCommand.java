package seedu.address.logic.commands.admin;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.admin.Admin;
import seedu.address.model.admin.Password;
import seedu.address.model.admin.Username;

/**
 * Lets one admin add another admin
 */
public class AddAdminCommand extends Command {
    public static final String COMMAND_WORD = "addAdmin";
    public static final String MESSAGE_NO_ACCESS = "You must be logged in to add another admin!";
    public static final String MESSAGE_SUCCESS = "New admin added successfully!";
    public static final String MESSAGE_ADMIN_ALREADY_EXISTS = "This username already exists. Try a new one or login.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + "Used to add another admin.\n"
            + "Example: addAdmin USERNAME PASSWORD PASSWORD\n";
    public static final String MESSAGE_PASSWORDS_DONT_MATCH = "The two password fields don't match! Please try again.";
    public static final String MESSAGE_NOT_VALID_PASSWORD = "Password not valid! You need at least 8 chars, "
            + "where you have at least 1 smaller case, 1 bigger case, 1 symbol, 1 number and no whitespace";

    private final Username username;
    private final Password password;
    private final Password passwordVerify;
    private final Admin toAddIn;

    public AddAdminCommand(Username username, Password password, Password passwordVerify) {
        requireNonNull(username);
        requireNonNull(password);
        requireNonNull(passwordVerify);

        this.username = username;
        this.password = password;
        this.passwordVerify = passwordVerify;
        this.toAddIn = new Admin(username, password);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.isLoggedIn()) {
            throw new CommandException(MESSAGE_NO_ACCESS);
        }

        if (!password.equals(passwordVerify)) {
            throw new CommandException(MESSAGE_PASSWORDS_DONT_MATCH);
        }

        if (model.findAdmin(username) != null) {
            throw new CommandException(MESSAGE_ADMIN_ALREADY_EXISTS);
        }

        PasswordValidator pwVal = new PasswordValidator();

        if (!pwVal.isValidPassword(this.password)) {
            throw new CommandException(MESSAGE_NOT_VALID_PASSWORD);
        }

        model.addAdmin(toAddIn);
        model.commitAddressBook();  //TODO: not sure what this does;

        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddAdminCommand // instanceof handles nulls
                && username.equals(((AddAdminCommand) other).username)
                && password.equals(((AddAdminCommand) other).password)
                && password.equals(((AddAdminCommand) other).passwordVerify)); // state check
    }

}
