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

import marabillas.loremar.gamehunter.framework.Query;
import marabillas.loremar.gamehunter.framework.ResultsItem;

/**
 * This class attempts to wrap any available API from any website with a video games database. It
 * is meant to be subclassed for each specific API.
 */
public abstract class BaseAPI {
    private Set<Feature> configuration;

    protected enum Feature {
        /**
         * This feature means the API can provide thumbnails for each game in the results
         */
        THUMBNAIL,

        /**
         * This feature means the API can provide a brief description of each game in the results
         */
        DESCRIPTION,

        /**
         * This feature means the API can provide the release date of each game in the results
         */
        RELEASE_DATE,

        /**
         * This feature means the API can show results based on the platform the game is
         * developed for.
         */
        FILTER_BY_PLATFORM,

        /**
         * This feature means the API can show results based on genre
         */
        FILTER_BY_GENRE,

        /**
         * This feature means the API can show results based on theme
         */
        FILTER_BY_THEME,

        /**
         * This feature means the API can show results based on the year the game was released
         */
        FILTER_BY_YEAR,

        /**
         * This feature means the API can show results of video games that are released on
         * between a set of range of years.
         */
        FILTER_BY_YEARS,

        /**
         * This feature means the API can show ordered results
         */
        SORT_BY,

        /**
         * This feature supports ascending and descending options when ordering results. SORT_BY
         * feature is implied to be supported, hence, there is no need to include Feature.SORT_BY
         * in configuration when Feature.SORT_BY_REVERSIBLE is included.
         */
        SORT_BY_REVERSIBLE,

        /**
         * This feature means this API returns a partial amount of the total results as a page.
         * You have to indicate which page you want to retrieve for partial results.
         */
        PAGES,

        /**
         * This feature allows you to indicate how many results per page to show. PAGES feature
         * is implied to be supported, hence, there is no need to include Feature
         * .RESULTS_PER_PAGE in configuration when Feature.RESULTS_PER_PAGE is included.
         */
        RESULTS_PER_PAGE,

        /**
         * This feature allows you to search the game database using a keyword.
         */
        SEARCH,

        /**
         * This feature means the API can provide thumbnails for search results. SEARCH feature
         * is implied to be supported, hence, there is no need to include Feature.SEARCH in
         * configuration when Feature.SEARCH_THUMBNAIL is included.
         */
        SEARCH_THUMBNAIL,

        /**
         * This feature means the API can provide a brief description for each search result.
         * SEARCH feature is implied to be supported, hence, there is no need to include Feature
         * .SEARCH in configuration when Feature.SEARCH_DESCRIPTION is included.
         */
        SEARCH_DESCRIPTION,

        /**
         * This feature mean the API can provide the release date of each game in the search
         * results. SEARCH feature is implied to be supported, hence, there is no need to include
         * Feature.SEARCH in configuration when Feature.SEARCH_RELEASE_DATE is included.
         */
        SEARCH_RELEASE_DATE,

        /**
         * This feature means the API can show results based on the platform the game is
         * developed for. SEARCH feature is implied to be supported, hence, there is no need to include
         * Feature.SEARCH in configuration
         */
        SEARCH_FILTER_BY_PLATFORM,

        /**
         * This feature means the API can show results based on genre. SEARCH feature is implied
         * to be supported, hence, there is no need to include Feature.SEARCH in configuration.
         */
        SEARCH_FILTER_BY_GENRE,

        /**
         * This feature means the API can show results based on theme. SEARCH feature is implied
         * to be supported, hence, there is no need to include Feature.SEARCH in configuration.
         */
        SEARCH_FILTER_BY_THEME,

        /**
         * This feature means the API can show results based on the year the game was released.
         * SEARCH feature is implied to be supported, hence, there is no need to include Feature
         * .SEARCH in configuration.
         */
        SEARCH_FILTER_BY_YEAR,

        /**
         * This feature means the API can show results of video games that are released on
         * between a set of range of years. SEARCH feature is implied to be supported, hence,
         * there is no need to include Feature.SEARCH in configuration.
         */
        SEARCH_FILTER_BY_YEARS,

        /**
         * This feature means the API can show ordered results. SEARCH feature is implied to be
         * supported, hence, there is no need to include Feature.SEARCH in configuration.
         */
        SEARCH_SORT_BY,

        /**
         * This feature supports ascending and descending options when ordering results. SORT_BY
         * and SEARCH features are implied to be supported, hence, there is no need to include
         * Feature.SORT_BY or Feature.SEARCH in configuration when Feature.SEARCH_SORT_BY_REVERSIBLE
         * is included.
         */
        SEARCH_SORT_BY_REVERSIBLE,

        /**
         * This feature means this API returns a partial amount of the total search results as a
         * page. You have to indicate which page you want to retrieve for partial results.
         */
        SEARCH_PAGES,

        /**
         * This feature allows you to indicate how many results per page to show. PAGES feature
         * and SEARCH feature are implied to be supported, hence, there is no need to include
         * Feature.PAGES or Feature.SEARCH in configuration when Feature.SEARCH_RESULTS_PER_PAGE is
         * included.
         */
        SEARCH_RESULTS_PER_PAGE
    }

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
     * @return a set containing the names of available game genres as filters
     */
    public abstract Set<String> getGenreFilters();

    /**
     * Gets all the gaming platforms that is made available by the API for the user to choose
     * from as filters.
     *
     * @return a set containing the names of available gaming platforms as filters
     */
    public abstract Set<String> getPlatformFilters();

    /**
     * Gets all the choices for ordering results.
     *
     * @return a set containing all sort by choices.
     */
    public abstract Set<String> getSortChoices();

    /**
     * Gets all the themes that is made available by the API for the user to choose from as filters.
     *
     * @return a set containing the names of available themes as filters
     */
    public abstract Set<String> getThemeFilters();

    /**
     * Queries the game database for games that match the given fields set inside the Query object.
     *
     * @param query an object that contains fields used as parameters for querying the game
     *              database.
     * @return the results as a list of ResultsItem instances containing the required fields as
     * set in the Query instance passed to the query method.
     */
    public abstract List<ResultsItem> query(Query query);

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
    public boolean hasSearhFilterByTheme() {
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
