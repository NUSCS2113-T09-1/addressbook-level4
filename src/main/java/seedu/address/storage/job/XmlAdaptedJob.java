package seedu.address.storage.job;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.job.Job;
import seedu.address.model.job.JobName;
import seedu.address.model.job.JobOwner;
import seedu.address.model.job.JobPriority;
import seedu.address.model.machine.Machine;
import seedu.address.model.person.Name;
import seedu.address.model.tag.Tag;
import seedu.address.storage.XmlAdaptedTag;

/**
 * JAXB-friendly version of the Job.
 */
public class XmlAdaptedJob {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Job's %s field is missing!";

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String machine;
    @XmlElement(required = true)
    private String time;
    @XmlElement(required = true)
    private String owner;
    @XmlElement(required = true)
    private JobPriority priority;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedJob.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedJob() {}

    /**
     * Constructs an {@code XmlAdaptedJob} with the given job details.
     */
    public XmlAdaptedJob(String name, String machine, String time, String owner,
                         JobPriority priority, List<XmlAdaptedTag> tagged) {
        this.name = name;
        this.machine = machine;
        this.time = time;
        this.owner = owner;
        this.priority = priority;
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
    }

    /**
     * Converts a given Job into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedJob
     */
    public XmlAdaptedJob(Job source) {
        name = source.getName().fullName;
        machine = source.getMachine().toString();
        time = source.getTime().toString();
        owner = source.getOwner().toString();
        priority = source.getPriority();
        tagged = source.getTags().stream()
                .map(XmlAdaptedTag::new)
                .collect(Collectors.toList());
    }

    /**
     * Converts this jaxb-friendly adapted job object into the model's Job object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted job
     */
    public Job toModelType() throws IllegalValueException {
        final List<Tag> jobTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            jobTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    JobName.class.getSimpleName()));
        }
        if (!JobName.isValidName(name)) {
            throw new IllegalValueException(JobName.MESSAGE_NAME_CONSTRAINTS);
        }
        final JobName modelJobName = new JobName(name);

        if (machine == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Machine.class.getSimpleName()));
        }
        if (!Machine.isValidMachine(machine)) {
            throw new IllegalValueException(Machine.MESSAGE_MACHINENAME_CONSTRAINTS);
        }
        final Machine modelMachine = new Machine(machine);

        if (owner == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    JobOwner.class.getSimpleName()));
        }
        if (!JobOwner.isValidJobOwner(owner)) {
            throw new IllegalValueException(JobOwner.MESSAGE_OWNERNAME_CONSTRAINTS);
        }
        final JobOwner modelJobOwner = new JobOwner(new Name(owner));

        if (priority == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    JobPriority.class.getSimpleName()));
        }
        if (!JobPriority.isValidPriority(priority)) {
            throw new IllegalValueException(JobPriority.MESSAGE_JOBPRIORITY_CONSTRAINTS);
        }
        final JobPriority modelPriority = priority;

        final Set<Tag> modelTags = new HashSet<>(jobTags);
        return new Job(modelJobName, modelMachine, modelJobOwner, modelPriority, modelTags);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof seedu.address.storage.job.XmlAdaptedJob)) {
            return false;
        }

        seedu.address.storage.job.XmlAdaptedJob otherJob = (seedu.address.storage.job.XmlAdaptedJob) other;
        return Objects.equals(name, otherJob.name)
                && Objects.equals(machine, otherJob.machine)
                && Objects.equals(owner, otherJob.owner)
                && Objects.equals(priority, otherJob.priority)
                && tagged.equals(otherJob.tagged);
    }
}
