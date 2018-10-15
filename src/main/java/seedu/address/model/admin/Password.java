package seedu.address.model.admin;

import static java.util.Objects.requireNonNull;

/**
 * Represents the hash of the password of an admin
 */
public class Password {

    private String value;

    public Password(String password) {
        requireNonNull(password);
        this.value = password;
    }

    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Password // instanceof handles nulls
                && value.equals(((Password) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
