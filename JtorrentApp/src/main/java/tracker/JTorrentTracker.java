package tracker;

import com.turn.ttorrent.tracker.TrackedTorrent;
import com.turn.ttorrent.tracker.Tracker;
import exception.JTorrentTrackerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Arrays;

/**
 * Created  on 2017-11-15.
 */
public class JTorrentTracker {

    private static final Logger LOGGER = LoggerFactory.getLogger(JTorrentTracker.class);


    public static Tracker createTracker(Integer port) throws JTorrentTrackerException {
        try {
            return new Tracker(new InetSocketAddress(port));
        } catch (IOException e) {
            throw new JTorrentTrackerException("error while creating JTorrentTracker");
        }
    }

    public static Tracker crateTrackerWithDefaultPort() throws JTorrentTrackerException {
        return createTracker(6969);
    }

    public static Tracker announceTorrents(Tracker tracker, String path) throws JTorrentTrackerException {

        java.util.List<File> filesToAnnounce = Arrays.asList(
                java.util.Optional.ofNullable(
                        new File(path).listFiles( (dir, name) -> name.endsWith(".torrent") )).orElse(null)
        );

        if(filesToAnnounce.isEmpty()) {
            LOGGER.warn("there are no files to announce.");
            return tracker;
        }
        LOGGER.info("files to announce list: " + filesToAnnounce);

        try {
            for(File f : filesToAnnounce) {
                tracker.announce(TrackedTorrent.load(f));
                LOGGER.info("announcing file: " + f.getName());
            }
        } catch (IOException e) {
            throw new JTorrentTrackerException("unexpected error while announcing.");
        }
        return tracker;

    }

}
