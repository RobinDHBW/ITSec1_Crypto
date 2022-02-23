package szenario;

import configuration.ConsoleCorrespondation;
import console.Console;
import console.TextColor;
import leverCom.CommandControl;
import person.Attacker;
import person.Victim;

public class Attack {
    private Attacker ed;// = new Attacker("ed",console);
    private Victim clueLess;
    private CommandControl cc;

    public Attack() {
        Console console = new Console(TextColor.WHITE, 1);
        ed = new Attacker("Ed", console, 1);
        clueLess = new Victim("Clue Less", console, 1);
        cc = new CommandControl(1);
        console.addSubscriber(ed);
        console.addSubscriber(cc);
    }

    public void start() {
        clueLess.writeToConsole(ConsoleCorrespondation.CL_LAUNCH);
    }
}
