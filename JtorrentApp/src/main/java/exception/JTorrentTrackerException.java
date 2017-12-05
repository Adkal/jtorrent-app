package exception;

/**
 * Created  on 2017-11-15.
 */
public class JTorrentTrackerException extends Exception {

    /**
     * Instantiates a new J torrent client exception.
     */
    public JTorrentTrackerException() {
        super();
    }

    /**
     * Instantiates a new J torrent client exception.
     *
     * @param message the message
     */
    public JTorrentTrackerException(String message) {
        super(message);
    }

    /**
     * Instantiates a new J torrent client exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public JTorrentTrackerException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new J torrent client exception.
     *
     * @param cause the cause
     */
    public JTorrentTrackerException(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new J torrent client exception.
     *
     * @param message            the message
     * @param cause              the cause
     * @param enableSuppression  the enable suppression
     * @param writableStackTrace the writable stack trace
     */
    protected JTorrentTrackerException(String message, Throwable cause,
                                      boolean enableSuppression,
                                      boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
