package configuration;

public enum Configuration {
    instance;
    public final String userDirectory = System.getProperty("user.dir");
    public final String fileSeparator = System.getProperty("file.separator");

    public final String pathToAttack = userDirectory + fileSeparator + "HoneyPot" + fileSeparator;
    public final String pathToJavaArchive = userDirectory + fileSeparator + "ITSec1_Crypto_Component" + fileSeparator + "jar" + fileSeparator;
}
