package exception;

/**
 * The type J torrent client exception.
 */
public class JTorrentClientException extends Exception {
    /**
     * Instantiates a new J torrent client exception.
     */
    public JTorrentClientException() {
        super();
    }

    /**
     * Instantiates a new J torrent client exception.
     *
     * @param message the message
     */
    public JTorrentClientException(String message) {
        super(message);
    }

    /**
     * Instantiates a new J torrent client exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public JTorrentClientException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new J torrent client exception.
     *
     * @param cause the cause
     */
    public JTorrentClientException(Throwable cause) {
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
    protected JTorrentClientException(String message, Throwable cause,
                        boolean enableSuppression,
                        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
