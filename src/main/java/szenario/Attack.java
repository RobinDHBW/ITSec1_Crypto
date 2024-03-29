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

public class Attack {
    private static Attack instance;
    private Console console;
    private Attacker ed;
    private Victim clueLess;
    private CommandControl cc;
    private Trader trader;
    private Miner sam;
    private Miner bob;
    private Miner eve;

    private Attack() {
        this.console = new Console(TextColor.WHITE, 1);
        Pressurize pressurize = new Pressurize(console, 60000L);
        ed = new Attacker("Ed", 1);
        clueLess = new Victim("Clue Less", console, 1);
        Network.getInstance().addMiner(sam = new Miner("Sam", 0));
        Network.getInstance().addMiner(bob = new Miner("Bob", 1));
        Network.getInstance().addMiner(eve = new Miner("Eve", 2));

        cc = new CommandControl(1, console, pressurize, ed.getWallet());
        trader = new Trader(1, console, clueLess, ed.getWallet().getPublicKey());
        console.addSubscriber(ed);
        console.addSubscriber(cc);
        console.addSubscriber(trader);
        init();
    }

    public static Attack getInstance() {
        if (Objects.isNull(instance)) instance = new Attack();
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

        public void start() {
        this.console.writeln("***************************", TextColor.YELLOW);
        this.console.writeln("* Start szenario 'Attack' *", TextColor.YELLOW);
        this.console.writeln("***************************", TextColor.YELLOW);

        clueLess.writeToConsole(ConsoleCorrespondation.CL_LAUNCH);
        ConsoleCorrespondation.CL_EXCHANGE.setValue("exchange " + clueLess.getRansom().getAmount() + " BTC");
        clueLess.writeToConsole(ConsoleCorrespondation.CL_EXCHANGE);
        clueLess.writeToConsole(ConsoleCorrespondation.CL_SHOWBALANCE);
        clueLess.writeToConsole(ConsoleCorrespondation.CL_SHOWRECIPIENT);
        ConsoleCorrespondation.CL_PAYBTC.setValue("pay " + clueLess.getRansom().getAmount() + "BTC");
        clueLess.writeToConsole(ConsoleCorrespondation.CL_PAYBTC);
        clueLess.writeToConsole(ConsoleCorrespondation.CL_CHECKPAYMENT);

    }
}
