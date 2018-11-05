package seedu.address.model.job;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.job.Name.NAME_VALIDATION_REGEX;

import java.util.Objects;

/**
 * Represents a JobOwner in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class JobOwner {
    public static final String MESSAGE_OWNERNAME_CONSTRAINTS =
        "Owner names should only contain alphanumeric characters and spaces, "
            + "and it should not be blank";
    public static final String MESSAGE_JOBOWNER_CONSTRAINTS =
        "Job owner's name should only contain alphanumeric characters and spaces, and it should not be blank";

    // Identity fields
    private final Name name;
    
    /**
     * Every field must be present and not null.
     */
    public JobOwner(Name name) {
        requireNonNull(name);
        this.name = name;
    }
    

    public Name getName() {
        return name;
    }
    

    /**
     * Returns true if both jobOwners of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two jobOwners.
     */
    public boolean isSameJobOwner(JobOwner otherJobOwner) {
        if (otherJobOwner == this) {
            return true;
        }

        return otherJobOwner != null && otherJobOwner.getName().equals(getName());
    }

    /**
     * Returns true if both jobOwners have the same identity and data fields.
     * This defines a stronger notion of equality between two jobOwners.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof JobOwner)) {
            return false;
        }

        JobOwner otherJobOwner = (JobOwner) other;
        return otherJobOwner.getName().equals(getName());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName());
        return builder.toString();
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidJobOwner(String test) {
        return test.matches(NAME_VALIDATION_REGEX);
    }

}
