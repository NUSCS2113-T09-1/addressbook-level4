package seedu.address.ui.machine;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.machine.Machine;
import seedu.address.ui.UiPart;


/**
 * A UI component that displays information of a {@code Machine}
 */
public class MachineCard extends UiPart<Region> {

    private static final String FXML = "MachineListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Machine machine;

    @FXML
    private HBox cardPane;
    @FXML
    private Label machineName;
    @FXML
    private Label id;
    @FXML
    private Label status;

    public MachineCard(Machine machine, int displayIndex) {
        super(FXML);
        this.machine = machine;
        id.setText(displayIndex + ". ");
        machineName.setText(machine.getName().fullName);
        status.setText(machine.getStringStatus());
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
        MachineCard machineCard = (MachineCard) other;
        return id.getText().equals(machineCard.id.getText())
                && machine.equals(machineCard.machine);
    }
}
