package seedu.address.logic.commands.job;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_JOBS;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

/**
 * Lists all jobs in the address book to the user.
 */
public class ListJobsCommand extends Command {

    public static final String COMMAND_WORD = "listJobs";

    public static final String MESSAGE_SUCCESS = "Listed all jobs";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredJobList(PREDICATE_SHOW_ALL_JOBS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
