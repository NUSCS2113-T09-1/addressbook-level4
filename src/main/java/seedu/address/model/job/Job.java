package seedu.address.model.job;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.machine.Machine;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Represents a Printing job in MakerManager.
 * Morphed from the Person class in original Addressbook
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Job {
    public static final String MESSAGE_NAME_CONSTRAINTS =
            "Job names should only contain alphanumeric characters and spaces, "
                    + "and it should not be blank";

    //Identity field
    private JobName name;
    private Machine machine;
    private TimeStamp time;
    private Person owner;

    //Data field
    private final Set<Tag> tags = new HashSet<>();
    private JobPriority priority;

    /**
     * Every field must be present and not null.
     */
    public Job(Name name, Machine machine, Person owner, JobPriority priority, Set<Tag> tags) {
        requireAllNonNull(name, machine, owner, tags, priority);
        this.name = (JobName) name;
        this.machine = machine;
        this.owner = owner;
        this.tags.addAll(tags);
        this.priority = priority;

        time = new TimeStamp();
    }

    public JobPriority getPriority() {
        return priority;
    }

    public Name getName() {
        return name;
    }

    public Machine getMachine() {
        return machine;
    }

    public TimeStamp getTime() {
        return time;
    }

    public Person getOwner() {
        return owner;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /*
    * In case the function of adding new notes during the process in needed
    * have to change the note to a set
    public void addNote(JobNote newNote) {
        notes.add(newNote);
    }
    */

    public void setName(String newName) {
        name = new JobName(newName);
    }

    public void setMachine(Machine newMachine) {
        machine = newMachine;
    }

    public void setOwner(Person newOwner) {
        owner = newOwner;
    }

    /**
     * Returns true if both jobs of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameJob(Job otherJob) {
        if (otherJob == this) {
            return true;
        }

        return otherJob != null
                && otherJob.getName().equals(getName())
                && (otherJob.getMachine().equals(getMachine())
                || otherJob.getTime().equals(getTime())
                || otherJob.getOwner().equals(getOwner()));
    }

    /**
     * Returns true if both jobs have the same identity and data fields.
     * This defines a stronger notion of equality between two jobs.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Job)) {
            return false;
        }

        Job otherJob = (Job) other;
        return otherJob.getName().equals(getName())
                && otherJob.getMachine().equals(getMachine())
                && otherJob.getOwner().equals(getOwner())
                && otherJob.getTime().equals(getTime())
                && otherJob.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, machine, time, owner, tags);
    }


}
