package seedu.address.storage.machine;

import java.util.HashSet;
import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.job.Job;
import seedu.address.model.job.JobName;
import seedu.address.model.job.JobNote;
import seedu.address.model.job.JobOwner;
import seedu.address.model.job.Name;
import seedu.address.model.job.Priority;
import seedu.address.model.machine.MachineName;


/**
 * JAXB-friendly version of the JobName.
 */
public class XmlAdaptedJobName {

    @XmlValue
    private String jobName;

    /**
     * Constructs an XmlAdaptedJobName
     * This is the no-arg constructor that is required by JAXB
     */
    public XmlAdaptedJobName() {
    }

    /**
     * Constructs a {@code XmlAdaptedJobName} with the given {@code jobName}.
     */
    public XmlAdaptedJobName(String jobName) {
        this.jobName = jobName;
    }

    public XmlAdaptedJobName(Name name) {
    }

    public XmlAdaptedJobName(Job job) {

    }

    /**
     * Converts this jaxb-friendly adapted jobname object into the model's Name object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted name
     */
    public Job toModelType() throws IllegalValueException {
        if (!JobName.isValidJobName(jobName)) {
            throw new IllegalValueException((JobName.MESSAGE_JOBNAME_CONSTRAINTS));
        }

        //TODO:  Remove hardcoding
        JobName name = new JobName(jobName);
        MachineName machine = new MachineName("JJ's printer");
        JobNote note = new JobNote("Empty note for now");
        JobOwner person = new JobOwner(new Name("JunJie"));

        return new Job(name, machine, person, Priority.HIGH, 2, note, new HashSet<>());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedJobName)) {
            return false;
        }

        return jobName.equals(((XmlAdaptedJobName) other).jobName);
    }
}
