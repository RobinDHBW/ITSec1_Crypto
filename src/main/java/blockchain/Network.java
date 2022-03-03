package blockchain;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.google.gson.GsonBuilder;
import configuration.Configuration;
import currency.BTC;
import financial.Wallet;
import person.Miner;

public class Network {
    private static Network instance;
    private List<Miner> miners = new ArrayList<>();
    //private HashMap<String, TransactionOutput> utx0Map = new HashMap<>();
    private ArrayList<Block> network = new ArrayList<>();
    private Transaction genesisBlockTransaction;
    private Block previousBlock;
    private Wallet satoshiNakamoto = new Wallet();
    private int sequence;
    boolean encrypted = false;
    boolean paid = false;
    Wallet wallet;
    Double amount = 0.02755;

    public void addTransaction(Transaction transaction, PublicKey publicKey) {
        Block block = new Block(this.previousBlock.getHash(), publicKey);
        block.addTransaction(transaction);
        this.addBlock(block);
    }

    public void addBlock(Block newBlock) {
        //Miner m = miners.get(ThreadLocalRandom.current().nextInt(miners.size()) % miners.size());
        newBlock.mine(Configuration.instance.difficulty);
        this.network.add(newBlock);
        this.previousBlock = newBlock;

        /*try {
            File file = Path.of("blockchain.json").toAbsolutePath().toFile();
            file.createNewFile();
            FileWriter fileWriter = new FileWriter(file);
            new GsonBuilder().setPrettyPrinting().create().toJson(this, fileWriter);
            fileWriter.close();
        } catch (IOException e){
            e.printStackTrace();
        }*/
    }


    public static Network getInstance() {
        if (instance == null) {
            instance = new Network();
            //instance.setup();
        }
        Double val = instance.satoshiNakamoto.getBalance();
        return instance;
    }

    public void incrementTransactionSequence() {
        this.sequence++;
    }

    public int getTransactionSequence() {
        return sequence;
    }

    public HashMap<String, TransactionOutput> getUtx0Map() {
        return Configuration.instance.utx0Map;
    }

    public Wallet requestBTCBuying(BTC amount){
        for(Miner m : miners){
            if(m.getWallet().getBalance() >= amount.getAmount()) return m.getWallet();
        }
        if(satoshiNakamoto.getBalance() >= amount.getAmount()) {
            return this.satoshiNakamoto;
        }
        return null;
    }

    public void check(){
        if (!this.encrypted) return;
        if (this.wallet.getBalance() < this.amount) return;
        if (Network.getInstance().isPaid()) {
            this.paid = true;
        }
    }

    public boolean isPaid(){
        Block block;
        String hashTarget = Utility.getDifficultyString(Configuration.instance.difficulty);
        HashMap<String, TransactionOutput> tempUtx0 = new HashMap<>();
        tempUtx0.put(this.genesisBlockTransaction.getOutputs().get(0).getID(), this.genesisBlockTransaction.getOutputs().get(0));

        for (int i = 1; i < this.network.size(); i++){
            block = this.network.get(i);

            if (!block.getHash().equals(block.calculateHash())){
                return false;
            }
            if(!block.getHash().substring(0, Configuration.instance.difficulty).equals(hashTarget)){
                return false;
            }
        }

        return true;
    }

    public Wallet getSatoshiNakamoto() {
        return satoshiNakamoto;
    }
}