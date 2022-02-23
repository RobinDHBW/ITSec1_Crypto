package financial;

import currency.Currency;
import currency.Euro;

public interface ITransfer {
    Boolean transfer(Currency money, ITransfer receiver);

    Boolean receive(Currency money);

    Double calcConversion(Currency money);

    Class getCurrency();
}
