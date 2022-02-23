package configuration;

public enum ConsoleCorrespondation {
    CL_LAUNCH("launch http://www.trust-me.mcg/report.jar"),
    CL_EXCHANGE("exchange 0.02755 BTC"),
    CL_SHOWBALANCE("show balance"),
    CL_SHOWRECIPIENT("show recipient"),
    CL_PAYBTC("pay 0.02755 BTC"),
    CL_CHECKPAYMENT("check payment"),
    M_ENCRYPTED("Oops, your files have been encrypted. With a payment of 0.02755 BTC all files will be decrypted");


    private final String value;

    ConsoleCorrespondation(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
