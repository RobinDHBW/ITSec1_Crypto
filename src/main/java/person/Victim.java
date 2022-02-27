package person;

import console.Console;
import console.TextColor;
import financial.BankAccount;

public class Victim extends Person implements IConsoleUser {
    private  Console console;
    private BankAccount bankAccount = new BankAccount(5000.0);
    public Victim(String name, Console console, Integer id){
        super(name, id);
        this.console = console;
    }

    @Override
    public void writeToConsole(configuration.ConsoleCorrespondation text, TextColor color) {
        this.console.writeln(text,color);
    }

    public void writeToConsole(configuration.ConsoleCorrespondation text) {
        this.console.writeln(text);
    }
}