package seedu.address.model.machine;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.job.Job;
import seedu.address.model.tag.Tag;

/**
 * Represents a Machine in the lab. Morphed from Persons.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Machine {

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String NAME_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";
    public static final String MESSAGE_NAME_CONSTRAINTS =
            "Names should only contain alphanumeric characters and spaces, "
                    + "and it should not be blank";

    // Identity fields
    private final MachineName machineName;
    //TODO make status be more diverse, like enum
    private final boolean status;

    // Data fields
    //Name is a placeholder. To be replaced by Job class in the future
    private final ArrayList<Job> jobs = new ArrayList<>();
    private final Set<Tag> tags = new HashSet<>();


    /**
     * Every field must be present and not null.
     */
    public Machine(MachineName name, ArrayList<Job> jobs, Set<Tag> tags, boolean status) {
        requireAllNonNull(name, jobs, tags);
        this.machineName = name;
        this.jobs.addAll(jobs);
        this.tags.addAll(tags);
        this.status = status;
    }

    public MachineName getName() {
        return machineName;
    }

    /**
     * Returns an immutable Job List, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public ArrayList<Job> getJobs() {
        return (ArrayList<Job>) Collections.unmodifiableList(jobs);
    }
    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }


    /**
     * Returns true if both persons of the same name and same list of Jobs.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameMachine(Machine otherMachine) {
        if (otherMachine == this) {
            return true;
        }

        return otherMachine != null
                && otherMachine.getName().equals(getName())
                && otherMachine.getJobs().equals(getJobs());
    }

    /**
     * Returns true if both machines have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Machine)) {
            return false;
        }

        Machine otherMachine = (Machine) other;
        return otherMachine.getName().equals(getName())
                && otherMachine.getJobs().equals(getJobs())
                && otherMachine.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(machineName, jobs, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Tags: ");
        getTags().forEach(builder::append);

        builder.append(" Jobs: ");
        getJobs().forEach(builder::append);

        return builder.toString();
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return test.matches(NAME_VALIDATION_REGEX);
    }



    public boolean getStatus() {
        return status;
    }
}
