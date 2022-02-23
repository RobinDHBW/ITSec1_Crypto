package person;

import configuration.ConsoleCorrespondation;
import console.Console;
import console.TextColor;

public abstract class ConsoleUser extends Person implements IConsoleUser{
    protected Console console;

    public ConsoleUser(String name, Console console, Integer id){
        super(name, id);
        this.console = console;
    }

    @Override
    public void writeToConsole(ConsoleCorrespondation text, TextColor color) {
        this.console.writeln(text,color);
    }

    public void writeToConsole(ConsoleCorrespondation text) {
        this.console.writeln(text);
    }
}
