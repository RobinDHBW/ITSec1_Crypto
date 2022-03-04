package console;

import com.google.common.eventbus.EventBus;
import configuration.ConsoleCorrespondation;
import events.AttackEvent;
import events.VictimEvent;

import java.util.Arrays;
import java.util.List;

public class Console implements IConsole {
    private static final String ansiReset = "\u001B[0m";
    private final TextColor defaultColor;
    private final EventBus eventBus;
    private final Integer id;
    private Integer aIndex = 1;
    private Integer bIndex = 1;

    public Console(TextColor color, Integer id) {
        this.defaultColor = color;
        this.id = id;
        this.eventBus = new EventBus("Console_" + id);
    }

    public void addSubscriber(Subscriber subscriber) {
        eventBus.register(subscriber);
    }

    @Override
    public void writeln(String input, TextColor color) {
        System.out.println(color.getValue() + input + ansiReset);
    }

    @Override
    public void writeln(ConsoleCorrespondation input, TextColor color) {
        List<ConsoleCorrespondation> ccEvents = Arrays.asList(ConsoleCorrespondation.CL_LAUNCH, ConsoleCorrespondation.CL_SHOWRECIPIENT, ConsoleCorrespondation.M_TRANSACTIONSUCCESS, ConsoleCorrespondation.M_BUSTED);
        List<ConsoleCorrespondation> finEvents = Arrays.asList(ConsoleCorrespondation.CL_EXCHANGE, ConsoleCorrespondation.CL_SHOWBALANCE, ConsoleCorrespondation.CL_SHOWRECIPIENT, ConsoleCorrespondation.CL_PAYBTC, ConsoleCorrespondation.CL_CHECKPAYMENT, ConsoleCorrespondation.M_ENCRYPTED, ConsoleCorrespondation.M_RANSOMINCREASED);

        System.out.println(color.getValue() + input.getValue() + ansiReset);

        if (ccEvents.contains(input)) {
            eventBus.post(new AttackEvent(aIndex++, input));
        } else if (finEvents.contains(input)) {
            this.eventBus.post(new VictimEvent(bIndex++, input));
        }
    }

    public void writeln(ConsoleCorrespondation input) {
        this.writeln(input, defaultColor);
    }

    public void writeln(String input) {
        this.writeln(input, defaultColor);
    }
}
