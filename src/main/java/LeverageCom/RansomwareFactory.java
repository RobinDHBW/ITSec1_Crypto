package LeverageCom;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import Configuration.Configuration;



public class RansomwareFactory {
    @SuppressWarnings("unchecked")
    public static Object build(){
        Object compPort = null;

        try {
            URL[] urls = {new File(Configuration.instance.pathToJavaArchive+"EngineComp.jar").toURI().toURL()};
            URLClassLoader urlClassLoader = new URLClassLoader(urls, RansomwareFactory.class.getClassLoader());
            Class engineClass = Class.forName("Engine", true, urlClassLoader);
            Object engineInstance = engineClass.getMethod("getInstance").invoke(null);
            compPort = engineClass.getDeclaredField("port").get(engineInstance);
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return compPort;
    }
}
