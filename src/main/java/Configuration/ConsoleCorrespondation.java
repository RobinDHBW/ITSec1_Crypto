package configuration;

public enum ConsoleCorrespondation {
    CL_LAUNCH("launch http://www.trust-me.mcg/report.jar"),
    CL_EXCHANGE("exchange 0.02755 BTC"),
    CL_SHOWBALANCE("show balance"),
    CL_SHOWRECIPIENT("show recipient"),
    CL_PAYBTC("pay 0.02755 BTC"),
    CL_CHECKPAYMENT("check payment"),
    M_ENCRYPTED("Oops, your files have been encrypted. With a payment of 0.02755 BTC all files will be decrypted"),
    M_TRANSACTIONSUCCESS("Transaction successful"),
    M_TRANSACTIONFAIL("Transaction failed"),
    M_RANSOMINCREASED("Amount to pay increased by 0,01 BTC to: "),
    M_RANSOMFINAL("Pay BTC immediately or your files will be irrevocably deleted");



    private String value;

    ConsoleCorrespondation(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
