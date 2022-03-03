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
    private int nonce =0;

    private PublicKey pKey;
    private final double reward = 0.025;

    public Block(String previousHash, PublicKey pKey){
        this.previousHash = previousHash;
        this.pKey = pKey;
        this.timeStamp = new Date().getTime();
        this.hash = calculateHash();
    }

    public String calculateHash(){
        return Utility.sha256(previousHash + timeStamp + nonce + merkleRoot + Utility.getStringFromKey(this.pKey) + reward);
    }

    public void mine(int difficulty){
        //this.pKey = miner.getWallet().getPublicKey();
        merkleRoot = Utility.getMerkleRoot(transactions);
        String target = Utility.getDifficultyString(difficulty);

        while (!hash.substring(0, difficulty).equals(target)) {
            nonce++;
            hash = calculateHash();
        }

       // System.out.println("block mined | " + hash);
    }

    public void addTransaction(Transaction transaction) {
        try{
            if (transaction == null) {
                return;
            }

            if (!Objects.equals(previousHash, "0")) {
                if (!transaction.processTransaction()) {
                    throw  new Exception("transaction failed to process");
                }
            }

            transactions.add(transaction);
            //System.out.println("transaction added to block");
        }catch (Exception ex){
            System.err.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public String getHash() {
        return hash;
    }
}