package financial;

import currency.Currency;
import currency.Euro;

public class BankAccount extends Depository{
    protected Double credit;

    public BankAccount(Double seedCapital){
        super(1.0, Euro.class);
        this.credit = seedCapital;
    }

    @Override
    public Boolean transfer(Currency money, ITransfer receiver) {
        if(super.transfer(money, receiver)) {
            this.credit -= money.getAmount();
            return true;
        }
        return false;
    }

    @Override
    public Boolean receive(Currency money) {
        super.receive(money);
        credit += money.getAmount();
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
