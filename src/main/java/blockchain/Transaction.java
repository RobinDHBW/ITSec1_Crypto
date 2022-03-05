package blockchain;

import configuration.Configuration;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;

public class Transaction {
    private final PublicKey sender;
    private final PublicKey recipient;
    private final Double value;
    private final ArrayList<TransactionOutput> outputs = new ArrayList<>();
    private final ArrayList<TransactionInput> inputs;
    private String id;
    private byte[] signature;

    public Transaction(PublicKey from, PublicKey to, Double value, ArrayList<TransactionInput> inputs) {
        this.sender = from;
        this.recipient = to;
        this.value = value;
        this.inputs = inputs;
    }

    private String calculateHash() {
        Configuration.instance.transactionSequence++;
        return Utility.sha256(Utility.getStringFromKey(sender) + Utility.getStringFromKey(recipient)
                + value + Configuration.instance.transactionSequence);
    }

    public void generateSignature(PrivateKey privateKey) {
        String data = Utility.getStringFromKey(sender) + Utility.getStringFromKey(recipient) + value;
        signature = Utility.applySig(privateKey, data);
    }

    public boolean verifySignature() {
        String data = Utility.getStringFromKey(sender) + Utility.getStringFromKey(recipient) + value;
        return Utility.verifySig(sender, data, signature);

    }

    public Boolean processTransaction() {
        try{
            if (!verifySignature()) {
                throw new Exception("#transaction signature failed to verify");
            }

            for (TransactionInput i : inputs) {
                i.setUtx0(Configuration.instance.utx0Map.get(i.getId()));
            }

            if (getInputsValue() < Configuration.instance.minimumTransaction) {
                throw new Exception("#transaction input to small | " + getInputsValue());
            }

            Double leftOver = getInputsValue() - value;
            id = calculateHash();
            outputs.add(new TransactionOutput(recipient, value, id));
            outputs.add(new TransactionOutput(sender, leftOver, id));

            for (TransactionOutput o : outputs) {
                Configuration.instance.utx0Map.put(o.getID(), o);
            }

            for (TransactionInput i : inputs) {
                if (i.getUTX0() == null) {
                    continue;
                }
                Configuration.instance.utx0Map.remove(i.getUTX0().getID());
            }

            return true;
        }catch (Exception ex){
            System.err.println(ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    public float getInputsValue() {
        float total = 0;

        for (TransactionInput i : inputs) {
            if (i.getUTX0() == null) {
                continue;
            }
            total += i.getUTX0().getValue();
        }

        return total;
    }

    public float getOutputsValue() {
        float total = 0;

        for (TransactionOutput o : outputs) {
            total += o.getValue();
        }

        return total;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PublicKey getSender() {
        return sender;
    }

    public PublicKey getRecipient() {
        return recipient;
    }

    public Double getValue() {
        return value;
    }

    public ArrayList<TransactionInput> getInputs() {
        return inputs;
    }

    public ArrayList<TransactionOutput> getOutputs() {
        return outputs;
    }
}
