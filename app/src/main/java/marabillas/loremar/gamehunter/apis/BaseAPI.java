package marabillas.loremar.gamehunter.apis;

/**
 * This class attempts to wrap any available api from any game database website. It is meant to
 * be subclassed for each specific api.
 */
public abstract class BaseAPI {
    // List of features that may be provided by API
    protected final int SEARCH = 0x10000000;            // allows keyword input for searching
    protected final int THUMBNAIL = 0x20000000;         // provides thumbnails in the list

    private int configuration = 0x00000000;

    /**
     * sets what features are available for this particular API
     *
     * @param configuration features separated by the pipe(|) operator
     */
    protected void configure(int configuration) {
        this.configuration = configuration;
    }

    /**
     * Checks if this API allows user to input a keyword to search the database
     *
     * @return true if this feature is available
     */
    public boolean hasSearch() {
        return (configuration & SEARCH) == SEARCH;
    }

    /**
     * Checks if this API can provide thumbnails for the returned list of games
     *
     * @return true if thumbnails can be provided.
     */
    public boolean hasThumbnails() {
        return (configuration & THUMBNAIL) == THUMBNAIL;
    }
}
