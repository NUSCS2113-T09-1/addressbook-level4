package seedu.address.logic.commands.job;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.job.JobNameContainsKeywordsPredicate;

/**
 * Finds and lists all jobs in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindJobCommand extends Command {

    public static final String COMMAND_WORD = "findJob";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all jobs whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final JobNameContainsKeywordsPredicate predicate;

    public FindJobCommand(JobNameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredJobList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_JOBS_LISTED_OVERVIEW, model.getFilteredJobList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindJobCommand // instanceof handles nulls
                && predicate.equals(((FindJobCommand) other).predicate)); // state check
    }
}
