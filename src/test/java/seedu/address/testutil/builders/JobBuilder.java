package seedu.address.testutil.builders;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.job.Job;
import seedu.address.model.job.JobName;
import seedu.address.model.job.JobNote;
import seedu.address.model.job.JobOwner;
import seedu.address.model.job.Priority;
import seedu.address.model.job.Status;
import seedu.address.model.job.TimeStamp;
import seedu.address.model.machine.MachineName;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Job objects.
 */

public class JobBuilder {

    public static final String DEFAULT_JOBNAME = "IDCP";
    public static final Priority DEFAULT_PRIORITY = Priority.URGENT;
    public static final Status DEFAULT_STATUS = Status.ONGOING;
    private static final String DEFAULT_JOBNOTE = "This is makerManager default job";
    private static final long DEFAULT_DURAATION = 3;


    //Identity field
    private JobName name;
    private MachineName machine;
    private TimeStamp startTime;
    private JobOwner owner;
    private final String addedTime;

    //Data field
    private Set<Tag> tags;
    private JobNote jobNote;
    private Priority priority;
    private Status status;
    private long duration;


    //TODO: solving bug

    public JobBuilder() {
        name = new JobName(DEFAULT_JOBNAME);
        machine = new MachineBuilder().build().getName();
        owner = new JobOwnerBuilder().build();
        priority = DEFAULT_PRIORITY;
        status = DEFAULT_STATUS;
        jobNote = new JobNote(DEFAULT_JOBNOTE);
        duration = DEFAULT_DURAATION;
        tags = new HashSet<>();

        startTime = new TimeStamp();
        addedTime = new TimeStamp();
    }

    public JobBuilder(Job jobToCopy) {
        name = jobToCopy.getJobName();
        machine = jobToCopy.getMachineName();
        owner = jobToCopy.getOwner();
        addedTime = jobToCopy.getAddedTime();
        tags = new HashSet<>(jobToCopy.getTags());
        jobNote = jobToCopy.getJobNote();
        priority = jobToCopy.getPriority();
        status = jobToCopy.getStatus();
        duration = jobToCopy.getDuration();

    }

    /**
     * Sets the name to be the input name  parameter
     */
    public JobBuilder withName(String name) {
        this.name = new JobName(name);
        return this;
    }

    /**
     * Sets the machine to be the input machine parameter
     */
    public JobBuilder withMachine(MachineName machine) {
        this.machine = machine;
        return this;
    }

    /**
     * Sets the job owner to be the input person parameter
     */
    public JobBuilder withOwner(JobOwner owner) {
        this.owner = owner;
        return this;
    }

    /**
     * Sets the job note to be the input string parameter
     */
    public JobBuilder withJobNote(String jobNote) {
        this.jobNote = new JobNote(jobNote);
        return this;
    }

    /**
     * Sets the priority of the job to be the input priority parameter
     */
    public JobBuilder withPriority(Priority priority) {
        this.priority = priority;
        return this;
    }

    /**
     * Sets the status to be the input status parameter
     */
    public JobBuilder withStatus(Status status) {
        this.status = status;
        return this;
    }

    /**
     * Sets the duration to be the input duration parameter
     */
    public JobBuilder withDuration(long duration) {
        this.duration = duration;
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public JobBuilder withTags(String... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    public Job build() {
        return new Job(name, machine, owner, addedTime, startTime, priority, status, duration, jobNote, tags);
    }
}
