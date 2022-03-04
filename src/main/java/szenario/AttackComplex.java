package szenario;

import blockchain.Block;
import blockchain.Network;
import blockchain.Transaction;
import blockchain.TransactionOutput;
import configuration.Configuration;
import configuration.ConsoleCorrespondation;
import console.Console;
import console.TextColor;
import financial.Trader;
import financial.Wallet;
import leverCom.CommandControl;
import person.Attacker;
import person.Miner;
import person.Victim;
import pressurize.Pressurize;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

public class AttackComplex {
    private static AttackComplex instance;
    private Console console;
    private Attacker ed;
    private Victim clueLess;
    private CommandControl cc;
    private Trader trader;
    private Miner sam;
    private Miner bob;
    private Miner eve;
    private Timer timer;

    private AttackComplex() {
        this.console = new Console(TextColor.WHITE, 1);
        Pressurize pressurize = new Pressurize(console, 60000L);
        ed = new Attacker("Ed", 1);
        clueLess = new Victim("Clue Less", console, 1);
        sam = new Miner("Sam", 0);
        bob = new Miner("Bob", 1);
        eve = new Miner("Eve", 2);

        cc = new CommandControl(1, console, pressurize, ed.getWallet());
        trader = new Trader(1, console, clueLess, ed.getWallet().getPublicKey());
        console.addSubscriber(ed);
        console.addSubscriber(cc);
        console.addSubscriber(trader);
        init();
    }

    public static AttackComplex getInstance() {
        if (Objects.isNull(instance)) instance = new AttackComplex();
        return instance;
    }

    private void init() {
        Wallet coinbase = Network.getInstance().getSatoshiNakamoto();
        Configuration.instance.genesisTransaction = new Transaction(coinbase.getPublicKey(), coinbase.getPublicKey(), 1.0, null);
        Configuration.instance.genesisTransaction.generateSignature(coinbase.getPrivateKey());
        Configuration.instance.genesisTransaction.setId("0");
        Configuration.instance.genesisTransaction.getOutputs().add(new TransactionOutput(Configuration.instance.genesisTransaction.getRecipient(), Configuration.instance.genesisTransaction.getValue(), Configuration.instance.genesisTransaction.getId()));
        Configuration.instance.utx0Map.put(Configuration.instance.genesisTransaction.getOutputs().get(0).getID(), Configuration.instance.genesisTransaction.getOutputs().get(0));
        Block genesisBlock = new Block("0", coinbase.getPublicKey());
        genesisBlock.addTransaction(Configuration.instance.genesisTransaction);
        Network.getInstance().addBlock(genesisBlock);

    }

    private void payAtRandomMoment() {
        Integer delay = ThreadLocalRandom.current().nextInt(Configuration.instance.minTimer, Configuration.instance.maxTimer);
        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                ConsoleCorrespondation.CL_EXCHANGE.setValue("exchange " + clueLess.getRansom().getAmount() + " BTC");
                clueLess.writeToConsole(ConsoleCorrespondation.CL_EXCHANGE);
                clueLess.writeToConsole(ConsoleCorrespondation.CL_SHOWBALANCE);
                clueLess.writeToConsole(ConsoleCorrespondation.CL_SHOWRECIPIENT);
                ConsoleCorrespondation.CL_PAYBTC.setValue("pay " + clueLess.getRansom().getAmount() + "BTC");
                clueLess.writeToConsole(ConsoleCorrespondation.CL_PAYBTC);
                clueLess.writeToConsole(ConsoleCorrespondation.CL_CHECKPAYMENT);
            }
        };
        timer.schedule(task, delay);
    }

    public void start() {
        Double minTimerInMin = Configuration.instance.minTimer.doubleValue() / 60000;
        Double maxTimerInMin = Configuration.instance.maxTimer.doubleValue() / 60000;
        this.console.writeln("************************************", TextColor.YELLOW);
        this.console.writeln("* Start szenario 'AttackComplex'   *", TextColor.YELLOW);
        this.console.writeln("* This will run a random time      *", TextColor.YELLOW);
        this.console.writeln("* between " + minTimerInMin + " min and " + maxTimerInMin + " min      *", TextColor.YELLOW);
        this.console.writeln("* Feel free to lean back and relax *", TextColor.YELLOW);
        this.console.writeln("************************************", TextColor.YELLOW);

        clueLess.writeToConsole(ConsoleCorrespondation.CL_LAUNCH);
        payAtRandomMoment();
    }

    public void hePaid() {
        if (Objects.nonNull(timer)) {
            timer.cancel();
            timer.purge();
        }
    }
}
