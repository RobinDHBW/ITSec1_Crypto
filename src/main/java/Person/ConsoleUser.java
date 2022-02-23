package person;

import configuration.ConsoleCorrespondation;
import console.Console;
import console.TextColor;

public class ConsoleUser extends Person implements IConsoleUser{
    protected Console console;

    public ConsoleUser(String name, Console console){
        super(name);
        this.console = console;
    }

    @Override
    public void writeToConsole(ConsoleCorrespondation text, TextColor color) {
        this.console.writeln(text.getValue(),color);
    }

    public void writeToConsole(ConsoleCorrespondation text) {
        this.console.writeln(text.getValue());
    }
}
