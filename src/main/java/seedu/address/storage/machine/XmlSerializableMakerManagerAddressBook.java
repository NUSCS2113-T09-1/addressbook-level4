package seedu.address.storage.machine;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.machine.Machine;


/**
 * An Immutable MakerManager AddressBook that is serializable to XML format
 */
@XmlRootElement(name = "MakerManagerAddressBook")
public class XmlSerializableMakerManagerAddressBook {

    public static final String MESSAGE_DUPLICATE_MACHINE = "Machines list contains duplicate machine(s)";

    @XmlElement
    private List<XmlAdaptedMachine> machines;

    /**
     * Creates an empty XmlSerializableMakerManagerAddressBook.
     * This empty constructor is required for marshalling
     */
    public XmlSerializableMakerManagerAddressBook() {
        machines = new ArrayList<>();
    }
    /**
     * Conversion
     */
    public XmlSerializableMakerManagerAddressBook(ReadOnlyAddressBook src) {
        this();
        machines.addAll(src.getMachineList().stream().map(XmlAdaptedMachine::new).collect(Collectors.toList()));
    }

    /**
     * Converts this addressbook in the model's {@code Addressbook} object.
     *
     * @throws seedu.address.commons.exceptions.IllegalValueException if there were any data
     * constraints violations or duplicates in the
     * {@code XmlAdaptedMachine}.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        for (XmlAdaptedMachine m : machines) {
            Machine machine = m.toModelType();
            if (addressBook.hasMachine(machine)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_MACHINE);
            }
            addressBook.addMachine(machine);
        }
        return addressBook;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableMakerManagerAddressBook)) {
            return false;
        }

        return machines.equals(((XmlSerializableMakerManagerAddressBook) other).machines);
    }
}
