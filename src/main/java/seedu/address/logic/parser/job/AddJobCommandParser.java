package seedu.address.logic.parser.job;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOB_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOB_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOB_OWNER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOB_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MACHINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.job.AddJobCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.job.Job;
import seedu.address.model.job.JobName;
import seedu.address.model.job.JobNote;
import seedu.address.model.job.Priority;
import seedu.address.model.machine.MachineName;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddJobCommand object
 */
public class AddJobCommandParser implements Parser<AddJobCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddJobCommand
     * and returns an AddJobCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddJobCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer
            .tokenize(args, PREFIX_NAME, PREFIX_MACHINE, PREFIX_JOB_OWNER, PREFIX_JOB_PRIORITY, PREFIX_JOB_DURATION,
                PREFIX_JOB_NOTE, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_MACHINE, PREFIX_JOB_OWNER, PREFIX_JOB_PRIORITY,
            PREFIX_JOB_DURATION, PREFIX_JOB_NOTE) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddJobCommand.MESSAGE_USAGE));
        }

        JobName name = ParserUtil.parseJobName(argMultimap.getValue(PREFIX_NAME).get());
        MachineName machine = ParserUtil.parseMachineName(argMultimap.getValue(PREFIX_MACHINE).get());
        JobOwner jobOwner = ParserUtil.parseJobOwner(argMultimap.getValue(PREFIX_JOB_OWNER).get());
        Priority priority = ParserUtil.parseJobPriority(argMultimap.getValue(PREFIX_JOB_PRIORITY).get());
        Long duration = ParserUtil.parseDuration(argMultimap.getValue(PREFIX_JOB_DURATION).get());
        JobNote note = ParserUtil.parseJobNote(argMultimap.getValue(PREFIX_JOB_NOTE).get());
        Set<Tag> tags = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Job job = new Job(name, machine, jobOwner, priority, duration, note, tags);

        return new AddJobCommand(job);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
