package person;

import console.Subscriber;
import financial.Wallet;

public abstract class Person extends Subscriber {
    protected String name;
    protected Wallet wallet = new Wallet();

    public Person(String name, Integer id) {
        super(id);
        this.name = name;
    }

    public Wallet getWallet() {
        return wallet;
    }
}
