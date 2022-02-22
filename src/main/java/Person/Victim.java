package person;

import console.Console;
import financial.BankAccount;

public class Victim extends ConsoleUser{
    private BankAccount bankAccount = new BankAccount(5000.0);
    public Victim(String name, Console console){
        super(name, console);
    }
}
