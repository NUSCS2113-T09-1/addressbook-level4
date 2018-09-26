package seedu.address.logic.parser.machine;

import seedu.address.logic.commands.machine.AddMachineCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.machine.Machine;
import seedu.address.model.machine.MachineName;
import seedu.address.model.person.Name;
import seedu.address.model.tag.Tag;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MACHINE_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

/**
 * Parses input arguments and creates a new AddMachineCommand object
 */
public class AddMachineParser implements Parser<AddMachineCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddMachineCommand
     * and returns an AddMachineCommand object for execution
     * @throws ParseException if the user input does not conform the expected format
     */

    public AddMachineCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,PREFIX_NAME,PREFIX_MACHINE_STATUS);

        if(!arePrefixesPresent(argMultimap,PREFIX_NAME,PREFIX_MACHINE_STATUS)
                || !argMultimap.getPreamble().isEmpty()){
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,AddMachineCommand.MESSAGE_USAGE));
        }

        MachineName name = new MachineName("JJ's_Printer");
        List<Name> jobs = new ArrayList<>();
        Set<Tag> tags = new HashSet<>();
        Machine machine = new Machine(name,jobs,tags,true);

        return new AddMachineCommand(machine);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
