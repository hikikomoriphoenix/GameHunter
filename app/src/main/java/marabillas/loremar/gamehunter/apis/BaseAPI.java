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

import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

import marabillas.loremar.gamehunter.framework.ResultsItem;

/**
 * This class attempts to wrap any available API from any website with a video games database. It
 * is meant to be subclassed for each specific API.
 */
public abstract class BaseAPI {
    protected enum Feature {
        SEARCH, SEARCH_FILTER, SEARCH_SORT, SEARCH_FILTER_SORT, THUMBNAIL, DESCRIPTION,
        RELEASE_DATE, FILTER_BY_PLATFORM, FILTER_BY_GENRE, FILTER_BY_THEME, FILTER_BY_YEAR,
        FILTER_BY_YEARS, SORT_BY_NO_REVERSE, SORT_BY_REVERSIBLE
    }

    //private int configuration;
    private Set<Feature> configuration;

    public enum Order {ASCENDING, DESCENDING}

    protected BaseAPI() {
        configuration = configure();
    }

    /**
     * The extending class must explicitly specify which features are available in the API.
     * Example:
     * <pre>
     * <code>
     * {@literal @}Override
     *              protected Set<Feature> configure() {
     *                  return EnumSet.of(Feature.SEARCH, Feature.THUMBNAIL);
     *              }
     * </code>
     * </pre>
     *
     * @return a set of features that the extending API can provide. Use EnumSet.of() for this. A
     * null value means results will only return title.
     */
    protected abstract Set<Feature> configure();

    /**
     * queries the game database using the given set of options. Extending API class must override
     * this method.
     *
     * @param keyword Results should contain this string
     * @param fields  what data to return with. Set to null to return only titles
     * @param filters Results are limited to games associated with these filters
     * @param sortBy  Results are ordered according to this value
     * @param order   set to Order.ASCENDING or Order.DESCENDING. Set to null if results are
     *                non-reversible
     * @return the results as a list of ResultsItem instances containing the required fields as
     * set in the fields parameter.
     */
    public abstract List<ResultsItem> query(@Nullable String keyword, @Nullable Set<String> fields,
                                            @Nullable Set<String> filters, @Nullable String sortBy,
                                            @Nullable Order order);

    /**
     * Checks if the API can provide a brief description of each game from the results
     *
     * @return true if brief descriptions is supported
     */
    public boolean hasDescription() {
        return configuration.contains(Feature.DESCRIPTION);
    }


    /**
     * Checks if the API can show results based on a genre
     *
     * @return true if filter by genre is supported
     */
    public boolean hasFilterByGenre() {
        return configuration.contains(Feature.FILTER_BY_GENRE);
    }

    /**
     * Checks if the API can show results based on a platform the game is developed for
     *
     * @return true if filter by platform is supported
     */
    public boolean hasFilterByPlatform() {
        return configuration.contains(Feature.FILTER_BY_PLATFORM);
    }

    /**
     * Checks if the API can show results based on its theme
     *
     * @return true if filter by theme is supported
     */
    public boolean hasFilterByTheme() {
        return configuration.contains(Feature.FILTER_BY_THEME);
    }

    /**
     * Checks if the API can show results based on the year the game was released
     *
     * @return true if filter by year is supported
     */
    public boolean hasFilterByYear() {
        return configuration.contains(Feature.FILTER_BY_YEAR);
    }

    /**
     * Checks if the API can show results of video games that are released on between a set range
     * of years
     *
     * @return true if filter by years is supported
     */
    public boolean hasFilterByYears() {
        return configuration.contains(Feature.FILTER_BY_YEARS);
    }

    /**
     * Checks if the API can provide the release date of each game from the results
     *
     * @return true if release date is supported
     */
    public boolean hasReleaseDate() {
        return configuration.contains(Feature.RELEASE_DATE);
    }

    /**
     * Checks if this API allows the user to input a keyword to search the database. This feature
     * has no options for filtering and ordering when querying via keyword.
     *
     * @return true if this feature is available
     */
    public boolean hasSearch() {
        return configuration.contains(Feature.SEARCH);
    }

    /**
     * Checks if this API allows the user to input a keyword to search the database. This feature
     * allows filtering when querying via keyword.
     *
     * @return true if this feature is available
     */
    public boolean hasSearchFilter() {
        return configuration.contains(Feature.SEARCH_FILTER);
    }

    /**
     * Checks if this API allows the user to input a keyword to search the database. This feature
     * allows sorting when querying via keyword.
     *
     * @return true if this feature is available
     */
    public boolean hasSearchSort() {
        return configuration.contains(Feature.SEARCH_SORT);
    }

    /**
     * Checks if this API allows the user to input a keyword to search the database. This feature
     * allows both filtering and sorting when querying via keyword.
     *
     * @return true if this feature is available
     */
    public boolean hasSearchFilterSort() {
        return configuration.contains(Feature.SEARCH_FILTER_SORT);
    }

    /**
     * Checks if this API can return ordered results. This feature does not have ascending and
     * descending  options.
     *
     * @return true if this feature is supported
     */
    public boolean hasSortByNoReverse() {
        return configuration.contains(Feature.SORT_BY_NO_REVERSE);
    }

    /**
     * Checks if this API can return ordered results. This feature has ascending and descending
     * options
     *
     * @return true if this feature is supported
     */
    public boolean hasSortByReversible() {
        return configuration.contains(Feature.SORT_BY_REVERSIBLE);
    }

    /**
     * Checks if this API can provide a thumbnail for each game in the results
     *
     * @return true if thumbnails can be provided.
     */
    public boolean hasThumbnails() {
        return configuration.contains(Feature.THUMBNAIL);
    }
}
