package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.job.Job;
import seedu.address.model.job.JobName;
import seedu.address.model.job.JobOwner;
import seedu.address.model.job.JobPriority;
import seedu.address.model.machine.Machine;
import seedu.address.model.machine.MachineName;
import seedu.address.model.machine.MachineStatus;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_PHONE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_ADDRESS_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_EMAIL_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_TAG_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses {@code String machineName} into {@code trimMachineName}
     * Leading and trailing whitespace will be trimmed
     *
     * @throws ParseException if the given {@code machineName is invalid}
     */
    public static MachineName parseMachineName(String machineName) throws ParseException {
        requireNonNull(machineName);
        String trimMachineName = machineName.trim();
        if (!MachineName.isValidName(machineName)) {
            throw new ParseException(Machine.MESSAGE_MACHINENAME_CONSTRAINTS);
        }

        return new MachineName(trimMachineName);
    }

    /**
     * Parses {@code String machineStatus} into {@code Boolean boolMachineStatus}
     * depending if {@code machineStatus} equals true or false
     */
    public static MachineStatus parseMachineStatus(String machineStatus) throws ParseException {
        requireNonNull(machineStatus);
        String trimMachineStatus = machineStatus.trim();
        switch (trimMachineStatus) {
            case "ENABLED":
                return MachineStatus.ENABLED;
            case "DISABLED":
                return MachineStatus.DISABLED;
            default:
                throw new ParseException(Machine.MESSAGE_WRONG_STATUS);
        }
    }

    /**
     * Parses {@code String jobName} into {@code trimJobName}
     * Leading and trailing whitespace will be trimmed
     *
     * @throws ParseException if the given {@code jobName is invalid}
     */
    public static JobName parseJobName(String jobName) throws ParseException {
        requireNonNull(jobName);
        String trimJobName = jobName.trim();
        if (!JobName.isValidName(jobName)) {
            throw new ParseException(Job.MESSAGE_NAME_CONSTRAINTS);
        }

        return new JobName(trimJobName);
    }

    /**
     * Parses a {@code String machine} into a {@code machine}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code machine} is invalid.
     */
    public static Machine parseMachine(String machine) throws ParseException {
        requireNonNull(machine);
        String trimmedMachine = machine.trim();
        if (!Machine.isValidMachine(trimmedMachine)) {
            throw new ParseException(Machine.MESSAGE_MACHINENAME_CONSTRAINTS);
        }
        return new Machine(new MachineName(trimmedMachine), new ArrayList<Job>(),
                new HashSet<>(), MachineStatus.ENABLED);
    }

    // TODO: 10-Oct-18 hardcoded: should find a better way or modify the JobOwner
    /**
     * Parses {@code String jobOwner} into {@code trimJobOwner}
     * Leading and trailing whitespace will be trimmed
     *
     * @throws ParseException if the given {@code jobOwner is invalid}
     */
    public static JobOwner parseJobOwner(String jobOwner) throws ParseException {
        requireNonNull(jobOwner);
        String trimJobOwner = jobOwner.trim();
        if (!JobOwner.isValidJobOwner(jobOwner)) {
            throw new ParseException(JobOwner.MESSAGE_OWNERNAME_CONSTRAINTS);
        }

        return new JobOwner(new Name(trimJobOwner));
    }

    /**
     * Parses {@code String jobPriority} into {@code trimJobPriority}
     * Leading and trailing whitespace will be trimmed
     *
     * @throws ParseException if the given {@code jobPriority is invalid}
     */
    public static JobPriority parseJobPriority(String jobPriority) {
        requireNonNull(jobPriority);
        String trimJobPriority = jobPriority.trim();

        return JobPriority.valueOf(trimJobPriority);
    }
}
