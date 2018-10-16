package seedu.address.storage.job;

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
 * A class to access makerManagerAddressBook data stored as xml file on the hard disk.
 */
public class XmlMakerManagerJobStorage implements AddressBookStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlMakerManagerJobStorage.class);

    private Path filePath;

    public XmlMakerManagerJobStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getAddressBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, IOException {
        return readAddressBook(filePath);
    }

    /**
     * Similar to {@link #readAddressBook()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format
     */
    public Optional<ReadOnlyAddressBook> readAddressBook(Path filePath) throws FileNotFoundException,
            DataConversionException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("Job file " + filePath + " not found");
            return Optional.empty();
        }

        logger.info("Filepath in XmlMakerManagerJobStorage class : " + filePath.toString());

        XmlSerializableMakerManagerJobs xmlSerializableMakerManagerJobs =
                XmlFileStorage.loadMakerManagerJobDataFromSaveFile(filePath);
        try {
            return Optional.of(xmlSerializableMakerManagerJobs.toModelType());
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
        XmlFileStorage.saveMakerManagerJobDataToFile(filePath, new XmlSerializableMakerManagerJobs(addressBook));
    }
}
