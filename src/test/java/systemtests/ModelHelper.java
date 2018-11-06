package systemtests;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.model.Model;
import seedu.address.model.admin.Admin;
import seedu.address.model.machine.Machine;

/**
 * Contains helper methods to set up {@code Model} for testing.
 */
public class ModelHelper {
    private static final Predicate<Machine> PREDICATE_MATCHING_NO_MACHINES = unused -> false;
    private static final Predicate<Admin> PREDICATE_MATCHING_NO_ADMINS = unused -> false;

    /**
     * Updates {@code model}'s filtered list to display only {@code toDisplay}.
     */
    public static void setFilteredMachineList(Model model, List<Machine> toDisplay) {
        Optional<Predicate<Machine>> predicate =
                toDisplay.stream().map(ModelHelper::getPredicateMatching).reduce(Predicate::or);
        model.updateFilteredMachineList(predicate.orElse(PREDICATE_MATCHING_NO_MACHINES));
    }

    /**
     * @see ModelHelper#setFilteredMachineList(Model, List)
     */
    public static void setFilteredMachineList(Model model, Machine... toDisplay) {
        setFilteredMachineList(model, Arrays.asList(toDisplay));
    }
    /**
     * Updates {@code model}'s filtered list to display only {@code toDisplay}.
     */
    public static void setFilteredAdminList(Model model, List<Admin> toDisplay) {
        Optional<Predicate<Admin>> predicate =
            toDisplay.stream().map(ModelHelper::getPredicateMatching).reduce(Predicate::or);
        model.updateFilteredAdminList(predicate.orElse(PREDICATE_MATCHING_NO_ADMINS));
    }

    /**
     * @see ModelHelper#setFilteredAdminList(Model, List)
     */
    public static void setFilteredAdminList(Model model, Admin... toDisplay) {
        setFilteredAdminList(model, Arrays.asList(toDisplay));
    }

    /**
     * Returns a predicate that evaluates to true if this {@code Machine} equals to {@code other}.
     */
    private static Predicate<Machine> getPredicateMatching(Machine other) {
        return machine -> machine.equals(other);
    }

    /**
     * Returns a predicate that evaluates to true if this {@code Machine} equals to {@code other}.
     */
    private static Predicate<Admin> getPredicateMatching(Admin other) {
        return admin -> admin.equals(other);
    }

}
