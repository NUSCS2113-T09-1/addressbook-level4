package seedu.address.model.job;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.job.Status.ONGOING;
import static seedu.address.model.job.Status.PAUSED;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.job.exceptions.JobNotStartedException;
import seedu.address.model.machine.MachineName;
import seedu.address.model.tag.Tag;

/**
 * Represents a Printing job in MakerManager.
 * Morphed from the JobOwner class in original Addressbook
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Job {
    public static final String MESSAGE_NAME_CONSTRAINTS = "Job names should only contain alphanumeric characters and "
        + "spaces, " + "and it should not be blank";

    public static final String MESSAGE_NOTE_CONSTRAINTS = "Job notes should only contain alphanumeric characters and "
        + "spaces, " + "and it should not be blank";

    public static final String MEEEAGE_PRIORITY_CONSTRAINTS = "Job priority can only be URGENT, HIGH and NORMAL";

    private static final Logger logger = LogsCenter.getLogger(Job.class);

    //Identity field
    private JobName name;
    private MachineName machineName;
    private TimeStamp startTime;
    private JobOwner owner;
    private final TimeStamp addedTime;

    //Data field
    private final Set<Tag> tags = new HashSet<>();
    private JobNote jobNote;
    private Priority priority;
    private Status status;
    private long duration;

    /**
     * Every field must be present and not null.
     * TODO: Need to validate all these somewhere
     */
    public Job(JobName name, MachineName machine, JobOwner owner, Priority priority, long duration, JobNote jobNote,
               Set<Tag> tags) {
        requireAllNonNull(name, machine, owner, tags);
        this.name = name;
        this.machineName = machine;
        this.owner = owner;
        this.priority = priority;
        this.duration = duration;
        this.jobNote = jobNote;
        this.tags.addAll(tags);

        this.status = Status.QUEUED;
        startTime = new TimeStamp();
        addedTime = new TimeStamp();
        //pausedTime = new TimeStamp();
    }

    /**
     * Recovers a job object from the storage file
     */
    public Job(JobName name, MachineName machine, JobOwner owner, TimeStamp addedTime, TimeStamp startTime,
               Priority priority, Status status, long duration, JobNote jobNote, Set<Tag> tags) {
        requireAllNonNull(name, machine, owner, tags);
        this.name = name;
        this.machineName = machine;
        this.owner = owner;
        this.addedTime = addedTime;
        this.priority = priority;
        this.status = status;
        this.duration = duration;
        this.jobNote = jobNote;
        this.startTime = startTime;
        this.tags.addAll(tags);
    }

    public Job(Job toBeCopied) {
        this.name = toBeCopied.name;
        this.machineName = toBeCopied.machineName;
        this.owner = toBeCopied.owner;
        this.addedTime = new TimeStamp(toBeCopied.addedTime.getDate().getTime());
        this.priority = toBeCopied.priority;
        this.status = toBeCopied.status;
        this.duration = toBeCopied.duration;
        this.jobNote = toBeCopied.jobNote;
        this.startTime = new TimeStamp(toBeCopied.startTime.getDate().getTime());
        this.tags.addAll(toBeCopied.tags);
    }


    /**
     * checks if a job has been finished
     */
    public boolean isFinished() throws JobNotStartedException {

        if (this.status == ONGOING) {
            TimeStamp current = new TimeStamp();
            return TimeStamp.timeDifference(startTime, current) > this.duration;
        } else {
            throw new JobNotStartedException();
        }
    }

    public JobNote getJobNote() {
        return this.jobNote;
    }

    public long getDuration() {
        return this.duration;
    }

    public String getReadableDurationString() {
        //return this.duration + "";
        TimeStamp t = new TimeStamp(this.duration);
        return t.showAsDuration();
    }

    public float getPercentageCompletion() {
        TimeStamp t = new TimeStamp(this.duration);
        long timeElapsed = TimeStamp.timeDifference(startTime, new TimeStamp());
        return (float) timeElapsed / this.duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    /**
     * Used to start a job
     */
    public void startJob() {
        if (this.status == PAUSED) {
            this.status = ONGOING;
        } else {
            this.status = ONGOING;
        }
        this.startTime = new TimeStamp();
    }

    /**
     * Used in case of failed prints
     */
    public void restartJob() {
        this.startJob();
    }

    public void pauseJob() {
        this.status = PAUSED;
    }

    public void cancelJob() {
        this.status = Status.CANCELLED;
    }

    public void finishJob() {
        this.status = Status.FINISHED;
    }

    public void setJobNote(String jobNote) {
        this.jobNote.changeNote(jobNote);
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public JobName getJobName() {
        return name;
    }

    public MachineName getMachineName() {
        return machineName;
    }

    public TimeStamp getAddedTime() {
        return addedTime;
    }

    public TimeStamp getStartTime() {
        return startTime;
    }

    public JobOwner getOwner() {
        return owner;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public void addNote(String addition) {
        this.jobNote.addNote(addition);
    }


    public void setName(String newName) {
        name = new JobName(newName);
    }

    public void setMachine(MachineName newMachine) {
        machineName = newMachine;
    }

    public void setOwner(JobOwner newOwner) {
        owner = newOwner;
    }


    /**
     * Returns true if both jobs of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two jobs.
     * //TODO: Modify to match new class
     */
    public boolean isSameJob(Job otherJob) {
        if (otherJob == this) {
            return true;
        }

        return otherJob != null && otherJob.getJobName().equals(getJobName()) && (otherJob.getMachineName()
            .equals(getMachineName()) || otherJob.getAddedTime().equals(getAddedTime()) || otherJob.getOwner()
            .equals(getOwner()));
    }

    /**
     * Compares priority between two jobs
     */

    public int hasHigherDisplayPriority(Job comparedJob) {
        //TODO clean up code to make it neater for comparison
        if (this.equals(comparedJob) || statusRank(this.getStatus()) == statusRank(comparedJob.getStatus())) {
            return 0;
        }
        if (statusRank(this.getStatus()) > statusRank(comparedJob.getStatus())) {
            return 1;
        }
        return -1;
    }

    /**
     * ranks statuses
     */
    private int statusRank(Status myStatus) {
        switch (myStatus) {
        case ONGOING:
            return 3;
        case QUEUED:
            return 2;
        case CANCELLED:
        case FINISHED:
        case DELETING:
        case PAUSED:
        default:
            return 1;
        }

    }

    /**
     * Returns true if both jobs have the same identity and data fields.
     * This defines a stronger notion of equality between two jobs.
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Job)) {
            return false;
        }
        Job otherJob = (Job) other;

        if (other == this) {
            return true;
        }

        if (other == null) {
            return false;
        }

        return otherJob.getJobName().equals(getJobName()) && otherJob.getMachineName()
            .equals(getMachineName()) && otherJob.getOwner().equals(getOwner()) && otherJob.getAddedTime().showTime()
            .equals(getAddedTime().showTime()) && otherJob.getTags().equals(getTags()) && otherJob.getJobNote()
            .equals(getJobNote()) && (otherJob.getPriority() == getPriority()) && (otherJob
            .getDuration() == getDuration());
    }

    /**
     * Returns true if both jobs have the same identity.
     * This defines a weaker notion of equality between two jobs.
     */
    public boolean hasSameName(Object other) {
        Job otherJob = (Job) other;

        if (other == this) {
            return true;
        }

        if (other == null) {
            return false;
        }

        return otherJob.getJobName().equals(getJobName());
    }


    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, machineName, startTime, owner, tags);
    }

    @Override
    public String toString() {
        return "Job name " + this.getJobName().toString() + "\nJob machine " + this.getMachineName()
            .toString() + "\nJob Priority " + this.getPriority() + "\nJob status " + this.getStatus();
    }


}
