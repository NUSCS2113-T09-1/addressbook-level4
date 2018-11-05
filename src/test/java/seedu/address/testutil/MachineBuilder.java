package seedu.address.testutil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.model.job.Job;
import seedu.address.model.machine.Machine;
import seedu.address.model.machine.MachineName;
import seedu.address.model.machine.MachineStatus;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class MachineBuilder {

    public static final String DEFAULT_NAME = "Machine1";
    public static final MachineStatus DEFAULT_STATUS = MachineStatus.ENABLED;

    private MachineName name;
    private MachineStatus status;
    private Set<Tag> tags;
    private List<Job> jobs;

    public MachineBuilder() {
        name = new MachineName(DEFAULT_NAME);
        status = DEFAULT_STATUS;
        jobs = new ArrayList<>();
        tags = new HashSet<>();

    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public MachineBuilder(Machine machineToCopy) {
        name = machineToCopy.getName();
        status = machineToCopy.getStatus();

        tags = new HashSet<>(machineToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public MachineBuilder withName(String name) {
        this.name = new MachineName(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public MachineBuilder withTags(String... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public MachineBuilder withStatus(MachineStatus status) {
        this.status = status;
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public MachineBuilder withJobs(List<Job> jobs) {
        this.jobs = jobs;
        return this;
    }

    public Machine build() {
        return new Machine(name, jobs, tags, status);
    }

}
