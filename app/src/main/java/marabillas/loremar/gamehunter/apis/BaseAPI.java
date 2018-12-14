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

import java.util.List;
import java.util.Set;

import io.reactivex.Observable;
import marabillas.loremar.gamehunter.components.Query;
import marabillas.loremar.gamehunter.components.ResultsItem;

/**
 * This class attempts to wrap any available API from any website with a video games database. It
 * is meant to be subclassed for each specific API.
 */
public abstract class BaseAPI {
    private Set<Feature> configuration;

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
     * Gets all the game genres that is made available by the API for the user to choose from as
     * filters.
     *
     * @return an observable that can be subscribed to to get genre filters.
     */
    public abstract Observable<Set<String>> getGenreFilters();

    /**
     * Gets all the gaming platforms that is made available by the API for the user to choose
     * from as filters.
     *
     * @return an observable that can be subscribed to to get platform filters.
     */
    public abstract Observable<Set<String>> getPlatformFilters();

    /**
     * Gets all the choices for ordering results.
     *
     * @return an observable that can be subscribed to to get choices.
     */
    public abstract Observable<Set<String>> getSortChoices();

    /**
     * Gets all the themes that is made available by the API for the user to choose from as filters.
     *
     * @return an observable that can be subscribed to to get theme filters.
     */
    public abstract Observable<Set<String>> getThemeFilters();

    /**
     * Computes total number of pages given total results and number of results per page.
     *
     * @param resultsPerPage Number of results per page. Should be the same number from that last
     *                       time the query method was called.
     * @return the total pages in long
     */
    public long getTotalPages(int resultsPerPage) {
        long totalResults = getTotatResultsFromLastQuery();
        if ((totalResults % resultsPerPage) > 0) {
            return (totalResults / resultsPerPage) + 1;
        } else {
            return totalResults / resultsPerPage;
        }
    }

    /**
     * Gets the total number of results from last time the query method was called.
     *
     * @return total results in long
     */
    public abstract long getTotatResultsFromLastQuery();

    /**
     * Queries the game database for games that match the given fields set inside the Query object.
     *
     * @param query an object that contains fields used as parameters for querying the game
     *              database.
     * @return an observable that can be subscribed on to get query results.
     */
    public abstract Observable<List<ResultsItem>> query(Query query);

    /**
     * Checks if {@link Feature#DESCRIPTION} is supported.
     *
     * @return true if supported, false if not
     */
    public boolean hasDescription() {
        return configuration.contains(Feature.DESCRIPTION);
    }


    /**
     * Checks if {@link Feature#FILTER_BY_GENRE} is supported
     *
     * @return true if supported, false if not
     */
    public boolean hasFilterByGenre() {
        return configuration.contains(Feature.FILTER_BY_GENRE);
    }

    /**
     * Checks if {@link Feature#FILTER_BY_PLATFORM} is supported
     *
     * @return true if supported, false if not
     */
    public boolean hasFilterByPlatform() {
        return configuration.contains(Feature.FILTER_BY_PLATFORM);
    }

    /**
     * Checks if {@link Feature#FILTER_BY_THEME} is supported
     *
     * @return true if supported, false if not
     */
    public boolean hasFilterByTheme() {
        return configuration.contains(Feature.FILTER_BY_THEME);
    }

    /**
     * Checks if {@link Feature#FILTER_BY_YEAR} is supported
     *
     * @return true if supported, false if not
     */
    public boolean hasFilterByYear() {
        return configuration.contains(Feature.FILTER_BY_YEAR);
    }

    /**
     * Checks if {@link Feature#FILTER_BY_YEARS} is supported
     *
     * @return true if supported, false if not
     */
    public boolean hasFilterByYears() {
        return configuration.contains(Feature.FILTER_BY_YEARS);
    }

    /**
     * Checks if {@link Feature#PAGES} is supported
     *
     * @return true if supported, false if not
     */
    public boolean hasPages() {
        return configuration.contains(Feature.PAGES) || configuration.contains(Feature.RESULTS_PER_PAGE);
    }

    /**
     * Checks if {@link Feature#RELEASE_DATE} is supported
     *
     * @return true if supported, false if not
     */
    public boolean hasReleaseDate() {
        return configuration.contains(Feature.RELEASE_DATE);
    }

    /**
     * Checks if {@link Feature#RESULTS_PER_PAGE} is supported
     *
     * @return true if supported, false if not
     */
    public boolean hasResultsPerPage() {
        return configuration.contains(Feature.RESULTS_PER_PAGE);
    }

    /**
     * Checks if {@link Feature#SEARCH} is supported
     *
     * @return true if supported, false if not
     */
    public boolean hasSearch() {
        return configuration.contains(Feature.SEARCH) || configuration.contains(Feature
                .SEARCH_THUMBNAIL) || configuration.contains(Feature.SEARCH_DESCRIPTION) ||
                configuration.contains(Feature.SEARCH_RELEASE_DATE) || configuration.contains
                (Feature.SEARCH_FILTER_BY_PLATFORM) || configuration.contains(Feature
                .SEARCH_FILTER_BY_GENRE) || configuration.contains(Feature
                .SEARCH_FILTER_BY_THEME) || configuration.contains(Feature.SEARCH_FILTER_BY_YEAR) ||
                configuration.contains(Feature.SEARCH_FILTER_BY_YEARS) || configuration.contains
                (Feature.SEARCH_SORT_BY) || configuration.contains(Feature
                .SEARCH_SORT_BY_REVERSIBLE) || configuration.contains(Feature.SEARCH_PAGES) ||
                configuration.contains(Feature.SEARCH_RESULTS_PER_PAGE);
    }

    /**
     * Checks if {@link Feature#SEARCH_DESCRIPTION} is supported
     *
     * @return true if supported, false if not
     */
    public boolean hasSearchDescription() {
        return configuration.contains(Feature.SEARCH_DESCRIPTION);
    }

    /**
     * Checks if {@link Feature#SEARCH_FILTER_BY_GENRE} is supported
     *
     * @return true if supported, false if not
     */
    public boolean hasSearchFilterByGenre() {
        return configuration.contains(Feature.SEARCH_FILTER_BY_GENRE);
    }

    /**
     * Checks if {@link Feature#SEARCH_FILTER_BY_PLATFORM} is supported
     *
     * @return true if supported, false if not
     */
    public boolean hasSearchFilterByPlatform() {
        return configuration.contains(Feature.SEARCH_FILTER_BY_PLATFORM);
    }

    /**
     * Checks if {@link Feature#SEARCH_FILTER_BY_THEME)} is supported
     *
     * @return true if supported, false if not
     */
    public boolean hasSearchFilterByTheme() {
        return configuration.contains(Feature.SEARCH_FILTER_BY_THEME);
    }

    /**
     * Checks if {@link Feature#SEARCH_FILTER_BY_YEAR} is supported
     *
     * @return true if supported, false if not
     */
    public boolean hasSearchFilterByYear() {
        return configuration.contains(Feature.SEARCH_FILTER_BY_YEAR);
    }

    /**
     * Checks if {@link Feature#SEARCH_FILTER_BY_YEARS} is supported
     *
     * @return true if supported, false if not
     */
    public boolean hasSearchFilterByYears() {
        return configuration.contains(Feature.SEARCH_FILTER_BY_YEARS);
    }

    /**
     * Checks if {@link Feature#SEARCH_PAGES)} is supported
     *
     * @return true if supported, false if not
     */
    public boolean hasSearchPages() {
        return configuration.contains(Feature.SEARCH_PAGES) || configuration.contains(Feature
                .SEARCH_RESULTS_PER_PAGE);
    }

    /**
     * Checks if {@link Feature#SEARCH_RELEASE_DATE} is supported
     *
     * @return true if supported, false if not
     */
    public boolean hasSearchReleaseDate() {
        return configuration.contains(Feature.SEARCH_RELEASE_DATE);
    }

    /**
     * Checks if {@link Feature#SEARCH_RESULTS_PER_PAGE} is supported
     *
     * @return true if supported, false if not
     */
    public boolean hasSearchResultsPerPage() {
        return configuration.contains(Feature.SEARCH_RESULTS_PER_PAGE);
    }

    /**
     * Checks if {@link Feature#SEARCH_SORT_BY} is supported
     *
     * @return true if supported, false if not
     */
    public boolean hasSearchSortBy() {
        return configuration.contains(Feature.SEARCH_SORT_BY) || configuration.contains(Feature
                .SEARCH_SORT_BY_REVERSIBLE);
    }

    /**
     * Checks if {@link Feature#SEARCH_SORT_BY_REVERSIBLE} is supported
     *
     * @return true if supported, false if not
     */
    public boolean hasSearchSortByReversible() {
        return configuration.contains(Feature.SEARCH_SORT_BY_REVERSIBLE);
    }

    /**
     * Checks if {@link Feature#SEARCH_THUMBNAIL} is supported
     *
     * @return true if supported, false if not
     */
    public boolean hasSearchThumbnail() {
        return configuration.contains(Feature.SEARCH_THUMBNAIL);
    }

    /**
     * Checks if {@link Feature#SORT_BY} is supported
     *
     * @return true if supported, false if not
     */
    public boolean hasSort() {
        return configuration.contains(Feature.SORT_BY) || configuration.contains(Feature.SORT_BY_REVERSIBLE);
    }

    /**
     * Checks if {@link Feature#SORT_BY_REVERSIBLE} is supported
     *
     * @return true if supported, false if not
     */
    public boolean hasSortByReversible() {
        return configuration.contains(Feature.SORT_BY_REVERSIBLE);
    }

    /**
     * Checks if {@link Feature#THUMBNAIL} is supported
     *
     * @return true if supported, false if not
     */
    public boolean hasThumbnails() {
        return configuration.contains(Feature.THUMBNAIL);
    }
}
