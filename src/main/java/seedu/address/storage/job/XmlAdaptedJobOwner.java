package seedu.address.storage.job;

import java.util.Objects;
import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.job.JobOwner;
import seedu.address.model.job.Name;

/**
 * JAXB-friendly version of the JobOwner.
 */
public class XmlAdaptedJobOwner {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "JobOwner's %s field is missing!";

    @XmlElement(required = true)
    private String name;

    /**
     * Constructs an XmlAdaptedJobOwner.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedJobOwner() {}

    /**
     * Constructs an {@code XmlAdaptedJobOwner} with the given jobOwner  details.
     */
    public XmlAdaptedJobOwner(String name) {
        this.name = name;
    }

    /**
     * Converts a given JobOwner into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedJobOwner
     */
    public XmlAdaptedJobOwner(JobOwner source) {
        name = source.getName().fullName;
    }

    /**
     * Converts this jaxb-friendly adapted jobOwner  object into the model's JobOwner object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted jobOwner
     */
    public JobOwner toModelType() throws IllegalValueException {

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final Name modelName = new Name(name);


        return new JobOwner(modelName);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedJobOwner)) {
            return false;
        }

        XmlAdaptedJobOwner otherJobOwner = (XmlAdaptedJobOwner) other;
        return Objects.equals(name, otherJobOwner.name);
    }
}
