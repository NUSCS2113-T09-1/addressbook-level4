package seedu.address.storage.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.admin.Admin;

/**
 * An Immutable MakerManagerAdminw that is serializable to XML format
 */
@XmlRootElement(name = "makermanager_admins")
public class XmlSerializableMakerManagerAdmins {

    public static final String MESSAGE_DUPLICATE_ADMIN = "Admins list contains duplicate admin(s).";

    @XmlElement
    private List<XmlAdaptedAdmin> admins;

    /**
     * Creates an empty XmlSerializableMakerManagerAdmins.
     * This empty constructor is required for marshalling.
     * TODO: What is marshalling?
     */
    public XmlSerializableMakerManagerAdmins() {
        admins = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableMakerManagerAdmins(ReadOnlyAddressBook src) {
        this();
        admins.addAll(src.getAdminList().stream().map(XmlAdaptedAdmin::new).collect(Collectors.toList()));
    }

    /**
     * Converts this MakerManagerAdmins into the model's {@code Addressbook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedAdmin}.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();

        for (XmlAdaptedAdmin a : admins) {
            Admin admin = a.toModelType();
            if (addressBook.hasAdmin(admin)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_ADMIN);
            }
            addressBook.addAdmin(admin);
        }

        return addressBook;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableMakerManagerAdmins)) {
            return false;
        }
        return admins.equals(((XmlSerializableMakerManagerAdmins) other).admins);
    }
}
