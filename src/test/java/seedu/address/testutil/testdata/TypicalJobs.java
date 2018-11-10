package seedu.address.testutil.testdata;

import static seedu.address.model.job.Status.CANCELLED;
import static seedu.address.model.job.Status.FINISHED;
import static seedu.address.model.job.Status.QUEUED;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.job.Job;
import seedu.address.model.job.Priority;
import seedu.address.model.machine.MachineStatus;
import seedu.address.testutil.builders.JobBuilder;
import seedu.address.testutil.builders.JobOwnerBuilder;
import seedu.address.testutil.builders.MachineBuilder;

/**
 * A utility class containing a list of {@code Job} objects to be used in tests.
 */
public class TypicalJobs {

    public static final Job IDCP = new JobBuilder().withName("IDCP").withMachine(
        new MachineBuilder().withMachineName("JJPrinter").withMachineStatus(MachineStatus.ENABLED).build().getName())
        .withJobNote("This is jj jobnote").withDuration(2).withPriority(Priority.URGENT)
        .withOwner(new JobOwnerBuilder().withName("Jun jie").build()).withStatus(QUEUED).build();

    public static final Job NEWPROJECT = new JobBuilder().withName("newProject").withMachine(
        new MachineBuilder().withMachineName("TyPrinter").withMachineStatus(MachineStatus.ENABLED).build().getName())
        .withJobNote("This is newproject jobnote").withDuration(2).withPriority(Priority.HIGH)
        .withOwner(new JobOwnerBuilder().withName("TianYuan").build()).withStatus(FINISHED).build();

    public static final Job BUMBERBEE = new JobBuilder().withName("bumblebee").withMachine(
        new MachineBuilder().withMachineName("BumberbeePrinter").withMachineStatus(MachineStatus.ENABLED).build()
            .getName()).withJobNote("This is bumberbee jobnote").withDuration(1).withPriority(Priority.NORMAL)
        .withOwner(new JobOwnerBuilder().withName("Bumble bee").build()).withStatus(CANCELLED).build();

    public static AddressBook getJobsData() {
        AddressBook makerManagerJobsData = new AddressBook();
        for (Job m : getTypicalJobs()) {
            makerManagerJobsData.addJob(m);
        }
        return makerManagerJobsData;
    }
    /**
     * Returns an {@code AddressBook} with all the typical jobs.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Job job : getTypicalJobs()) {
            ab.addJob(job);
        }
        return ab;
    }

    public static List<Job> getTypicalJobs() {
        return new ArrayList<>(Arrays.asList(IDCP, NEWPROJECT, BUMBERBEE));
    }
}
