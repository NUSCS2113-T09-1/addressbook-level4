package seedu.address.ui.machine;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.events.ui.MachinePanelSelectiononChangedEvent;
import seedu.address.model.machine.Machine;
import seedu.address.ui.UiPart;




/**
 * Panel containing the list of machines
 */
public class MachineListPanel extends UiPart<Region> {
    private static final String FXML = "MachineListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(MachineListPanel.class);

    @FXML
    private ListView<Machine> machineListView;

    public MachineListPanel(ObservableList<Machine> machineList) {
        super(FXML);
        setConnections(machineList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Machine> machineList) {
        machineListView.setItems(machineList);
        machineListView.setCellFactory(listView -> new MachineListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        machineListView.getSelectionModel().selectedItemProperty()
                .addListener(((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in machine list panel changed to : " + newValue + "'");
                        raise(new MachinePanelSelectiononChangedEvent(newValue));
                    }
                }));
    }

    /**
     * Scrolls to the [@code MachineCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            machineListView.scrollTo(index);
            machineListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Machine} using a {@code MachineCard}
     */
    class MachineListViewCell extends ListCell<Machine> {
        @Override
        protected void updateItem(Machine machine, boolean empty) {
            super.updateItem(machine, empty);

            if (empty || machine == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new MachineCard(machine, getIndex() + 1).getRoot());
            }
        }
    }
}
