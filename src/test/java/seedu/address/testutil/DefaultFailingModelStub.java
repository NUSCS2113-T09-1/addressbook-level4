package seedu.address.testutil;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.JobMachineTuple;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.admin.Admin;
import seedu.address.model.admin.Username;
import seedu.address.model.job.Job;
import seedu.address.model.job.JobName;
import seedu.address.model.machine.Machine;
import seedu.address.model.machine.MachineName;

/**
 * A default model stub that have all of the methods failing.
 */
public class DefaultFailingModelStub implements Model {

    @Override
    public void resetData(ReadOnlyAddressBook newData) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasJob(Job job) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addJob(Job job) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteJob(JobName job)  {
        throw new AssertionError("This method should not be called.");
    }

    public void startJob(JobName name) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void cancelJob(JobName name) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void restartJob(JobName name) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void swapJobs(JobName jobname1, JobName jobName2) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void finishJob(JobMachineTuple job)  {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void requestDeletion(JobName jobName)  {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public int getTotalNumberOfJobsDisplayed() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean isTopJob(JobName job) {
        throw new AssertionError("This method should not be called.");
    }

    public void moveJobToMachine(Job job, Machine targetMachine)  {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void autoMoveJobsDuringFlush(Machine currentMachine)  {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Machine findMachine(MachineName machinename)  {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateJob(Job oldJob, Job updatedJob)  {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public JobMachineTuple findJob(JobName name) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addMachine(Machine machine) {
        throw new AssertionError("This method should not be called");
    }

    @Override
    public void updateMachine(Machine target, Machine editedMachine) {
        throw new AssertionError("This method should not be called");
    }

    @Override
    public Machine getMostFreeMachine() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Machine getMostFreeMachine(Machine otherThanMe)  {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void removeMachine(Machine machine) {
        throw new AssertionError("This method should not be called");
    }

    @Override
    public boolean hasMachine(Machine machine) {
        throw new AssertionError("This method should not be called");
    }

    @Override
    public boolean hasSameMachineName(Machine machine)  {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void flushMachine(Machine toFlushMachine)  {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void cleanMachine(Machine toCleanMachine)  {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addAdmin(Admin admin) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void removeAdmin(Admin admin) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateAdmin(Admin admin, Admin updatedAdmin) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setLogin(Admin admin) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void clearLogin() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean isLoggedIn() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Admin findAdmin(Username username) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public int numAdmins() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Admin> getFilteredAdminList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredAdminList(Predicate<Admin> predicate) {
        throw new AssertionError("This method should not be called");
    }

    @Override
    public ObservableList<Machine> getFilteredMachineList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredMachineList(Predicate<Machine> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredJobListInAllMachines(Predicate<Job> predicate)  {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean canUndoAddressBook() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean canRedoAddressBook() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void undoAddressBook() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void redoAddressBook() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void commitAddressBook() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void adminLoginCommitAddressBook() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void adminLogoutCommitAddressBook() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean isRedoLogin() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean isUndoLogout() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Admin currentlyLoggedIn()  {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean isUndoLogin() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void shiftJob(JobName jobName, int shiftBy) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void moveJob(JobName jobName, MachineName targetMachineName) {
        throw new AssertionError("This method should not be called.");
    }
}
