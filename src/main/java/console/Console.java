package console;

import com.google.common.eventbus.EventBus;

import java.util.concurrent.Flow;

public class Console implements IConsole {
    private static final String ansiReset = "\u001B[0m";
    private final TextColor defaultColor;
    private final EventBus eventBus;
    private final Integer id;

    public Console(TextColor color, Integer id) {
        this.defaultColor = color;
        this.id = id;
        this.eventBus = new EventBus("Console_" + id);
    }

    public void addSubscriber(Subscriber subscriber) {
        eventBus.register(subscriber);
    }

    @Override
    public String readln() {
        return null;
    }

    @Override
    public void writeln(String input, TextColor color) {
        System.out.println(color.getValue() + input + ansiReset);
    }

    public void writeln(String input) {
        writeln(input, defaultColor);
    }
}
