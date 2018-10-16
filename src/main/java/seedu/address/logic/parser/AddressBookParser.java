package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.admin.AddAdminCommand;
import seedu.address.logic.commands.admin.LoginCommand;
import seedu.address.logic.commands.admin.LogoutCommand;
import seedu.address.logic.commands.admin.RemoveAdminCommand;
import seedu.address.logic.commands.admin.UpdatePasswordCommand;
import seedu.address.logic.commands.job.AddJobCommand;
import seedu.address.logic.commands.job.DeleteJobCommand;
import seedu.address.logic.commands.job.FindJobCommand;
import seedu.address.logic.commands.job.ListJobsCommand;
import seedu.address.logic.commands.machine.AddMachineCommand;
import seedu.address.logic.commands.machine.EditMachineCommand;
import seedu.address.logic.commands.machine.FindMachineCommand;
import seedu.address.logic.commands.machine.ListMachinesCommand;
import seedu.address.logic.parser.admin.AddAdminCommandParser;
import seedu.address.logic.parser.admin.LoginCommandParser;
import seedu.address.logic.parser.admin.RemoveAdminCommandParser;
import seedu.address.logic.parser.admin.UpdatePasswordCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.job.AddJobCommandParser;
import seedu.address.logic.parser.job.DeleteJobCommandParser;
import seedu.address.logic.parser.job.FindJobCommandParser;
import seedu.address.logic.parser.machine.AddMachineCommandParser;
import seedu.address.logic.parser.machine.EditMachineCommandParser;
import seedu.address.logic.parser.machine.FindMachineCommandParser;

/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case SelectCommand.COMMAND_WORD:
            return new SelectCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case HistoryCommand.COMMAND_WORD:
            return new HistoryCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        case LoginCommand.COMMAND_WORD:
            return new LoginCommandParser().parse(arguments);

        case LogoutCommand.COMMAND_WORD:
            return new LogoutCommand();

        case AddMachineCommand.COMMAND_WORD:
            return new AddMachineCommandParser().parse(arguments);

        case AddAdminCommand.COMMAND_WORD:
            return new AddAdminCommandParser().parse(arguments);

        case RemoveAdminCommand.COMMAND_WORD:
            return new RemoveAdminCommandParser().parse(arguments);

        case UpdatePasswordCommand.COMMAND_WORD:
            return new UpdatePasswordCommandParser().parse(arguments);

        case FindMachineCommand.COMMAND_WORD:
            return new FindMachineCommandParser().parse(arguments);

        case ListMachinesCommand.COMMAND_WORD:
            return new ListMachinesCommand();

         case EditMachineCommand.COMMAND_WORD:
            return new EditMachineCommandParser().parse(arguments);

        case AddJobCommand.COMMAND_WORD:
            return new AddJobCommandParser().parse(arguments);

        case DeleteJobCommand.COMMAND_WORD:
            return new DeleteJobCommandParser().parse(arguments);

        case FindJobCommand.COMMAND_WORD:
            return new FindJobCommandParser().parse(arguments);

        case ListJobsCommand.COMMAND_WORD:
            return new ListJobsCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
