package financial;

import blockchain.Network;
import com.google.common.eventbus.Subscribe;
import configuration.Configuration;
import configuration.ConsoleCorrespondation;
import console.Console;
import console.Subscriber;
import console.TextColor;
import currency.BTC;
import currency.Euro;
import events.AttackEvent;
import events.VictimEvent;
import person.IConsoleUser;
import person.Victim;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class Trader extends Subscriber implements IConsoleUser {
    Console console;
    Victim cl;
    PublicKey recipient;
    BTC toPay = new BTC(0.02755);

    public Trader(Integer id, Console console, Victim cl, PublicKey recipient) {
        super(id);
        this.console = console;
        this.cl = cl;
        this.recipient = recipient;
    }

    private PublicKey decodePublicKey(String key) {
        try {
            KeyFactory factory = KeyFactory.getInstance("RSA");
            return factory.generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(key)));
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }

    @Subscribe
    public void receive(VictimEvent event) {
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
                Network.getInstance().addTransaction(this.cl.getWallet().sendFunds(this.recipient, toPay.getAmount()), this.recipient);
            }
            case CL_CHECKPAYMENT -> {
                Network.getInstance().check();
                if (Network.getInstance().isPaid()) {
                    this.writeToConsole(ConsoleCorrespondation.M_TRANSACTIONSUCCESS, TextColor.GREEN);
                } else {
                    this.writeToConsole(ConsoleCorrespondation.M_TRANSACTIONFAIL, TextColor.RED);
                }
            }
            case M_RANSOMINCREASED -> {
                String toProcess = event.getTask().getValue();
                String doubleString = toProcess.substring(40, toProcess.length() - 4);
                toPay = new BTC(Double.parseDouble(doubleString));

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
