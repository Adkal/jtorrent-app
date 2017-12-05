package client;

import com.turn.ttorrent.client.Client;
import com.turn.ttorrent.client.SharedTorrent;
import exception.JTorrentClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

/**
 * Created by Adrian Kalata on 2017-11-15.
 */
public class JTorrentClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(JTorrentClient.class);

    public static Client createDownloadClient(String sourcePath, String tagretPath) throws JTorrentClientException {
        try {

            LOGGER.info("Creating new torrent client(sourcePath: " + sourcePath +
                    ", targetPath: " + tagretPath);
            return new Client(

                    InetAddress.getLocalHost(),
                    SharedTorrent.fromFile(
                            new File(sourcePath),
                            new File(tagretPath))
            );
        } catch (IOException e) {
            throw new JTorrentClientException("error while creating JTorrentClient Object");
        }
    }

    public static Client startDownloadWithDefault(Client client){
        return startDownload(client, 50.0, 50.0);
    }

    public static Client startDownload(Client client, double maxDownloadRate, double maxUploadRate) {

        LOGGER.info("------------------------------------");
        LOGGER.info("Starting download file: " + client.getTorrent().getName());
        LOGGER.info("Max download rate: " + maxDownloadRate);
        LOGGER.info("Max upload rate: " + maxUploadRate);
        LOGGER.info("Peers: " + client.getPeers());
        LOGGER.info("------------------------------------");
        client.setMaxDownloadRate(maxDownloadRate);
        client.setMaxUploadRate(maxUploadRate);
        client.download();
        LOGGER.info("Download started, waiting for finish....");
        //client.waitForCompletion();
        return client;
    }

    public static Client addObserver(Client client){
        LOGGER.info("Observer added to " + client.getTorrent().getName());
        client.addObserver((observable, data) -> {
            Client client1 = (Client) observable;
            LOGGER.info(String.valueOf(client1.getTorrent().getCompletion()));
            LOGGER.info(String.valueOf(client1.getState()));
        });
        return client;
    }

}
