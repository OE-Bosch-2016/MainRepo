package hu.nik.project.environment.logger;

/**
 * Created by Róbert on 2016.02.27..
 *
 * Logger class
 */

import java.io.IOException;
import java.nio.file.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Logger {

    private String filename;
    private Path logFilePath;
    private Timestamp startTime;

    public Logger(String filename) throws LoggerException {
        this.filename = filename;
        this.startTime = Timestamp.valueOf(LocalDateTime.now());

        createLog();
        log("-------------------------------------------------------------------------");
        log(String.format("Logging started at %1tc", startTime));
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

    public long getElapsedTime() {
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        return now.getTime() - startTime.getTime();
    }
}
