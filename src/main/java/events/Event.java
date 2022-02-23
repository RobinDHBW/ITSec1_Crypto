package events;

import configuration.ConsoleCorrespondation;

public abstract class Event {
    private final Integer id;
    private final ConsoleCorrespondation task;

    public Event(Integer id, ConsoleCorrespondation task) {
        this.id = id;
        this.task = task;
    }

    public ConsoleCorrespondation getTask() {
        return task;
    }
}
