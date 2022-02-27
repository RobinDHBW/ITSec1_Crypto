package financial;

import blockchain.Network;
import com.google.common.eventbus.Subscribe;
import configuration.ConsoleCorrespondation;
import console.Console;
import console.Subscriber;
import console.TextColor;
import currency.BTC;
import currency.Euro;
import events.AttackEvent;
import person.IConsoleUser;
import person.Victim;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class Trader extends Subscriber implements IConsoleUser {
    Console console;
    Victim cl;
<<<<<<< HEAD
    Network network;
    public Trader(Integer id, Console console, Victim cl){
=======
    PublicKey recipient;
    BTC toPay = new BTC(0.02755);

    public Trader(Integer id, Console console, Victim cl) {
>>>>>>> 62a210deb59be85bff4c06fe6f841ab5ba9e9814
        super(id);
        this.console = console;
        this.cl = cl;
    }

    private PublicKey decodePublicKey(String key) {
        try {
            KeyFactory factory = KeyFactory.getInstance("ECDSA", "BC");
            return factory.generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(key)));
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }

    @Subscribe
    public void receive(AttackEvent event) {
        switch (event.getTask()) {
            case CL_EXCHANGE -> {
                this.cl.exchangeEuroToBTC(new Euro(cl.getBankAccount().calcConversion(toPay)));
            }
            case CL_SHOWBALANCE -> {
                ConsoleCorrespondation.M_WALLETBALANCE.setValue(ConsoleCorrespondation.M_WALLETBALANCE.getValue() + this.cl.getWallet().getBalance());
                this.writeToConsole(ConsoleCorrespondation.M_WALLETBALANCE, TextColor.GREEN);
            }
            case M_SHOWRECIPIENT -> {
                this.recipient = decodePublicKey(event.getTask().getValue().substring(11));
            }
            case CL_PAYBTC -> {
                this.cl.getWallet().sendFunds(this.recipient, toPay.getAmount());
            }
            case CL_CHECKPAYMENT -> {
<<<<<<< HEAD
                this.network.check();
=======

            }
            case M_RANSOMINCREASED -> {
                String toProcess = event.getTask().getValue();
                String doubleString = toProcess.substring(40, toProcess.length() - 4);
                toPay = new BTC(Double.parseDouble(doubleString));
>>>>>>> 62a210deb59be85bff4c06fe6f841ab5ba9e9814
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
