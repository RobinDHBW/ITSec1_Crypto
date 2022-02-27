package blockchain;

import currency.BTC;
import currency.Currency;
import financial.Depository;
import financial.ITransfer;

import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Wallet extends Depository {
    public HashMap<String, TransactionOutput> utx0Map = new HashMap<>();
    private PrivateKey privateKey;
    private PublicKey publicKey;

    public Wallet() {
        super(0.000019, BTC.class);
        generateKeyPair();
    }

    public void generateKeyPair() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA", "BC");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");

            keyGen.initialize(ecSpec, random);
            KeyPair keyPair = keyGen.generateKeyPair();

            privateKey = keyPair.getPrivate();
            publicKey = keyPair.getPublic();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Double getBalance() {
        Double total = 0.0;

        for (Map.Entry<String, TransactionOutput> item : Configuration.instance.utx0Map.entrySet()) {
            TransactionOutput utx0 = item.getValue();
            if (utx0.isMine(publicKey)) {
                utx0Map.put(utx0.getID(), utx0);
                total += utx0.getValue();
            }
        }

        return total;
    }

    public Transaction sendFunds(PublicKey recipient, Double value) {
        if (getBalance() < value) {
            System.out.println("#not enough funds to send transaction - transaction discarded");
            return null;
        }

        ArrayList<TransactionInput> inputs = new ArrayList<>();

        float total = 0;
        for (Map.Entry<String, TransactionOutput> item : utx0Map.entrySet()) {
            TransactionOutput utx0 = item.getValue();
            total += utx0.getValue();
            inputs.add(new TransactionInput(utx0.getID()));
            if (total > value) {
                break;
            }
        }

        Transaction transaction = new Transaction(publicKey, recipient, value, inputs);
        transaction.generateSignature(privateKey);

        for (TransactionInput input : inputs) {
            utx0Map.remove(input.getId());
        }

        return transaction;
    }

    public PrivateKey getPrivateKey() {
        return this.privateKey;
    }

    public PublicKey getPublicKey() {
        return this.publicKey;
    }

    @Override
    public Boolean transfer(Currency money, ITransfer receiver) {
        if(super.transfer(money, receiver)) {
            
            return true;
        }
        return false;
    }

    @Override
    public Boolean receive(Currency money) {
        super.receive(money);
        return true;
    }

    @Override
    public Double calcConversion(Currency money) {
        return super.calcConversion(money);
    }

    @Override
    public Class getCurrency() {
        return super.getCurrency();
    }
}
