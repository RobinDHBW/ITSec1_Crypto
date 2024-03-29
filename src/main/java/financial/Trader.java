package financial;

import blockchain.Network;
import com.google.common.eventbus.Subscribe;
import configuration.ConsoleCorrespondation;
import console.Console;
import console.Subscriber;
import console.TextColor;
import currency.BTC;
import currency.Euro;
import events.VictimEvent;
import person.IConsoleUser;
import person.Victim;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class Trader extends Subscriber implements IConsoleUser {
    private Console console;
    private Victim cl;
    private PublicKey recipient;

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
                this.cl.exchangeEuroToBTC(new Euro(cl.getBankAccount().calcConversion(cl.getRansom())));
            }
            case CL_SHOWBALANCE -> {
                ConsoleCorrespondation.M_WALLETBALANCE.setValue(">>Wallet balance:" + this.cl.getWallet().getBalance() + " BTC");
                this.writeToConsole(ConsoleCorrespondation.M_WALLETBALANCE, TextColor.GREEN);
            }
            case M_SHOWRECIPIENT -> {
                this.recipient = decodePublicKey(event.getTask().getValue().substring(11));
            }
            case CL_PAYBTC -> {
                Network.getInstance().addTransaction(this.cl.getWallet().sendFunds(this.recipient, cl.getRansom().getAmount()), this.recipient);
            }
            case CL_CHECKPAYMENT -> {
                Network.getInstance().check();
                if (Network.getInstance().checkBlockchainValidity()) {
                    this.writeToConsole(ConsoleCorrespondation.M_TRANSACTIONSUCCESS, TextColor.GREEN);
                } else {
                    this.writeToConsole(ConsoleCorrespondation.M_TRANSACTIONFAIL, TextColor.RED);
                }
            }
            case M_RANSOMINCREASED -> {
                String toProcess = event.getTask().getValue();
                String doubleString = toProcess.substring(42, toProcess.length() - 4);
                cl.setRansom(new BTC(Double.parseDouble(doubleString)));
            }
            case M_RANSOMFINAL -> {
                cl.setRansom(new BTC(0.06755));
            }
            case M_ENCRYPTED -> {
                cl.setRansom(new BTC(0.02755));
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
