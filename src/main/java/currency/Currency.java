package currency;

public abstract class Currency {
    private final Double amount;
    public Currency(Double amount){
        this.amount = amount;
    }

    public Double getAmount() {
        return amount;
    }
}
