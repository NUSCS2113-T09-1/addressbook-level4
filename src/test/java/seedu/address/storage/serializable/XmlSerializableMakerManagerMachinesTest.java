package seedu.address.storage.serializable;


import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.XmlUtil;
import seedu.address.storage.machine.XmlSerializableMakerManagerMachines;

public class XmlSerializableMakerManagerMachinesTest {

    private static final Path TEST_DATA_FOLDER =
            Paths.get("src", "test", "data", "XmlSerializableMakerManagerMachinesTest");
    private static final Path VALID_MACHINES_FILE = TEST_DATA_FOLDER.resolve("validMachinesFile.xml");
    private static final Path INVALID_MACHINES_FILE = TEST_DATA_FOLDER.resolve("invalidMachinesFile.xml");
    private static final Path DUPLICATE_MACHINES_FILE = TEST_DATA_FOLDER.resolve("duplicateMachinesFile.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    /*
    @Test
    public void toModelType_validMachinesFile_success() throws Exception {
        XmlSerializableMakerManagerMachines dataFromFile =
                XmlUtil.getDataFromFile(VALID_MACHINES_FILE, XmlSerializableMakerManagerMachines.class);
        AddressBook makerManagerMachinesFile = dataFromFile.toModelType();
        AddressBook validMakerManagerMachines = ValidMachines.getMachinesData();
        assertEquals(makerManagerMachinesFile, validMakerManagerMachines);
    }
    */

    @Test
    public void toModelType_invalidMachinesFile_throwsNullPointerException () throws Exception {
        XmlSerializableMakerManagerMachines dataFromFile =
                XmlUtil.getDataFromFile(INVALID_MACHINES_FILE, XmlSerializableMakerManagerMachines.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicateJobs_throwsillegalValueException() throws Exception {
        XmlSerializableMakerManagerMachines dataFromFile =
                XmlUtil.getDataFromFile(DUPLICATE_MACHINES_FILE, XmlSerializableMakerManagerMachines.class);
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(XmlSerializableMakerManagerMachines.MESSAGE_DUPLICATE_MACHINE);
        dataFromFile.toModelType();
    }

}
