package financial;

import currency.Currency;
import currency.Euro;

import java.util.Arrays;

public abstract class Depository implements ITransfer {
    protected final Double moneyFactor;
    protected Class currency;

    public Depository(Double moneyFactor, Class currency) {
        this.moneyFactor = moneyFactor;
        this.currency = currency;
    }

    @Override
    public Double calcConversion(Currency money) {
        return money.getAmount() * moneyFactor;
    }

    @Override
    public Boolean transfer(Currency money, ITransfer receiver) {
        try {
            Currency target = (Currency) (receiver.getCurrency()).getDeclaredConstructor().newInstance(receiver.calcConversion(money));
            if (receiver.receive(target)) {
                return true;
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            System.err.println(Arrays.toString(ex.getStackTrace()));
        }
        return false;
    }

    @Override
    public Boolean receive(Currency money) {
        try{
            if (!money.getClass().equals(this.currency))
                throw new Exception("Can't transfer " + money.getClass() + " to this Depository");
            return true;
        }catch (Exception ex){
            System.err.println(ex.getMessage());
            System.err.println(Arrays.toString(ex.getStackTrace()));
            return false;
        }
    }


    @Override
    public Class getCurrency() {
        return currency;
    }
}
