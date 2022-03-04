package financial;

import currency.Currency;

public interface ITransfer {
    Boolean transfer(Currency money, ITransfer receiver);

    Boolean receive(Currency money);

    Double calcConversion(Currency money);

    Class getCurrency();
}
