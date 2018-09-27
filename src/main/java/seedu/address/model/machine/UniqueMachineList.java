package seedu.address.model.machine;

import static java.util.Objects.requireNonNull;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.machine.exceptions.DuplicateMachineException;
import seedu.address.model.machine.exceptions.MachineNotFoundException;


/**
 * A list of machines that ensures uniqueness in Machine names
 */
public class UniqueMachineList {
    private final ObservableList<Machine> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent machineName
     */
    public boolean contains(Machine toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Adds the Machine to the list
     * The Machine must not exist in the list
     * @param toAdd
     */
    public void add(Machine toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateMachineException();
        }

        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent machine from the list
     * The machine must exist in the list
     */
    public void remove(Machine toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new MachineNotFoundException();
        }
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}
     */
    public ObservableList<Machine> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }




}
