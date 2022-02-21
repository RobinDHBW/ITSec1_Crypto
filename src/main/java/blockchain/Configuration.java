package blockchain;

import java.util.ArrayList;
import java.util.HashMap;

// inspiration: 01/implementation.html and 05/implementation.html

public enum Configuration {
    instance;

    public final int difficulty = 5;

    Transaction genesisTransaction;
    HashMap<String, TransactionOutput> utx0Map = new HashMap<>();
    double minimumTransaction = 0.1;
    ArrayList<Block> blockchain = new ArrayList<>();
    int transactionSequence = 0;
}
