package seedu.address.logic.commands.admin;

import seedu.address.model.admin.Password;

/**
 * Used for Validation on AddAdmin and UpdateAdmin
 * Credits to this answer: https://stackoverflow.com/a/8148112
 */
public class PasswordValidator {
    /**
     * Returns whether the password provided is strong enough to be accepted
     * @param password
     */
    public boolean isValidPassword(Password password) {
        String pwd = password.toString();
        String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
        if (pwd.matches(pattern)) {
            return true;
        } else {
            return false;
        }

    }
}
