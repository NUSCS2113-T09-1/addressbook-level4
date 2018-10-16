package seedu.address.storage;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javax.xml.bind.JAXBException;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.XmlUtil;
import seedu.address.storage.admin.XmlSerializableMakerManagerAdmins;
import seedu.address.storage.job.XmlSerializableMakerManagerJobs;
import seedu.address.storage.machine.XmlSerializableMakerManagerMachines;

/**
 * Stores addressbook data in an XML file
 */
public class XmlFileStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlFileStorage.class);

    /**
     * Saves the given addressbook data to the specified file.
     */
    public static void saveDataToFile(Path file, XmlSerializableAddressBook addressBook)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, addressBook);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage(), e);
        }
    }

    /**
     * Returns address book in the file or an empty address book
     */
    public static XmlSerializableAddressBook loadDataFromSaveFile(Path file) throws DataConversionException,
                                                                            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableAddressBook.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

    /**
     * Saves the given maker manager admins data to the specified file
     */
    public static void saveMakerManagerAdminDataToFile(Path file, XmlSerializableMakerManagerAdmins addressBook)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, addressBook);
        } catch (JAXBException e) {
            throw  new AssertionError("Unexpected exception" + e.getMessage(), e);
        }
    }
    /**
     * Returns makerManager admin_file in the file or an empty admin_file
     */

    public static XmlSerializableMakerManagerAdmins loadMakerManagerAdminDataFromSaveFile(Path file)
            throws DataConversionException,
            FileNotFoundException {
        try {
            logger.info("Getting serialized data from custom xml file");
            return XmlUtil.getDataFromFile(file, XmlSerializableMakerManagerAdmins.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

    //MakerManager address book xml file functions below

    /**
     * Saves the given maker manager address book data to the specified file
     */
    public static void saveMakerManagerDataToFile(Path file, XmlSerializableMakerManagerMachines addressBook)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, addressBook);
        } catch (JAXBException e) {
            throw  new AssertionError("Unexpected exception" + e.getMessage(), e);
        }
    }
    /**
     * Returns makerManager address book in the file or an empty address book
     */

    public static XmlSerializableMakerManagerMachines loadMakerManagerDataFromSaveFile(Path file)
            throws DataConversionException,
            FileNotFoundException {
        try {
            logger.info("Getting serialized data from custom xml file");
            return XmlUtil.getDataFromFile(file, XmlSerializableMakerManagerMachines.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

    /**
     * Saves the given maker manager jobs data to the specified file
     */
    public static void saveMakerManagerJobDataToFile(Path file, XmlSerializableMakerManagerJobs addressBook)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, addressBook);
        } catch (JAXBException e) {
            throw  new AssertionError("Unexpected exception" + e.getMessage(), e);
        }
    }
    /**
     * Returns makerManager job_file in the file or an empty job_file
     */

    public static XmlSerializableMakerManagerJobs loadMakerManagerJobDataFromSaveFile(Path file)
            throws DataConversionException,
            FileNotFoundException {
        try {
            logger.info("Getting serialized data from custom xml file");
            return XmlUtil.getDataFromFile(file, XmlSerializableMakerManagerJobs.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}
