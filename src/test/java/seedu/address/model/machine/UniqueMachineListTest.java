package seedu.address.model.machine;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.job.Job;
import seedu.address.model.machine.exceptions.DuplicateMachineException;
import seedu.address.model.machine.exceptions.MachineNotFoundException;
import seedu.address.testutil.builders.MachineBuilder;
import seedu.address.testutil.testdata.ValidJobs;
import seedu.address.testutil.testdata.ValidMachines;

public class UniqueMachineListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueMachineList myList = new UniqueMachineList();

    @Test
    public void contains_sameNameMachine() {
        myList.add(ValidMachines.JJPRINTER);
        assertTrue(myList.contains(ValidMachines.JJPRINTER));
    }

    @Test
    public void contains_nullMachine_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        myList.contains(null);
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(myList.contains(ValidMachines.TYPRINTER));
    }

    @Test
    public void contains_machineInList_returnsTrue() {
        myList.add(ValidMachines.JJPRINTER);
        assertTrue(myList.contains(ValidMachines.JJPRINTER));
    }

    @Test
    public void add_nullMachine_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        myList.add(null);
    }

    @Test
    public void add_duplicateMachine_throwsDuplicateMachineException() {
        myList.add(ValidMachines.JJPRINTER);
        thrown.expect(DuplicateMachineException.class);
        myList.add(ValidMachines.JJPRINTER);
    }

    @Test
    public void setMachine_nullTargetMachine_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        myList.setMachine(null, ValidMachines.JJPRINTER);
    }

    @Test
    public void setMachine_nullEditedMachine_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        myList.setMachine(ValidMachines.JJPRINTER, null);
    }

    @Test
    public void remove_nullMachine_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        myList.remove(null);
    }

    @Test
    public void remove_machineDoesNotExist_throwsMachineNotFoundException() {
        thrown.expect(MachineNotFoundException.class);
        myList.remove(ValidMachines.JJPRINTER);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperation() {
        thrown.expect(UnsupportedOperationException.class);
        myList.asUnmodifiableObservableList().remove(0);
    }

    @Test
    public void findMachineTest() {
        myList.add(ValidMachines.JJPRINTER);
        assertNotNull(myList.findMachine(new MachineName("JJPrinter")));
    }

    @Test
    public void containsSameNameMachine() {
        myList.add(ValidMachines.JJPRINTER);
        Machine testMachine = new MachineBuilder().withMachineName("JJPrinter").withMachineStatus(MachineStatus.ENABLED)
            .build();
        assertTrue(myList.containsSameNameMachine(testMachine));
    }

    @Test
    public void replaceTargetMachineWithEditedMachine() {
        myList.add(ValidMachines.JJPRINTER);

        myList.setMachine(ValidMachines.JJPRINTER, ValidMachines.TYPRINTER);
        assertNotNull(myList.findMachine(new MachineName("TYPrinter")));
    }


    @Test
    public void findMachineReturnsNotNull() {
        myList.add(ValidMachines.JJPRINTER);
        assertNotNull(myList.findMachine(new MachineName("JJPrinter")));
    }

    @Test
    public void findMachineReturnsNull() {
        assertNull(myList.findMachine(new MachineName("JJPrinter")));
    }

    // TODO: 11/5/2018 THIS TEST FAILS WITH GRADLE BUT PASSES WITH INTELLIJ. RETURNS DUPLICATE JOB FOR SOME REASON
    // WITH GRADLE. UNKNOWN AS TO WHY :( Uncomment //myList.addJobToMachineList(ValidJobs.IDCP) and
    // compile with gradle to see
    @Test
    public void addJobToMachineList() {
        myList.add(new MachineBuilder().withMachineName("JJPrinter").withMachineStatus(MachineStatus.ENABLED).build());
        Job jobToAdd = ValidJobs.job1();
        myList.addJobToMachineList(ValidJobs.job1());
        Machine addedJobMachine = myList.findMachine(new MachineName("JJPrinter"));
        assertTrue(addedJobMachine.hasJob(jobToAdd));
    }

    @Test
    public void addJobToMachineList_returnsMachineNotFoundException() {
        thrown.expect(MachineNotFoundException.class);
        myList.add(ValidMachines.TYPRINTER);
        myList.addJobToMachineList(ValidJobs.job1());
    }

}
