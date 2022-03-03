package configuration;

import blockchain.Block;
import blockchain.Transaction;
import blockchain.TransactionOutput;

import java.util.ArrayList;
import java.util.HashMap;

public enum Configuration {
    instance;
    public final String userDirectory = System.getProperty("user.dir");
    public final String fileSeparator = System.getProperty("file.separator");

    public final String pathToAttack = userDirectory + fileSeparator + "HoneyPot" + fileSeparator;
    public final String pathToJavaArchive = userDirectory + fileSeparator + "ITSec1_Crypto_Component" + fileSeparator + "jar" + fileSeparator;
    public final String pathToJarsigner = "C:" + fileSeparator + "Program Files" + fileSeparator + "Java" + fileSeparator + "jdk-17.0.2" + fileSeparator + "bin" + fileSeparator + "jarsigner";

    public final Integer difficulty = 5;

    public Transaction genesisTransaction;
    public HashMap<String, TransactionOutput> utx0Map = new HashMap<>();
    public Double minimumTransaction = 0.001;
    public ArrayList<Block> blockchain = new ArrayList<>();
    public Integer transactionSequence = 0;
}
