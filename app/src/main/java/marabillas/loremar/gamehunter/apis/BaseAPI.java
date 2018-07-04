/*
 *     GameHunter is an Android app for searching video games
 *     Copyright (C) 2018 Loremar Marabillas
 *
 *     This program is free software; you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation; either version 2 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License along
 *     with this program; if not, write to the Free Software Foundation, Inc.,
 *     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package marabillas.loremar.gamehunter.apis;

/**
 * This class attempts to wrap any available api from any game database website. It is meant to
 * be subclassed for each specific api.
 */
public abstract class BaseAPI {
    // List of features that may be provided by the API
    protected static final int SEARCH = 0x10000000;
    protected static final int THUMBNAIL = 0x20000000;
    protected static final int DESCRIPTION = 0x30000000;
    protected static final int RELEASE_DATE = 0x40000000;
    protected static final int FILTER_BY_PLATFORM = 0x50000000;
    protected static final int FILTER_BY_GENRE = 0x60000000;
    protected static final int FILTER_BY_THEME = 0x70000000;
    protected static final int FILTER_BY_YEAR = 0x80000000;
    protected static final int FILTER_BY_YEARS = 0x90000000;
    protected static final int SORT_BY_ONE_WAY = 0x01000000;
    protected static final int SORT_BY_TWO_WAYS = 0x02000000;

    private int configuration;

    protected BaseAPI() {
        configuration = configure();
    }

    /**
     * The extending class must explicitly specify which features are available in the api.
     * Example:
     * <pre>
     * <code>
     * {@literal @}Override
     *              protected int configure() {
     *                  return SEARCH | THUMBNAIL;
     *              }
     * </code>
     * </pre>
     *
     * @return a set of features separated by a pipe{|} operator
     */
    protected abstract int configure();

    /**
     * Checks if the api can provide a brief description of each game in the resulting list
     *
     * @return true if brief descriptions is supported
     */
    public boolean hasDescription() {
        return (configuration & DESCRIPTION) == DESCRIPTION;
    }


    /**
     * Checks if the api can show results based on genre
     *
     * @return true if filter by genre is supported
     */
    public boolean hasFilterByGenre() {
        return (configuration & FILTER_BY_GENRE) == FILTER_BY_GENRE;
    }

    /**
     * Checks if the api can show results based on platform the game is developed for
     *
     * @return true if filter by platform is supported
     */
    public boolean hasFilterByPlatform() {
        return (configuration & FILTER_BY_PLATFORM) == FILTER_BY_PLATFORM;
    }

    /**
     * Checks if the api can show results based on theme
     *
     * @return true if filter by theme is supported
     */
    public boolean hasFilterByTheme() {
        return (configuration & FILTER_BY_THEME) == FILTER_BY_THEME;
    }

    /**
     * Checks if the api can show results based on the year the game was released
     *
     * @return true if filter by year is supported
     */
    public boolean hasFilterByYear() {
        return (configuration & FILTER_BY_YEAR) == FILTER_BY_YEAR;
    }

    /**
     * Checks if the api can show reults based on a range of release dates
     *
     * @return true if filter by years is supported
     */
    public boolean hasFilterByYears() {
        return (configuration & FILTER_BY_YEARS) == FILTER_BY_YEARS;
    }

    /**
     * Checks if the api can provide the release date of each game in the resulting list
     *
     * @return true if release date is supported
     */
    public boolean hasReleaseDate() {
        return (configuration & RELEASE_DATE) == RELEASE_DATE;
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
     * Checks if this API can return sorted results. This feature does not have ascending and
     * descending  options.
     *
     * @return true if this feature is supported
     */
    public boolean hasSortByOneWay() {
        return (configuration & SORT_BY_ONE_WAY) == SORT_BY_ONE_WAY;
    }

    /**
     * Checks if this API can return sorted results. This feature has ascending and descending
     * options
     *
     * @return true if this feature is supported
     */
    public boolean hasSortByTwoWays() {
        return (configuration & SORT_BY_TWO_WAYS) == SORT_BY_TWO_WAYS;
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
