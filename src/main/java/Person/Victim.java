package person;

import console.Console;
import console.Subscriber;
import financial.BankAccount;

public class Victim extends ConsoleUser {
    private BankAccount bankAccount = new BankAccount(5000.0);
    public Victim(String name, Console console, Integer id){
        super(name, console, id);
    }
}
