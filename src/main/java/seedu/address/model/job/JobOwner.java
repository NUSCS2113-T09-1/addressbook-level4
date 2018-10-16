package seedu.address.model.job;

import static seedu.address.model.person.Name.NAME_VALIDATION_REGEX;

import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the lab. Inherit from Person.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class JobOwner extends Person {
    public static final String MESSAGE_OWNERNAME_CONSTRAINTS =
            "Owner names should only contain alphanumeric characters and spaces, "
                    + "and it should not be blank";
    public static final String MESSAGE_JOBOWNER_CONSTRAINTS =
            "Job owner's name should only contain alphanumeric characters and spaces, and it should not be blank";

    /**
     * Construct a {@code JobOwner}
     *
     * @param name a vaild name of the owner
     * @param phone a valid phone number of the owner
     * @param email a valid email address of the owner
     * @param address a valid address of the owner
     * @param tags some special tags to the owner
     *
     * Every field must be present and not null.
     */
    public JobOwner(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        super(name, phone, email, address, tags);
    }

    public JobOwner(Name name) {
        super(name);
    }


    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidJobOwner(String test) {
        return test.matches(NAME_VALIDATION_REGEX);
    }
}
