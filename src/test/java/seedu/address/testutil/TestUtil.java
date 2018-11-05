package seedu.address.testutil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.admin.Admin;
import seedu.address.model.machine.Machine;

/**
 * A utility class for test cases.
 */
public class TestUtil {

    /**
     * Folder used for temp files created during testing. Ignored by Git.
     */
    private static final Path SANDBOX_FOLDER = Paths.get("src", "test", "data", "sandbox");

    /**
     * Appends {@code fileName} to the sandbox folder path and returns the resulting path.
     * Creates the sandbox folder if it doesn't exist.
     */
    public static Path getFilePathInSandboxFolder(String fileName) {
        try {
            Files.createDirectories(SANDBOX_FOLDER);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return SANDBOX_FOLDER.resolve(fileName);
    }

    /**
     * Returns the middle index of the machine in the {@code model}'s person list.
     */
    public static Index getMidMachineIndex(Model model) {
        return Index.fromOneBased(model.getFilteredMachineList().size() / 2);
    }

    /**
     * Returns the middle index of the admin in the {@code model}'s person list.
     */
    public static Index getMidAdminIndex(Model model) {
        return Index.fromOneBased(model.getFilteredAdminList().size() / 2);
    }

    /**
     * Returns the last index of the machine in the {@code model}'s person list.
     */
    public static Index getLastMachineIndex(Model model) {
        return Index.fromOneBased(model.getFilteredMachineList().size());
    }

    /**
     * Returns the last index of the admin in the {@code model}'s person list.
     */
    public static Index getLastAdminIndex(Model model) {
        return Index.fromOneBased(model.getFilteredAdminList().size());
    }

    /**
     * Returns the machine in the {@code model}'s person list at {@code index}.
     */
    public static Machine getMachine(Model model, Index index) {
        return model.getFilteredMachineList().get(index.getZeroBased());
    }

    /**
     * Returns the machine in the {@code model}'s person list at {@code index}.
     */
    public static Admin getAdmin(Model model, Index index) {
        return model.getFilteredAdminList().get(index.getZeroBased());
    }

}
