package financial;

import blockchain.Network;
import com.google.common.eventbus.Subscribe;
import configuration.ConsoleCorrespondation;
import console.Console;
import console.Subscriber;
import console.TextColor;
import currency.Euro;
import events.AttackEvent;
import person.IConsoleUser;
import person.Victim;

public class Trader extends Subscriber implements IConsoleUser {
    Console console;
    Victim cl;
    Network network;
    public Trader(Integer id, Console console, Victim cl){
        super(id);
        this.console = console;
        this.cl = cl;
    }

    @Subscribe
    public void receive(AttackEvent event){
        switch (event.getTask()){
            case CL_EXCHANGE -> {
                this.cl.exchangeEuroToBTC(new Euro(0.02755)); //TODO parse amount from String (Enum value)
            }
            case CL_SHOWBALANCE -> {
                ConsoleCorrespondation.M_WALLETBALANCE.setValue(ConsoleCorrespondation.M_WALLETBALANCE.getValue()+this.cl.getWallet().getBalance());
                this.writeToConsole(ConsoleCorrespondation.M_WALLETBALANCE, TextColor.GREEN);
            }
            case CL_SHOWRECIPIENT -> {
                ConsoleCorrespondation.M_SHOWRECIPIENT.setValue(ConsoleCorrespondation.M_SHOWRECIPIENT.getValue()); //TODO Get Recipient publicKey
                this.writeToConsole(ConsoleCorrespondation.M_SHOWRECIPIENT, TextColor.GREEN);
            }
            case CL_PAYBTC -> {
                this.cl.getWallet().sendFunds(null, 0.02755); //TODO get Recipient
            }
            case CL_CHECKPAYMENT -> {
                this.network.check();
            }
        }
    }

    @Override
    public void writeToConsole(ConsoleCorrespondation text, TextColor color) {
        this.console.writeln(text, color);
    }

    public void writeToConsole(ConsoleCorrespondation text) {
        this.console.writeln(text);
    }

}
