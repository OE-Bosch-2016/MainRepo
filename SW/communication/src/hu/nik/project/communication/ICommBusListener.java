package hu.nik.project.communication;

import java.io.InputStream;
import java.util.stream.Stream;

/**
 * Created by zhodvogner on 2016.03.19.
 *
 * Interface of communication bus listener
 */
public interface ICommBusListener {

    public int unitID = 0;
    public void commBusEvent( InputStream inputStream );

}
