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

import org.junit.Test;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import marabillas.loremar.gamehunter.framework.Query;
import marabillas.loremar.gamehunter.framework.ResultsItem;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class BaseAPITest {

    @Test
    public void testConfigurations() {
        BaseAPI api = new BaseAPI() {
            @Override
            protected Set<Feature> configure() {
                return EnumSet.of(Feature.THUMBNAIL, Feature.DESCRIPTION, Feature.RELEASE_DATE,
                        Feature.FILTER_BY_PLATFORM, Feature.FILTER_BY_GENRE, Feature
                                .FILTER_BY_THEME, Feature.FILTER_BY_YEAR, Feature
                                .FILTER_BY_YEARS, Feature.SORT_BY_REVERSIBLE, Feature
                                .RESULTS_PER_PAGE, Feature.SEARCH_THUMBNAIL, Feature
                                .SEARCH_DESCRIPTION, Feature.SEARCH_RELEASE_DATE, Feature
                                .SEARCH_FILTER_BY_PLATFORM, Feature.SEARCH_FILTER_BY_GENRE,
                        Feature.SEARCH_FILTER_BY_THEME, Feature.SEARCH_FILTER_BY_YEAR, Feature
                                .SEARCH_FILTER_BY_YEARS, Feature.SEARCH_SORT_BY_REVERSIBLE,
                        Feature.SEARCH_RESULTS_PER_PAGE);
            }

            @Override
            public Set<String> getGenreFilters() {
                return null;
            }

            @Override
            public Set<String> getPlatformFilters() {
                return null;
            }

            @Override
            public Set<String> getSortChoices() {
                return null;
            }

            @Override
            public Set<String> getThemeFilters() {
                return null;
            }

            @Override
            public List<ResultsItem> query(Query query) {
                return null;
            }
        };
        assertThat(api.hasThumbnails(), is(true));
        assertThat(api.hasDescription(), is(true));
        assertThat(api.hasReleaseDate(), is(true));
        assertThat(api.hasFilterByPlatform(), is(true));
        assertThat(api.hasFilterByGenre(), is(true));
        assertThat(api.hasFilterByTheme(), is(true));
        assertThat(api.hasFilterByYear(), is(true));
        assertThat(api.hasFilterByYears(), is(true));
        assertThat(api.hasSort(), is(true));
        assertThat(api.hasSortByReversible(), is(true));
        assertThat(api.hasPages(), is(true));
        assertThat(api.hasResultsPerPage(), is(true));
        assertThat(api.hasSearch(), is(true));
        assertThat(api.hasSearchThumbnail(), is(true));
        assertThat(api.hasSearchDescription(), is(true));
        assertThat(api.hasSearchReleaseDate(), is(true));
        assertThat(api.hasSearchFilterByPlatform(), is(true));
        assertThat(api.hasSearchFilterByGenre(), is(true));
        assertThat(api.hasSearhFilterByTheme(), is(true));
        assertThat(api.hasSearchFilterByYear(), is(true));
        assertThat(api.hasSearchFilterByYears(), is(true));
        assertThat(api.hasSearchSortBy(), is(true));
        assertThat(api.hasSearchSortByReversible(), is(true));
        assertThat(api.hasSearchPages(), is(true));
        assertThat(api.hasSearchResultsPerPage(), is(true));
    }
}