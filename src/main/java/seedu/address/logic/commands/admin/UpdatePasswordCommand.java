package seedu.address.logic.commands.admin;

import static java.util.Objects.requireNonNull;

import org.mindrot.jbcrypt.BCrypt;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.admin.Admin;
import seedu.address.model.admin.Password;
import seedu.address.model.admin.Username;

/**
 * Command used to update Password of currently logged in Admin
 */
public class UpdatePasswordCommand extends Command {
    public static final String COMMAND_WORD = "updatePassword";
    public static final String MESSAGE_NO_ACCESS = "You must be logged in to change your password!";
    public static final String MESSAGE_SUCCESS = "Your password has been changed successfully!";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " is used to change password of logged in admin.\n"
            + "Example: udpatePassword USERNAME OLD_PW NEW_PW NEW_PW_VERIFY\n";
    public static final String MESSAGE_ONLY_CHANGE_YOUR_OWN_PW = "You can only change your own password.";
    public static final String MESSAGE_PASSWORDS_DONT_MATCH = "The two password fields don't match! Please try again.";
    public static final String MESSAGE_WRONG_OLD_DETAILS = "Your old password doesn't match.";
    public static final String MESSAGE_NOT_VALID_PASSWORD = "Password not valid! You need at least 8 chars, "
            + "where you have at least 1 smaller case, 1 bigger case, 1 symbol, 1 number and no whitespace";

    private final Username username;
    private final Password oldPassword;
    private final Password newPassword;
    private final Password passwordVerify;

    public UpdatePasswordCommand(Username username, Password oldPassword,
                                 Password newPassword, Password passwordVerify) {
        requireNonNull(username);
        requireNonNull(oldPassword);
        requireNonNull(newPassword);
        requireNonNull(passwordVerify);

        this.username = username;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.passwordVerify = passwordVerify;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.isLoggedIn()) {
            throw new CommandException(MESSAGE_NO_ACCESS);
        }

        if (!newPassword.equals(passwordVerify)) {
            throw new CommandException(MESSAGE_PASSWORDS_DONT_MATCH);
        }

        if (!username.equals(model.currentlyLoggedIn())) {
            throw new CommandException(MESSAGE_ONLY_CHANGE_YOUR_OWN_PW);
        }

        PasswordValidator pwVal = new PasswordValidator();

        if (!pwVal.isValidPassword(this.newPassword)) {
            throw new CommandException(MESSAGE_NOT_VALID_PASSWORD);
        }

        final Admin toUpdate;
        final Admin updatedAdmin;

        if (!BCrypt.checkpw(oldPassword.toString(), model.findAdmin(username).getPassword().toString())) {
            throw new CommandException(MESSAGE_WRONG_OLD_DETAILS);
        } else {
            toUpdate = new Admin(username, model.findAdmin(username).getPassword());
            updatedAdmin = new Admin(username, newPassword);
        }



        model.updateAdmin(toUpdate, updatedAdmin);
        model.commitAddressBook();  //TODO: not sure what this does;

        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UpdatePasswordCommand // instanceof handles nulls
                && username.equals(((UpdatePasswordCommand) other).username)
                && newPassword.equals(((UpdatePasswordCommand) other).newPassword)
                && oldPassword.equals(((UpdatePasswordCommand) other).oldPassword)
                && passwordVerify.equals(((UpdatePasswordCommand) other).passwordVerify)); // state check
    }

}
