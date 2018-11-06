package seedu.address.testutil.builders;

import java.util.ArrayList;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.model.job.Job;
import seedu.address.model.machine.Machine;
import seedu.address.model.machine.MachineName;
import seedu.address.model.machine.MachineStatus;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building Machine objects.
 */
public class MachineBuilder {

    public static final String DEFAULT_MACHINE_NAME = "MakerManagerPrinter";
    public static final MachineStatus DEFAULT_MACHINE_STATUS = MachineStatus.ENABLED;


    private MachineName machineName;
    private MachineStatus machineStatus;
    private List<Job> jobs;
    private Set<Tag> tags;


    public MachineBuilder() {
        machineName = new MachineName(DEFAULT_MACHINE_NAME);
        machineStatus = DEFAULT_MACHINE_STATUS;
        jobs = new ArrayList<>();
        tags = new HashSet<>();
    }

    /**
     *
     */
    public MachineBuilder withMachineName(String machineName) {
        this.machineName = new MachineName(machineName);
        return this;
    }
    /**
     *
     */
    public MachineBuilder withMachineStatus(MachineStatus machineStatus) {
        this.machineStatus = machineStatus;
        return this;
    }
    /**
     *
     */
    public MachineBuilder withJobs(List<Job> jobs) {
        this.jobs = jobs;
        return this;
    }
    /**
     *
     */
    public MachineBuilder withTags(Set<Tag> tags) {
        this.tags = tags;
        return this;
    }


    public Machine build() {
        return new Machine(machineName, jobs, tags, machineStatus);
    }

    public MachineName getMachineName() {
        return machineName;
    }

    public MachineStatus getMachineStatus() {
        return machineStatus;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public Set<Tag> getTags() {
        return tags;
    }

}
