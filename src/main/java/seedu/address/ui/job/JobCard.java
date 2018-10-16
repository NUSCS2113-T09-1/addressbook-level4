package seedu.address.ui.job;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.job.Job;
import seedu.address.ui.UiPart;
import seedu.address.ui.machine.MachineCard;

/**
 * An UI component that displays information of a {@code Job}.
 */
public class JobCard extends UiPart<Region> {

    private static final String FXML = "JobListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Job job;

    @javafx.fxml.FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label jobName;
    @FXML
    private Label jobMachine;
    @FXML
    private Label time;
    @FXML
    private Label jobOwner;
    @FXML
    private Label jobPriority;
    @FXML
    private FlowPane tags;

    public JobCard(Job job, int displayIndex) {
        super(FXML);
        this.job = job;
        id.setText(displayIndex + ". ");
        jobName.setText(job.getName().fullName);
        jobMachine.setText(job.getMachine().getName().fullName);
        // TODO: 10-Oct-18 time to be displayed
        jobOwner.setText(job.getOwner().getName().fullName);
        jobPriority.setText(job.getPriority().toString());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MachineCard)) {
            return false;
        }

        // state check
        JobCard jobCard = (JobCard) other;
        return id.getText().equals(jobCard.id.getText())
                && job.equals(jobCard.job);
    }
}
