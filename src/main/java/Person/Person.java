package person;

import financial.Wallet;

public abstract class Person {
    protected String name;
    protected Wallet wallet = new Wallet();

    public Person(String name) {
        this.name = name;
    }
}
