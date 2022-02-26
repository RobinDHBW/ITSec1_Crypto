import szenario.Attack;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Security;

public class Application {
    public static void main(String[] args)
    {
        Security.addProvider(new BouncyCastleProvider());
         Attack attack = new Attack();
         attack.start();

    }

}
