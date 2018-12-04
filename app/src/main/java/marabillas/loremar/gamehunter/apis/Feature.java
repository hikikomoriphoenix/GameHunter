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

public enum Feature {
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
