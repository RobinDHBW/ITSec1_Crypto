package szenario;

import configuration.Configuration;
import configuration.ConsoleCorrespondation;
import console.Console;
import console.TextColor;
import person.Attacker;
import person.Victim;

public class Attack {
    //private Console console = new Console(TextColor.WHITE);
    private Attacker ed;// = new Attacker("ed",console);
    private Victim clueLess;

    public Attack(){
        Console console = new Console(TextColor.WHITE);
        ed = new Attacker("Ed", console);
        clueLess = new Victim("Clue Less", console);
    }

    public void start(){
        clueLess.writeToConsole(ConsoleCorrespondation.CL_LAUNCH);
    }
}
