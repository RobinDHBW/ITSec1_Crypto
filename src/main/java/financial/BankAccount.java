package financial;

import currency.Currency;
import currency.Euro;

public class BankAccount extends Depository{

    public BankAccount(Double seedCapital){
        super(seedCapital, 1.0, Euro.class);
    }

    @Override
    public Boolean transfer(Currency money, ITransfer receiver) {
        return super.transfer(money, receiver);
    }

    @Override
    public Boolean receive(Currency money) {
        return super.receive(money);
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
