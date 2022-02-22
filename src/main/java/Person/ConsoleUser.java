package person;

import console.Console;
import console.TextColor;

public class ConsoleUser extends Person implements IConsoleUser{
    protected Console console;

    public ConsoleUser(String name, Console console){
        super(name);
        this.console = console;
    }

    @Override
    public void writeToConsole(String text, TextColor color) {
        this.console.writeln(text,color);
    }

    public void writeToConsole(String text) {
        this.console.writeln(text);
    }
}
