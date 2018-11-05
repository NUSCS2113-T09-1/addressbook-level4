package seedu.address.model.machine;

import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.builders.MachineBuilder;
import seedu.address.testutil.testdata.ValidMachines;



public class MachineEqualityTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();




    @Test
    public void isSameNameMachine() {
        Machine toCheck = new MachineBuilder()
            .withMachineName("JJPrinter")
            .withMachineStatus(MachineStatus.ENABLED)
            .build();

        Machine testMachine = ValidMachines.JJPRINTER;
        assertTrue(testMachine.isSameNamedMachine(toCheck));
    }

    @Test
    public void isSameMachineParameters() {
        Machine toCheck = new MachineBuilder()
            .withMachineName("JJPrinter")
            .withMachineStatus(MachineStatus.ENABLED)
            .build();
        Machine testMachine = ValidMachines.JJPRINTER;
        assertTrue(testMachine.hasSameMachineParameters(toCheck));
    }

    @Test
    public void notEquals() {
        Machine toCheck = new MachineBuilder()
            .withMachineName("Printer")
            .withMachineStatus(MachineStatus.ENABLED)
            .build();
        Machine testMachine = ValidMachines.JJPRINTER;
        assertTrue(!toCheck.equals(testMachine));
    }
}
