package testReflectionComponent;

import leverCom.RansomwareReflector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class TestComponent {
    private RansomwareReflector reflector;

    static void copy(Path source, Path dest){
        try {
            Files.copy(source, dest, StandardCopyOption.REPLACE_EXISTING);
        }catch (Exception ex){
            System.err.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    @BeforeEach
    public void init(){
        this.reflector = new RansomwareReflector();


        //Reset files in directory
        try {
            Path source = Paths.get(ConfigurationUnitTest.instance.pathToSource);
            Files.walk(Paths.get(ConfigurationUnitTest.instance.pathToAttack)).filter(Files::isRegularFile).map(Path::toFile).forEach(File::delete);
            Files.walk(source).forEach(file -> copy(file, Paths.get(ConfigurationUnitTest.instance.pathToAttack).resolve(source.relativize(file))));
        }catch (Exception ex){
            System.err.println(ex.getMessage());
            ex.printStackTrace();
        }


    }

    @Test
    public void testEncryption(){
        reflector.setPath(ConfigurationUnitTest.instance.pathToAttack);
       assertTrue(reflector.encrypt());
    }

    @Test
    public void testDecryption(){
        reflector.setPath(ConfigurationUnitTest.instance.pathToAttack);
        reflector.encrypt();
        assertTrue(reflector.decrypt());
    }

    @Test
    public void testRename(){
        reflector.setPath(ConfigurationUnitTest.instance.pathToAttack);
        assertTrue(reflector.rename(".tst01", true));
    }

}
