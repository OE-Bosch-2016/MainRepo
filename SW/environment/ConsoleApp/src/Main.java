import hu.nik.project.environment.Scene;
import hu.nik.project.environment.XMLParserException;

/**
 * Created by Róbert on 2016.02.27..
 *
 * Main class of the ConsoleApp
 */
public class Main {
    public static void main(String [ ] args) {
        try {
            Scene scene = new Scene("C:\\Users\\thecy\\Documents\\Android\\MainRepo\\SW\\environment\\TestSource\\testScene.xml");
            System.out.println(scene.toString());
        }catch (XMLParserException e) {
            System.out.println("Error during the XML parsing: " + e.getMessage());
        }
    }
}