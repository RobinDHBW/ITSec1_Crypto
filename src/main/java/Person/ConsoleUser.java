package Person;

public class ConsoleUser extends Person implements IConsoleUser{

    public ConsoleUser(String name){
        super(name);
    }

    @Override
    public void writeToConsole(String text) {

    }
}
