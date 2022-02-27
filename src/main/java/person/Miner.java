package person;

import blockchain.Transaction;
import currency.BTC;

import java.security.PublicKey;

public class Miner extends Person{
    public Miner(String name, Integer id){
        super(name, id);
    }

    public Transaction buyBTC(BTC amount, PublicKey recipient){
        return this.wallet.sendFunds(recipient, amount.getAmount());
    }

}
