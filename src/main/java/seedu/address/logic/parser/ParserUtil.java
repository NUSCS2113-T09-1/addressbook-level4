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
import seedu.address.model.job.JobNote;
import seedu.address.model.job.JobOwner;
import seedu.address.model.job.Priority;
import seedu.address.model.job.TimeStamp;
import seedu.address.model.machine.Machine;
import seedu.address.model.machine.MachineName;
import seedu.address.model.machine.MachineStatus;
import seedu.address.model.job.Name;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_DURATION =
        "Duration must be a non-zero unsigned number with optional decimal point lesser than"
            + " or equal to 9999 rounded to the nearest 3 decimal places";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
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
            throw new ParseException(Machine.MESSAGE_NAME_CONSTRAINTS);
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
        if (!JobName.isValidJobName(jobName)) {
            throw new ParseException(Job.MESSAGE_NAME_CONSTRAINTS);
        }

        return new JobName(trimJobName);
    }

    // TODO: 11/3/2018 REMOVE PLS

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
            throw new ParseException(Machine.MESSAGE_NAME_CONSTRAINTS);
        }
        return new Machine(new MachineName(trimmedMachine), new ArrayList<Job>(), new HashSet<>(),
            MachineStatus.ENABLED);
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
    public static Priority parseJobPriority(String jobPriority) throws ParseException {
        requireNonNull(jobPriority);
        String trimJobPriority = jobPriority.trim();
        if (!Priority.isValidPriority(jobPriority)) {
            throw new ParseException(Job.MEEEAGE_PRIORITY_CONSTRAINTS);
        }

        return Priority.valueOf(trimJobPriority);
    }

    /**
     * Parses {@code String jobDuration} into {@code trimDuration}
     * Leading and trailing whitespace will be trimmed
     *
     * @throws ParseException if the given {@code trimDuration is invalid}
     */
    public static Long parseDuration(String duration) throws ParseException {
        requireNonNull(duration);
        String trimDuration = duration.trim();
        if (!StringUtil.isnonZeroFloat(trimDuration)) {
            throw new ParseException(MESSAGE_INVALID_DURATION);
        }

        return TimeStamp.hoursToMillis(Float.valueOf(trimDuration));
    }

    /**
     * Parses {@code String jobNote} into {@code trimJobNote}
     * Leading and trailing whitespace will be trimmed
     *
     * @throws ParseException if the given {@code jobNote is invalid}
     */
    public static JobNote parseJobNote(String jobNote) throws ParseException {
        requireNonNull(jobNote);
        String trimJobNote = jobNote.trim();
        if (!JobNote.isValidJobNote(jobNote)) {
            throw new ParseException(Job.MESSAGE_NOTE_CONSTRAINTS);
        }

        return new JobNote(trimJobNote);
    }

}
