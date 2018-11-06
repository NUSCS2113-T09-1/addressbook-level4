package seedu.address.testutil.builders;

import seedu.address.model.job.JobOwner;
import seedu.address.model.job.Name;

/**
 * A utility class to help with building a job owner.
 */
public class JobOwnerBuilder {

    public static final String DEFAULT_JOBWONER_NAME = "MakerManagerJobOwner";

    private Name name;

    public JobOwnerBuilder() {
        name = new Name(DEFAULT_JOBWONER_NAME);
    }

    /**
     * changes the name to the input name
     */
    public JobOwnerBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    //TODO: implement the testing
    public JobOwner build() {
        return new JobOwner(name);
    }


}
