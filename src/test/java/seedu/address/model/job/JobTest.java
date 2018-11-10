package seedu.address.model.job;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.VALID_JOBDURATION_FIVE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_JOBNAME_PROONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_JOBNOTE_ANOTHERIDCP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_JOBPRIORITY_NORMAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_JOBSTATUS_CANCELLED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.model.job.Status.QUEUED;
import static seedu.address.testutil.testdata.TypicalJobs.IDCP;
import static seedu.address.testutil.testdata.TypicalJobs.NEWPROJECT;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.machine.MachineStatus;
import seedu.address.testutil.builders.JobBuilder;
import seedu.address.testutil.builders.JobOwnerBuilder;
import seedu.address.testutil.builders.MachineBuilder;

public class JobTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Job job = new JobBuilder().build();
        thrown.expect(UnsupportedOperationException.class);
        job.getTags().remove(0);
    }

    @Test
    public void isSameJob() {

        // same object -> returns true
        assertTrue(IDCP.isSameJob(IDCP));

        // null -> returns false
        assertFalse(IDCP.isSameJob(null));

        // different name -> returns false
        Job editedIdcp = new JobBuilder().withName(VALID_JOBNAME_PROONE).build();
        assertFalse(IDCP.isSameJob(editedIdcp));

        /* different owner -> returns false */
        editedIdcp = new JobBuilder().withOwner(new JobOwnerBuilder().build()).build();
        assertFalse(IDCP.isSameJob(editedIdcp));

        // different machine -> returns false
        editedIdcp = new JobBuilder().withMachine(new MachineBuilder().build().getName()).build();
        assertFalse(IDCP.isSameJob(editedIdcp));

        // different duration -> returns false
        editedIdcp = new JobBuilder().withDuration(Long.valueOf(VALID_JOBDURATION_FIVE)).build();
        assertFalse(IDCP.isSameJob(editedIdcp));

        // same name, same machine, same owner, different attributes -> returns true
        editedIdcp = new JobBuilder(IDCP).withPriority(VALID_JOBPRIORITY_NORMAL).withJobNote(VALID_JOBNOTE_ANOTHERIDCP)
            .withTags(VALID_TAG_HUSBAND).withStatus(VALID_JOBSTATUS_CANCELLED).build();
        assertTrue(IDCP.isSameJob(editedIdcp));

        // same name, same machine, same owner, same priority, different attributes -> returns true
        editedIdcp = new JobBuilder(IDCP).withJobNote(VALID_JOBNOTE_ANOTHERIDCP).withTags(VALID_TAG_HUSBAND)
            .withStatus(VALID_JOBSTATUS_CANCELLED).build();
        assertTrue(IDCP.isSameJob(editedIdcp));

        // same name, same machine, same owner, same priority, same status, different attributes -> returns true
        editedIdcp = new JobBuilder(IDCP).withJobNote(VALID_JOBNOTE_ANOTHERIDCP).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(IDCP.isSameJob(editedIdcp));

    }

    @Test
    public void equals() {
        // same values -> returns true
        Job idcpCopy = new JobBuilder(IDCP).build();
        assertTrue(IDCP.equals(idcpCopy));

        // same object -> returns true
        assertTrue(IDCP.equals(IDCP));

        // null -> returns false
        assertFalse(IDCP.equals(null));

        // different type -> returns false
        assertFalse(IDCP.equals(5));

        // different job -> returns false
        assertFalse(IDCP.equals(NEWPROJECT));

        // different name -> returns false
        Job editedIdcp = new JobBuilder(IDCP).withName(VALID_JOBNAME_PROONE).build();
        assertFalse(IDCP.equals(editedIdcp));

        // different machine -> returns false
        editedIdcp = new JobBuilder(IDCP).withMachine(new MachineBuilder().build().getName()).build();
        assertFalse(IDCP.equals(editedIdcp));

        // different added time -> returns false
        editedIdcp = new JobBuilder().withName("IDCP").withMachine(
            new MachineBuilder().withMachineName("JJPrinter").withMachineStatus(MachineStatus.ENABLED).build()
                .getName()).withJobNote("This is jj jobnote").withDuration(2).withPriority(Priority.HIGH)
            .withOwner(new JobOwnerBuilder().withName("Jun jie").build()).withStatus(QUEUED).build();
        assertFalse(IDCP.equals(editedIdcp));

        // different owner -> returns false
        editedIdcp = new JobBuilder(IDCP).withOwner(new JobOwnerBuilder().build()).build();
        assertFalse(IDCP.equals(editedIdcp));


    }
}
