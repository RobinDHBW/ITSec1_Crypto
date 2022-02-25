package blockchain;

import person.Miner;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;


// inspiration: 05/implementation.html

public class Block {
    private final String previousHash;
    private final long timeStamp;
    private final ArrayList<Transaction> transactions = new ArrayList<>();
    private String merkleRoot;
    private String hash;
    private int nonce;

    private PublicKey miner;
    private final double reward = 0.025;

    public Block(String previousHash){
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();
        this.hash = calculateHash();
    }

    public String calculateHash(){
        return Utility.sha256(previousHash + timeStamp + nonce + merkleRoot + Utility.getStringFromKey(miner) + reward);
    }

    public void mine(int difficulty, Miner miner){
        this.miner = miner.getWallet().getPublicKey();
        merkleRoot = Utility.getMerkleRoot(transactions);
        String target = Utility.getDifficultyString(difficulty);

        while (!hash.substring(0, difficulty).equals(target)) {
            nonce++;
            hash = calculateHash();
        }

        System.out.println("block mined | " + hash);
    }

    public void addTransaction(Transaction transaction) {
        if (transaction == null) {
            return;
        }

        if (!Objects.equals(previousHash, "0")) {
            if (!transaction.processTransaction()) {
                System.out.println("transaction failed to process");
                return;
            }
        }

        transactions.add(transaction);
        System.out.println("transaction added to block");
    }

    public String getHash() {
        return hash;
    }
}