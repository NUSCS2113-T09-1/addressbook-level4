package seedu.address.storage.job;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.job.Job;

/**
 * An Immutable MakerManager AddressBook that is serializable to XML format
 */
@XmlRootElement(name = "MakerManagerJobs")
public class XmlSerializableMakerManagerJobs {

    public static final String MESSAGE_DUPLICATE_MACHINE = "Jobs list contains duplicate job(s)";

    @XmlElement
    private List<XmlAdaptedJob> jobs;

    /**
     * Creates an empty XmlSerializableMakerManagerJobs.
     * This empty constructor is required for marshalling
     */
    public XmlSerializableMakerManagerJobs() {
        jobs = new ArrayList<>();
    }
    /**
     * Conversion
     */
    public XmlSerializableMakerManagerJobs(ReadOnlyAddressBook src) {
        this();
        jobs.addAll(src.getJobList().stream().map(XmlAdaptedJob::new).collect(Collectors.toList()));
    }

    /**
     * Converts this addressbook in the model's {@code Addressbook} object.
     *
     * @throws seedu.address.commons.exceptions.IllegalValueException if there were any data
     * constraints violations or duplicates in the
     * {@code XmlAdaptedJob}.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        for (XmlAdaptedJob m : jobs) {
            Job job = m.toModelType();
            if (addressBook.hasJob(job)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_MACHINE);
            }
            addressBook.addJob(job);
        }
        return addressBook;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof seedu.address.storage.job.XmlSerializableMakerManagerJobs)) {
            return false;
        }

        return jobs.equals(((seedu.address.storage.job.XmlSerializableMakerManagerJobs) other).jobs);
    }
}

