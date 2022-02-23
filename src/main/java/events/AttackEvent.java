package events;

import configuration.ConsoleCorrespondation;

public class AttackEvent extends  Event{

    public AttackEvent(Integer id, ConsoleCorrespondation task) {
        super(id, task);
    }
}
