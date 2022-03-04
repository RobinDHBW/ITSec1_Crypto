import blockchain.Block;
import blockchain.Network;
import blockchain.Transaction;
import blockchain.TransactionOutput;
import configuration.Configuration;
import financial.Wallet;
import person.Miner;
import szenario.Attack;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import szenario.AttackComplex;

import java.security.PublicKey;
import java.security.Security;

public class Application {
    public static void main(String[] args)
    {
        try {
            //Security.addProvider(new BouncyCastleProvider());
            ProcessBuilder processBuilder = new ProcessBuilder(Configuration.instance.pathToJarsigner, "-verify", Configuration.instance.pathToJavaArchive+"ITSec1_Crypto_Component.jar");
            Process process = processBuilder.start();
            process.waitFor();


            Attack attack = Attack.getInstance();
            attack.start();

            AttackComplex attackComplex = AttackComplex.getInstance();
            attackComplex.start();
        }catch (Exception ex){
            System.err.println(ex.getMessage());
            ex.printStackTrace();
        }

    }

}
