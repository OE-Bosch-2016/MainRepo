import hu.nik.project.environment.Scene;
import hu.nik.project.environment.ScenePoint;
import hu.nik.project.environment.XMLParserException;

/**
 * Created by Róbert on 2016.02.27..
 *
 * Main class of the ConsoleApp
 */
public class Main {

    public static void main(String [ ] args) {

        try {
            String xmlPath;

            if( args.length > 0)
                xmlPath = args[0];
            else
                xmlPath = "TestSource\\testScene.xml";

            Scene scene = new Scene(xmlPath);
            System.out.println("Load scene from " + xmlPath);
            System.out.println(scene.toString());

        }catch (XMLParserException e) {
            System.out.println("Error during the XML parsing: " + e.getMessage());
        }
    }
}