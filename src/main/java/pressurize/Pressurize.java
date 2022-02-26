package pressurize;

import com.google.common.eventbus.Subscribe;
import configuration.ConsoleCorrespondation;
import console.Console;
import console.Subscriber;
import console.TextColor;
import events.AttackEvent;
import person.IConsoleUser;

import java.util.Timer;
import java.util.TimerTask;

public class Pressurize implements IConsoleUser {
    private Console console;
    private Long delay;
    private Integer timerCount = 0;
    private Boolean isPaid = false;

    public Pressurize(Console console, Long delay) {
        this.delay = delay;
        this.console = console;
    }

    public void invokeTimer() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                timerCount++;
                Double toPay = 0.02755 + timerCount * 0.01;

                if(timerCount<4){
                    ConsoleCorrespondation.M_RANSOMINCREASED.setValue(ConsoleCorrespondation.M_RANSOMINCREASED.getValue() + toPay);
                    writeToConsole(ConsoleCorrespondation.M_RANSOMINCREASED);
                }else {
                    ConsoleCorrespondation.M_RANSOMFINAL.setValue(toPay+ ConsoleCorrespondation.M_RANSOMFINAL.getValue());
                    writeToConsole(ConsoleCorrespondation.M_RANSOMINCREASED);
                }
                if(timerCount<5) invokeTimer();

                if (timerCount == 5 && !isPaid){
                    //TODO
                }
            }
        };
    }

    @Override
    public void writeToConsole(ConsoleCorrespondation text, TextColor color) {
        this.console.writeln(text, color);
    }

    public void writeToConsole(ConsoleCorrespondation text) {
        this.console.writeln(text);
    }

    public void setPaid(Boolean paid) {
        isPaid = paid;
    }
}

