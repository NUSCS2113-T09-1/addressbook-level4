package seedu.address.model.job;

import seedu.address.model.machine.Machine;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Represents a Printing job in makerManager
 */
public class Job {
    private Name name;
    private Machine machine;
    private Note note;
    private TimeStamp time;
    private Person owner;

    public Job(Name name, Machine machine, Person owner, Note note) {
        this.name = name;
        this.machine = machine;
        this.note = note;
        this.time = new TimeStamp();
        this.owner = owner;
    }

    public Name getName() {
        return name;
    }

    public Machine getMachine() {
        return machine;
    }

    public TimeStamp getTime() {
        return time;
    }

    public Note getNote() {
        return note;
    }

    public Person getOwner() {
        return owner;
    }

    public void changeName(String name) {
        this.name = new Name(name);
    }

    public void changeMachine(Machine machine) {
        this.machine = machine;
    }

    /*public void changeTime(long time) {
        this.time.setTime(time);
    }*/
}
