package hu.nik.project.environment.logger;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by Róbert on 2016.02.28..
 *
 * Test class for Logger
 */
public class LoggerTest {

    private static Logger logger;

    @BeforeClass
    public static void setUp() throws Exception {
        logger = new Logger("XML-Parse.log");
    }

    @Test
    public void testLog() throws Exception {
        logger.log("Test write!!");
    }

    @Test
    public void testNewLine() throws Exception {
        logger.emptyLine();
        logger.emptyLine();

        logger.log("New lines appended!");
    }
}