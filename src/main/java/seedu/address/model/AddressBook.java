package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

import javafx.collections.ObservableList;
import seedu.address.model.admin.Admin;
import seedu.address.model.admin.Password;
import seedu.address.model.admin.UniqueAdminList;
import seedu.address.model.admin.Username;
import seedu.address.model.job.Job;
import seedu.address.model.job.JobName;
import seedu.address.model.job.UniqueJobList;
import seedu.address.model.machine.Machine;
import seedu.address.model.machine.UniqueMachineList;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final UniqueAdminList admins;
    private final UniqueMachineList machines;
    private final UniqueJobList jobs;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to
     * avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid
     * duplication
     *   among constructors.
     */

    {
        persons = new UniquePersonList();
        admins = new UniqueAdminList();
        machines = new UniqueMachineList();
        jobs = new UniqueJobList();
    }

    public AddressBook() {
    }

    /**
     * Creates an AddressBook using initial data in the {@code toBeCopied}
     * i.e persons,admins,machines,jobs data etc.
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //============================= list overwrite operations ==============================//

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }

    /**
     * Replaces the contents of the machine list with {@code machines}.
     * {@code machines} must not contain duplicate machines
     */
    public void setMachines(List<Machine> machines) {
        this.machines.setMachines(machines);
    }

    /**
     * Replaces the contents of the admin list with {@code admins}.
     * {@code admins} must not contain duplicate admins
     */
    public void setAdmins(List<Admin> admins) {
        this.admins.setAdmins(admins);
    }

    /**
     * Replaces the contents of the jobs list with {@code jobs}.
     * {@code jobs} must not contain duplicate jobs
     */
    public void setJobs(ObservableList<Job> jobs) {
        this.jobs.setJobs(jobs);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
        setMachines(newData.getMachineList());
        setAdmins(newData.getAdminList());
    }

    /**
     * Returns true if a job with the same identity as {@code job} exists in the address book.
     */
    public boolean hasJob(Job job) {
        requireNonNull(job);
        return jobs.contains(job);
    }

    //======================== person methods ================================//

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addPerson(Person p) {
        persons.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing
     * person in the address book.
     */
    public void updatePerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);

        persons.setPerson(target, editedPerson);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Person key) {
        persons.remove(key);
    }

    //======================== admin methods ================================//

    /**
     * Adds an admin to the address book.
     * The admin must not already exist in the address book.
     */
    public void addAdmin(Admin toAdd) {
        admins.add(encryptedAdmin(toAdd));
    }

    /**
     * Adds an admin from Files to Model
     * The admin must not already exist in the address book.
     */
    public void addAdminWithoutRehash(Admin toAdd) {
        //TODO: Not idiot proof
        admins.add(toAdd);
    }

    /**
     * Removes an admin from the address book.
     * The admin must already exist in the address book.
     */
    public void removeAdmin(Admin toRemove) {
        admins.remove(toRemove);
    }

    public boolean hasAdmin(Admin admin) {
        return admins.contains(admin);
    }

    public Admin findAdmin(Username username) {
        return admins.findAdmin(username);
    }

    public int numAdmins() {
        return admins.size();
    }

    /**
     * Returns Admin with password hashed
     *
     * @param rawAdmin
     * @return
     */
    private Admin encryptedAdmin(Admin rawAdmin) {
        Password encryptedPassword = new Password(
                BCrypt.hashpw(rawAdmin.getPassword().toString(), BCrypt.gensalt()));
        Admin protectedAdmin = new Admin(rawAdmin.getUsername(), encryptedPassword);
        return protectedAdmin;
    }

    //======================== machine methods ================================//

    /**
     * Returns true if a machine that matches the {@code machine}
     */
    public boolean hasMachine(Machine machine) {
        requireNonNull(machine);
        return machines.contains(machine);
    }

    /**
     * Adds a machine if {@code machine} does not exist in the list
     */
    public void addMachine(Machine machine) {
        requireNonNull(machine);
        machines.add(machine);
    }

    /**
     * Removes a machine if {@code toRemove} exists in the list
     */
    public void removeMachine(Machine toRemove) {
        requireNonNull(toRemove);
        machines.remove(toRemove);
    }

    /**
     * Replaces the given Machine {@code target} in the list with {@code editedMachine}.
     * {@code target} must exist in the address book.
     * The Machine identity of {@code editedMachine} must not be the same as another existing
     * Machine in the address book.
     */
    public void updateMachine(Machine target, Machine editedMachine) {
        requireNonNull(editedMachine);

        machines.setMachine(target, editedMachine);
    }
    //======================== job methods ================================//

    /**
     * Adds a job if {@code job} does not exist in the list
     */
    public void addJob(Job job) {
        requireNonNull(job);

        jobs.add(job);
    }

    /**
     * Removes a job if {@code job} does not exist in the list
     */
    public void removeJob(Job job) {
        requireNonNull(job);
        jobs.remove(job);
    }

    /**
     * Returns the job, if present, according to JobName
     */
    public Job findJob(JobName name) {
        return jobs.findJob(name);
    }

    /**
     * Replaces the given job {@code target} in the list with {@code editedJob}.
     * {@code target} must exist in the address book.
     * The job identity of {@code editedJob} must not be the same as another existing
     * Job in the address book.
     */
    public void updateJob(Job target, Job editedJob) {
        requireNonNull(editedJob);

        jobs.setJob(target, editedJob);
    }

    //======================== get lists methods ===========================//
    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Admin> getAdminList() {
        return admins.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Job> getJobList() {
        return jobs.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Machine> getMachineList() {
        return machines.asUnmodifiableObservableList();
    }


    //======================== others ================================//
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && persons.equals(((AddressBook) other).persons));
        //TODO: refine later
    }

    @Override
    public int hashCode() {
        //TODO: Refine later
        return persons.hashCode();
    }

    //======================== util methods ================================//
    @Override
    public String toString() {
        return persons.asUnmodifiableObservableList().size() + " persons";
        // TODO: refine later
    }

}