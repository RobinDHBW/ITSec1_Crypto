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

import java.security.PublicKey;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Attack {
    private Attacker ed;// = new Attacker("ed",console);
    private Victim clueLess;
    private CommandControl cc;
    private Trader trader;
    private Miner sam;
    private Miner bob;
    private Miner eve;

    private void init(){
        Wallet coinbase = Network.getInstance().getSatoshiNakamoto();
        Configuration.instance.genesisTransaction = new Transaction(coinbase.getPublicKey(), coinbase.getPublicKey(), 1.0, null);
        Configuration.instance.genesisTransaction.generateSignature(coinbase.getPrivateKey());
        Configuration.instance.genesisTransaction.setId("0");
        Configuration.instance.genesisTransaction.getOutputs().add(new TransactionOutput(Configuration.instance.genesisTransaction.getRecipient(),Configuration.instance.genesisTransaction.getValue(), Configuration.instance.genesisTransaction.getId()));
        Configuration.instance.utx0Map.put(Configuration.instance.genesisTransaction.getOutputs().get(0).getID(), Configuration.instance.genesisTransaction.getOutputs().get(0));
        Block genesisBlock = new Block("0", coinbase.getPublicKey());
        genesisBlock.addTransaction(Configuration.instance.genesisTransaction);
        Network.getInstance().addBlock(genesisBlock);

    }

    private void payAtRandomMoment(){
        Integer delay = new Random().nextInt(360000);
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                clueLess.writeToConsole(ConsoleCorrespondation.CL_PAYBTC);
                clueLess.writeToConsole(ConsoleCorrespondation.CL_CHECKPAYMENT);
            }
        };
        timer.schedule(task, delay);
    }

    public Attack() {
        Console console = new Console(TextColor.WHITE, 1);
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

    public void start() {
        clueLess.writeToConsole(ConsoleCorrespondation.CL_LAUNCH);
        clueLess.writeToConsole(ConsoleCorrespondation.CL_EXCHANGE);
        clueLess.writeToConsole(ConsoleCorrespondation.CL_SHOWBALANCE);
        clueLess.writeToConsole(ConsoleCorrespondation.CL_SHOWRECIPIENT);
        payAtRandomMoment();
    }
}
