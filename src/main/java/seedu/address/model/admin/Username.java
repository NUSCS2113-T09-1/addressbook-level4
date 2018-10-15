package seedu.address.model.admin;

import static java.util.Objects.requireNonNull;

/**
 * Represents the Username of an admin
 */
public class Username {

    private String value;

    public Username(String username) {
        requireNonNull(username);
        this.value = username;
    }

    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Username // instanceof handles nulls
                && value.equals(((Username) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
