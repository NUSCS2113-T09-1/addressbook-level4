package seedu.address.storage;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.XmlUtil;
import seedu.address.storage.admin.XmlSerializableMakerManagerAdmins;
import seedu.address.storage.machine.XmlSerializableMakerManagerMachines;

/**
 * Stores addressbook data in an XML file
 */
public class XmlFileStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlFileStorage.class);

    //==================================Save data==============================================//


    /**
     * Saves the given admins data to the specified file.
     */
    public static void saveDataToFile(Path file, XmlSerializableMakerManagerAdmins addressBook)
        throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, addressBook);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage(), e);
        }
    }

    /**
     * Saves the given machines data to the specified file.
     */
    public static void saveDataToFile(Path file, XmlSerializableMakerManagerMachines addressBook)
        throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, addressBook);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage(), e);
        }
    }

    //==================================Load data================================================//


    /**
     * Returns makerManager admin_file in the file or an empty admin_file
     */

    public static XmlSerializableMakerManagerAdmins loadMakerManagerAdminDataFromSaveFile(Path file)
        throws DataConversionException, FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableMakerManagerAdmins.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

    /**
     * Returns makerManager address book in the file or an empty address book
     */
    public static XmlSerializableMakerManagerMachines loadMakerManagerMachineDataFromSaveFile(Path file)
        throws DataConversionException, FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableMakerManagerMachines.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }
}
