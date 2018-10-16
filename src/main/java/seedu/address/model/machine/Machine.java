package seedu.address.model.machine;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
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
    public static final String MESSAGE_MACHINENAME_CONSTRAINTS =
            "Machine names should only contain alphanumeric characters and spaces, "
                    + "and it should not be blank";
    public static final String MESSAGE_WRONG_STATUS =
            "Status can only contain 'ENABLED' or 'DISABLED'"
                    + "and should not be blank";
    // Identity fields
    private final MachineName machineName;
    //TODO make status be more diverse, like enum
    private final MachineStatus status;

    // Data fields
    //Name is a placeholder. To be replaced by Job class in the future
    private final List<Job> jobs = new ArrayList<>();
    private final Set<Tag> tags = new HashSet<>();


    /**
     * Every field must be present and not null.
     */
    public Machine(MachineName name, List<Job> jobs, Set<Tag> tags, MachineStatus status) {
        requireAllNonNull(name, jobs, tags);
        this.machineName = name;
        this.jobs.addAll(jobs);
        this.tags.addAll(tags);
        this.status = status;
    }

    public Machine(String machineName) {
        this.machineName = new MachineName(machineName);
        this.status = MachineStatus.ENABLED;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidMachine(String test) {
        return test.matches(NAME_VALIDATION_REGEX);
    }

    public MachineName getName() {
        return machineName;
    }

    /**
     * Returns an immutable Job List, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public List<Job> getJobs() {
        return Collections.unmodifiableList(jobs);
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
     * This defines a weaker notion of equality between two machines.
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

    /**
     * this method is meant for compare if two machines have the same name
     * two machines are treated as the same one once they have the same name
     * @param machine
     * @return boolean
     */
    public boolean sameMachine(Machine machine) {
        return this.getName().equals(machine.getName());
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

        builder.append(" Status: ");
        builder.append(getStatus().toString());
        return builder.toString();
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return test.matches(NAME_VALIDATION_REGEX);
    }



    public MachineStatus getStatus() {
        return status;
    }


}
