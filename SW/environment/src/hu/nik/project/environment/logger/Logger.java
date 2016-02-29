package hu.nik.project.environment.logger;

/**
 * Created by Róbert on 2016.02.27..
 *
 * Logger class
 */

import java.io.IOException;
import java.nio.file.*;

public class Logger {

    private String filename;
    private Path logFilePath;

    public Logger(String filename) throws LoggerException {
        this.filename = filename;

        createLog();
        log("-------------------------------------------------------------------------");
        log("Logging started");
        emptyLine();
    }

    private void createLog() throws LoggerException {
        logFilePath = Paths.get(filename);

        try {
            if (!Files.exists(logFilePath)) {
                Files.createFile(logFilePath);
            }
        } catch (IOException e) {
            throw new LoggerException("Error creating log file: " + e.getMessage());
        }
    }

    public void log(String message) throws LoggerException {

        String messageWithNewLine = message + '\n';

        try {
            Files.write(logFilePath, messageWithNewLine.getBytes(), StandardOpenOption.APPEND);
        }catch (IOException e) {
            throw new LoggerException("Can't write to file: " + e.getMessage());
        }
    }

    public void emptyLine() throws LoggerException {
        log("");
    }
}
