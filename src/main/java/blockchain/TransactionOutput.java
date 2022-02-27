package blockchain;

import java.security.PublicKey;

public class TransactionOutput {
    private final String id;
    private final PublicKey recipient;
    private final Double value;

    public TransactionOutput(PublicKey recipient, Double value, String parentTransactionId) {
        this.recipient = recipient;
        this.value = value;
        id = Utility.sha256(Utility.getStringFromKey(recipient) + value + parentTransactionId);
    }

    public boolean isMine(PublicKey publicKey) {
        return publicKey == recipient;
    }

    public String getID() {
        return id;
    }

    public PublicKey getRecipient() {
        return recipient;
    }

    public Double getValue() {
        return value;
    }
}
