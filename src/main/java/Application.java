import szenario.Attack;
import szenario.AttackComplex;

public class Application {
    public static void main(String[] args) {
        try {

            Attack attack = Attack.getInstance();
            attack.start();

            AttackComplex attackComplex = AttackComplex.getInstance();
            attackComplex.start();

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            ex.printStackTrace();
        }

    }

}
