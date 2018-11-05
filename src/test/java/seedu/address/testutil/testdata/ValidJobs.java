package seedu.address.testutil.testdata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.job.Job;
import seedu.address.model.job.Priority;
import seedu.address.model.job.Status;
import seedu.address.model.machine.MachineStatus;
import seedu.address.testutil.builders.JobBuilder;
import seedu.address.testutil.builders.MachineBuilder;

/**
 * A utility class containing a list of {@code Job} objects to be used in tests.
 */
public class ValidJobs {
    /**
     * Static method that returns a new Job created with the same parameters
     */
    public static final Job job1() {
        return new JobBuilder().withName("IDCP").withMachine(
            new MachineBuilder().withMachineName("JJPrinter").withMachineStatus(MachineStatus.ENABLED).build()
                .getName()).withJobNote("This is jj jobnote").withDuration(2).withPriority(Priority.HIGH)
            .withOwner(new PersonBuilder().withName("Jun jie").build()).withStatus(Status.QUEUED).build();
    }

    /**
     * Static method that returns a new Job created with the same parameters
     */
    public static final Job job2() {
        return new JobBuilder().withName("NEWPROJECT").withMachine(
            new MachineBuilder().withMachineName("TyPrinter").withMachineStatus(MachineStatus.ENABLED).build()
                .getName()).withJobNote("This is newproject jobnote").withDuration(2).withPriority(Priority.HIGH)
            .withOwner(new PersonBuilder().withName("TianYuan").build()).withStatus(Status.FINISHED).build();
    }

    /**
     * Static method that returns a new Job created with the same parameters
     */
    public static final Job job3() {
        return new JobBuilder().withName("bumblebee").withMachine(
            new MachineBuilder().withMachineName("BumberbeePrinter").withMachineStatus(MachineStatus.ENABLED).build()
                .getName()).withJobNote("This is BUMBERBEE jobnote").withDuration(1).withPriority(Priority.NORMAL)
            .withOwner(new PersonBuilder().withName("Bumble bee").build()).withStatus(Status.CANCELLED).build();
    }

    public static AddressBook getJobsData() {
        AddressBook makerManagerJobsData = new AddressBook();
        for (Job m : getValidJobs()) {
            makerManagerJobsData.addJob(m);
        }
        return makerManagerJobsData;
    }

    public static List<Job> getValidJobs() {
        return new ArrayList<>(Arrays.asList(job1(), job2(), job3()));
    }

}
