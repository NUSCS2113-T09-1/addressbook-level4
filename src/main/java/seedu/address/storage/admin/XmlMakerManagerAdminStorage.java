package seedu.address.storage.admin;

import static java.util.Objects.requireNonNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.storage.AddressBookStorage;
import seedu.address.storage.XmlFileStorage;

/**
 * A class to access MakerManagerAddressBook data stored as an xml file on the hard disk.
 */
public class XmlMakerManagerAdminStorage implements AddressBookStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlMakerManagerAdminStorage.class);

    private Path filePath;

    public XmlMakerManagerAdminStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getAddressBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, IOException {
        logger.info("Reading admin in XmlMakerManagerStorage class");
        return readAddressBook(filePath);
    }

    /**
     * Similar to {@link #readAddressBook()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyAddressBook> readAddressBook(Path filePath) throws DataConversionException,
            FileNotFoundException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("Admin file "  + filePath + " not found");
            return Optional.empty();
        }
        logger.info("Filepath in XmlAddressBookStorage class : " + filePath.toString());

        XmlSerializableMakerManagerAdmins xmlAddressBook =
                XmlFileStorage.loadMakerManagerAdminDataFromSaveFile(filePath);

        try {
            return Optional.of(xmlAddressBook.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, filePath);
    }

    /**
     * Similar to {@link #saveAddressBook(ReadOnlyAddressBook)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
        requireNonNull(addressBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        XmlFileStorage.saveMakerManagerAdminDataToFile(filePath, new XmlSerializableMakerManagerAdmins(addressBook));
    }

}
