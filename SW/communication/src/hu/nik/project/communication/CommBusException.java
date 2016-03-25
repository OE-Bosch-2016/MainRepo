package hu.nik.project.communication;

/**
 * Created by zhodvogner on 2016.03.19.
 *
 * Communication bus exception class
 */
public class CommBusException extends Exception {
    public CommBusException(String message) {
        super(message);
    }
}
