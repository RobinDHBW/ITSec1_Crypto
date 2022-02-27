package blockchain;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.google.gson.GsonBuilder;
import person.Miner;

public class Network {
    private List<Miner> miners = new ArrayList<>();
    private HashMap<String, TransactionOutput> utx0Map = new HashMap<>();
    private ArrayList<Block> network = new ArrayList<>();
    private Transaction genesisBlockTransaction;
    private Block previousBlock;
    private Wallet satoshiNakamoto;
    private int sequence;


    public Wallet SatoshiNakamoto;

    private void setup(List miners){
        this.SatoshiNakamoto = new Wallet();
        miners.add(new Miner("Bob",1));
        miners.add(new Miner("Eve",2));
        miners.add(new Miner("Sam",3));

        this.genesisBlockTransaction = new Transaction(satoshiNakamoto.getPublicKey(), satoshiNakamoto.getPublicKey(), 1.0, null);
        this.genesisBlockTransaction.generateSignature(satoshiNakamoto.getPrivateKey());
        this.genesisBlockTransaction.setId("0");
        this.genesisBlockTransaction.getOutputs().add(new TransactionOutput(this.genesisBlockTransaction.getRecipient(), this.genesisBlockTransaction.getValue(), this.genesisBlockTransaction.getId()));
        this.utx0Map.put(this.genesisBlockTransaction.getOutputs().get(0).getID(), this.genesisBlockTransaction.getOutputs().get(0));
        Block genesisBlock = new Block("0");
        genesisBlock.addTransaction(this.genesisBlockTransaction);
        addBlock(genesisBlock);
    }

    public void addTransaction(Transaction transaction) {
        Block block = new Block(this.previousBlock.getHash());
        block.addTransaction(transaction);
        this.addBlock(block);
    }

    private void addBlock(Block newBlock) {
        Miner m = miners.get(ThreadLocalRandom.current().nextInt(miners.size()) % miners.size());
        newBlock.mine(Configuration.instance.difficulty, m);
        this.network.add(newBlock);
        this.previousBlock = newBlock;

        try {
            File file = Path.of("blockchain.json").toAbsolutePath().toFile();
            file.createNewFile();
            FileWriter fileWriter = new FileWriter(file);
            new GsonBuilder().setPrettyPrinting().create().toJson(this, fileWriter);
            fileWriter.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private static Network instance;
    public static Network getInstance() {
        if (instance == null) instance = new Network();
        return instance;
    }

    public void incrementTransactionSequence() {
        this.sequence++;
    }

    public int getTransactionSequence() {
        return sequence;
    }

    public HashMap<String, TransactionOutput> getUtx0Map() {
        return this.utx0Map;
    }
}