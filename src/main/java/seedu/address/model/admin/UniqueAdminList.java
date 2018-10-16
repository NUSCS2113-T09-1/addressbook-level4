package seedu.address.model.admin;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.address.model.admin.exceptions.AdminNotFoundException;
import seedu.address.model.admin.exceptions.DuplicateAdminException;

/**
 * A list of admins that ensures uniqueness in Usernames
 */
public class UniqueAdminList {

    private final ObservableList<Admin> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent admin
     */
    public boolean contains(Admin toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Returns the admin, given the username
     * @param username
     * @return
     */
    public Admin findAdmin(Username username) {
        for (Admin admin : internalList) {
            if (admin.getUsername().equals(username)) {
                return admin;
            }
        }
        return null;
    }

    /**
     * Replaces the contents of this list with {@code admins}.
     * {@code admins} must not contain duplicate admins.
     */
    public void setAdmins(List<Admin> admins) {
        requireNonNull(admins);
        if (!adminsAreUnique(admins)) {
            throw new DuplicateAdminException();
        }

        internalList.setAll(admins);
    }

    /**
     * Adds the Admin to the list
     * The admin must not exist in the list
     * @param toAdd
     */
    public void add(Admin toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateAdminException();
        }

        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent admin from the list.
     * The admin must exist in the list.
     */
    public void remove(Admin toRemove) {
        requireNonNull(toRemove);
        if (!contains(toRemove)) {
            throw new AdminNotFoundException();
        }

        internalList.remove(toRemove);
    }

    /**
     * Returns size of adminList
     */
    public int size() {
        return internalList.size();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Admin> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }


    /**
     * Returns true if {@code admins} contains only unique admins
     */
    private boolean adminsAreUnique(List<Admin> admins) {
        for (int i = 0; i < admins.size() - 1; i++) {
            for (int j = i + 1; j < admins.size(); j++) {
                if (admins.get(i).equals(admins.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

}