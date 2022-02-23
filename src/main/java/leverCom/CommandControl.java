package leverCom;

import com.google.common.eventbus.Subscribe;
import configuration.ConsoleCorrespondation;
import console.Console;
import console.Subscriber;
import console.TextColor;
import events.AttackEvent;
import person.*;

public class CommandControl extends Subscriber implements IConsoleUser {
    private final RansomwareReflector reflector;
    private final Console console;
    public CommandControl(Integer id, Console console){
        super(id);
        this.reflector = new RansomwareReflector();
        this.console = console;
    }

    @Subscribe
    public void receive(AttackEvent event){
        switch (event.getTask()){
            case CL_LAUNCH -> {
                this.reflector.encrypt();
            }
            case CL_CHECKPAYMENT -> {

            }
        }
    }

    @Override
    public void writeToConsole(ConsoleCorrespondation text, TextColor color) {
            this.console.writeln(text, color);
    }

    public void writeToConsole(ConsoleCorrespondation text) {
        this.console.writeln(text);
    }
}
