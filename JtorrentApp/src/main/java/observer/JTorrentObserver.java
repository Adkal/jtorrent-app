package observer;

import com.turn.ttorrent.client.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Observer;

/**
 * Created by Adrian Kalata on 2017-11-15.
 */
public class JTorrentObserver {

    private static final Logger LOGGER = LoggerFactory.getLogger(JTorrentObserver.class);


    public static Observer getObserver(){
        return (observable, data) -> {
            Client client = (Client) observable;
            LOGGER.info(String.valueOf(client.getTorrent().getCompletion()));
        };
    }

}
