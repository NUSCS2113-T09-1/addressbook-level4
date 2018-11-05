package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.logging.Logger;

import org.mindrot.jbcrypt.BCrypt;

import javafx.collections.ObservableList;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.JobMachineTuple;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.FocusMachineRequestEvent;
import seedu.address.model.admin.Admin;
import seedu.address.model.admin.AdminSession;
import seedu.address.model.admin.Password;
import seedu.address.model.admin.UniqueAdminList;
import seedu.address.model.admin.Username;
import seedu.address.model.job.Job;
import seedu.address.model.job.JobName;
import seedu.address.model.job.Status;
import seedu.address.model.job.exceptions.JobNotFoundException;
import seedu.address.model.job.exceptions.JobOngoingException;
import seedu.address.model.machine.Machine;
import seedu.address.model.machine.MachineName;
import seedu.address.model.machine.MachineStatus;
import seedu.address.model.machine.UniqueMachineList;
import seedu.address.model.machine.exceptions.MachineDisabledException;
import seedu.address.model.machine.exceptions.MachineNotFoundException;
import seedu.address.model.person.Person;


/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private static final Logger logger = LogsCenter.getLogger(AddressBook.class);

    private final UniquePersonList persons;
    private final UniqueAdminList admins;
    private final UniqueMachineList machines;
    private final AdminSession adminSession;

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
        adminSession = new AdminSession();
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

    public void setAdminsSession(AdminSession adminsSession) {
        if (adminsSession.isAdminLoggedIn()) {
            this.adminSession.setLogin(adminsSession.getLoggedInAdmin());
        } else {
            this.adminSession.clearLogin();
        }
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
        setMachines(newData.getMachineList());
        setAdmins(newData.getAdminList());
        setAdminsSession(newData.getAdminSession());
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
        Password encryptedPassword = new Password(BCrypt.hashpw(rawAdmin.getPassword().toString(), BCrypt.gensalt()));
        Admin protectedAdmin = new Admin(rawAdmin.getUsername(), encryptedPassword);
        return protectedAdmin;
    }

    public void setLoggedInAdmin(Admin admin) {
        adminSession.setLogin(admin);
    }

    public void clearLogin() {
        adminSession.clearLogin();
    }

    public boolean isLoggedIn() {
        return adminSession.isAdminLoggedIn();
    }

    public Admin currentlyLoggedIn() {
        return adminSession.getLoggedInAdmin();
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
     * Returns true if a machine name matches any of that in the list
     */

    public boolean hasSameMachineName(Machine machine) {
        requireNonNull(machine);
        return machines.containsSameNameMachine(machine);
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
     * Flushes all jobs from a machine
     */

    public void flushMachine(Machine toFlushMachine) {
        requireNonNull(toFlushMachine);
        toFlushMachine.flushMachine();
        EventsCenter.getInstance().post(new FocusMachineRequestEvent(new JobMachineTuple(null, toFlushMachine)));

    }

    /**
     * Cleans all jobs from a machine that fufills the predicate below
     * 1) DELETING
     * 2) FINISHED
     * 3) CANCELLED
     */

    public void cleanMachine(Machine toCleanMachine) {
        requireNonNull(toCleanMachine);
        toCleanMachine.cleanMachine();
        EventsCenter.getInstance().post(new FocusMachineRequestEvent(new JobMachineTuple(null, toCleanMachine)));
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

    /**
     * Returns the machine that has least time left in Queue
     */
    public Machine getMostFreeMachine() {
        return machines.getMostFreeMachine();
    }

    /**
     * Returns the machine that has least time left in Queue that is not {@code otherThanMe}
     */
    public Machine getMostFreeMachine(Machine otherThanMe) {
        return machines.getMostFreeMachine(otherThanMe);
    }

    /**
     * Returns the machine that Machines the MachineName
     */
    public Machine findMachine(MachineName machineName) {
        requireNonNull(machineName);
        return machines.findMachine(machineName);
    }

    //======================== job methods ================================//

    /**
     * Returns true if a job with the same identity as {@code job} exists in the address book.
     */
    public boolean hasJob(Job job) {
        requireNonNull(job);
        for (Machine machine : getMachineList()) {
            if (machine.hasJob(job)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a job to the chosen machine list
     */

    public void addJobToMachineList(Job jobToAdd) {
        requireAllNonNull(jobToAdd);
        machines.addJobToMachineList(jobToAdd);
    }

    /**
     * Adds a job if {@code job} does not exist in the list
     */
    public void addJob(Job job) {
        requireNonNull(job);
        //jobs.add(job);
    }

    /**
     * Removes a job if {@code job} does not exist in the list
     */
    public void removeJob(JobName job) {
        requireNonNull(job);
        machines.removeJobFromMachineList(job);

    }

    /**
     * Returns the job, if present, according to JobName
     */
    public JobMachineTuple findJob(JobName name) {
        requireNonNull(name);
        for (Machine machine : getMachineList()) {
            Job jobToFind = machine.findJob(name);
            if (!(jobToFind == null)) {
                return new JobMachineTuple(jobToFind, machine);
            }
        }
        return null;
    }

    /**
     * Updates the job
     */
    public void updateJob(Job oldJob, Job updatedJob) {
        requireAllNonNull(oldJob, updatedJob);
        //jobs.updateJob(oldJob, updatedJob);
    }


    /**
     * Starts the job
     * Job Must exist in AddressBook else throws nullpointer exception
     *
     * @param name
     */
    public void startJob(JobName name) {
        requireNonNull(name);
        JobMachineTuple target = findJob(name);
        if (target.machine.getStatus() == MachineStatus.DISABLED) {
            throw new MachineDisabledException();
        }
        if (target.machine.getJobs().stream().anyMatch(job -> job.getStatus() == Status.ONGOING)) {
            throw new JobOngoingException();
        }
        target.job.startJob();
        target.machine.reSortJobList();
        EventsCenter.getInstance().post(new FocusMachineRequestEvent(target));
    }

    /**
     * Cancels the job
     *
     * @param name
     */
    public void cancelJob(JobName name) {
        requireNonNull(name);
        JobMachineTuple target = findJob(name);
        target.job.cancelJob();
        target.machine.reSortJobList();
        EventsCenter.getInstance().post(new FocusMachineRequestEvent(target));
    }

    /**
     * Restarts the job
     *
     * @param name
     */
    public void restartJob(JobName name) {
        requireNonNull(name);
        JobMachineTuple target = findJob(name);
        target.job.restartJob();
        target.machine.reSortJobList();
        EventsCenter.getInstance().post(new FocusMachineRequestEvent(target));
    }

    /**
     * Only admin can do this
     * Swaps the jobs with the given jobnames
     */
    public void swapJobs(JobName jobName1, JobName jobName2) {
        JobMachineTuple mj1 = findJob(jobName1);
        JobMachineTuple mj2 = findJob(jobName2);
        if (mj1 == null || mj2 == null) {
            throw new JobNotFoundException();
        }
        if (mj1.job.getStatus() == Status.ONGOING || mj1.job.getStatus() == Status.ONGOING) {
            throw new JobOngoingException();
        }
        mj1.machine.replaceJob(mj1.job, mj2.job);
        mj2.machine.replaceJob(mj2.job, mj1.job);
        mj1.machine.reSortJobList();
        mj2.machine.reSortJobList();
        EventsCenter.getInstance().post(new FocusMachineRequestEvent(mj2));
    }

    /**
     * Only admin can do this
     * Moves the job with the given jobnames to the machine with the give Machine Name
     */
    public void moveJob(JobName jobName, MachineName targetMachineName)
        throws JobNotFoundException, MachineNotFoundException {
        JobMachineTuple targetJobAndMachine = findJob(jobName);
        if (targetJobAndMachine == null) {
            throw new JobNotFoundException();
        }
        if (targetJobAndMachine.job.getStatus() == Status.ONGOING) {
            throw new JobOngoingException();
        }
        Machine targetMachine = findMachine(targetMachineName);
        if (targetMachine == null) {
            throw new MachineNotFoundException();
        }
        //removing job from old machine
        targetJobAndMachine.machine.removeJob(targetJobAndMachine.job);
        targetJobAndMachine.machine.reSortJobList();
        //updating job
        targetJobAndMachine.job.setMachine(targetMachine.getName());
        //adding to new Machine
        targetMachine.addJob(targetJobAndMachine.job);
        targetMachine.reSortJobList();
        EventsCenter.getInstance()
            .post(new FocusMachineRequestEvent(new JobMachineTuple(targetJobAndMachine.job, targetMachine)));
    }

    /**
     * Only admin can do this
     * shifts the order job with the given jobname within a machine
     */
    public void shiftJob(JobName jobName, int shiftBy) {
        JobMachineTuple targetJobAndMachine = findJob(jobName);
        if (targetJobAndMachine == null) {
            throw new JobNotFoundException();
        }
        if (targetJobAndMachine.job.getStatus() == Status.ONGOING) {
            throw new JobOngoingException();
        }
        //shifting
        targetJobAndMachine.machine.shift(targetJobAndMachine.job, shiftBy);
        targetJobAndMachine.machine.reSortJobList();
        EventsCenter.getInstance().post(new FocusMachineRequestEvent(targetJobAndMachine));
    }

    /**
     * Changes the status of the job to FINISHED
     *
     * @param target
     */
    public void finishJob(JobMachineTuple target) {
        requireNonNull(target);
        target.job.finishJob();
        target.machine.reSortJobList();
        EventsCenter.getInstance().post(new FocusMachineRequestEvent(target));
    }

    /**
     * Request deletion of print job
     */
    public void requestDeletion(JobName jobName) {
        JobMachineTuple toRequestDelete = findJob(jobName);
        toRequestDelete.job.setStatus(Status.DELETING);
        toRequestDelete.machine.reSortJobList();
        EventsCenter.getInstance().post(new FocusMachineRequestEvent(toRequestDelete));
    }

    public int getTotalNumberOfStoredJobs() {
        return getMachineList().stream().mapToInt(machine -> machine.getJobs().size()).sum();
    }

    public boolean isTopJob(JobName job) {
        return machines.isTopJob(job);
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
    public ObservableList<Machine> getMachineList() {
        return machines.asUnmodifiableObservableList();
    }

    @Override
    public AdminSession getAdminSession() {
        return adminSession;
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


