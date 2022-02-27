package szenario;

import configuration.ConsoleCorrespondation;
import console.Console;
import console.TextColor;
import leverCom.CommandControl;
import person.Attacker;
import person.Victim;
import pressurize.Pressurize;

public class Attack {
    private Attacker ed;// = new Attacker("ed",console);
    private Victim clueLess;
    private CommandControl cc;

    public Attack() {
        Console console = new Console(TextColor.WHITE, 1);
        Pressurize pressurize = new Pressurize(console, 60000L);
        ed = new Attacker("Ed", 1);
        clueLess = new Victim("Clue Less", console, 1);
        cc = new CommandControl(1, console, pressurize, ed.getWallet());
        console.addSubscriber(ed);
        console.addSubscriber(cc);
    }

    public void start() {
        clueLess.writeToConsole(ConsoleCorrespondation.CL_LAUNCH);
        clueLess.writeToConsole(ConsoleCorrespondation.CL_EXCHANGE);
        clueLess.writeToConsole(ConsoleCorrespondation.CL_SHOWBALANCE);
        clueLess.writeToConsole(ConsoleCorrespondation.CL_SHOWRECIPIENT);
        clueLess.writeToConsole(ConsoleCorrespondation.CL_PAYBTC);
        clueLess.writeToConsole(ConsoleCorrespondation.CL_CHECKPAYMENT);
    }
}
