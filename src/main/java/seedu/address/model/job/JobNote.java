package seedu.address.model.job;

import static seedu.address.model.job.Name.NAME_VALIDATION_REGEX;

/**
 * Represents a Note Object for Job.
 */
public class JobNote {

    public static final String MESSAGE_NOTE_CONSTRAINTS =
            "Job notes should only contain alphanumeric characters and spaces, "
                    + "and it should not be blank";

    private String note;

    public JobNote(String note) {
        this.note = note;
    }

    public static boolean isValidJobNote(String test) {
        return test.matches(NAME_VALIDATION_REGEX);
    }

    public String toString() {
        return note;
    }

    public void changeNote(String newNote) {
        this.note = newNote;
    }

    /**
     * Adding to existing note
     * @param addition
     */
    public void addNote(String addition) {
        String newNote = this.note + "; \n" + addition;
        changeNote(newNote);
    }

}
