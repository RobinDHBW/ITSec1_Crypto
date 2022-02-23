import leverCom.RansomwareFactory;

public class Application {
    private final Object fileEaterPort;

    public Application(){
        this.fileEaterPort = RansomwareFactory.build();
    }
    public static void main(String[] args) {
        Application app = new Application();
    }

}
