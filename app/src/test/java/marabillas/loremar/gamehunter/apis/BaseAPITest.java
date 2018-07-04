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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class BaseAPITest {
    class TestAPI extends BaseAPI {
        @Override
        protected int configure() {
            return SEARCH | THUMBNAIL | DESCRIPTION | RELEASE_DATE | FILTER_BY_PLATFORM |
                    FILTER_BY_GENRE | FILTER_BY_THEME | FILTER_BY_YEAR | FILTER_BY_YEARS |
                    SORT_BY_ONE_WAY | SORT_BY_TWO_WAYS;
        }
    }

    @Test
    public void testConfigurations() {
        TestAPI api = new TestAPI();
        assertThat(api.hasSearch(), is(true));
        assertThat(api.hasThumbnails(), is(true));
        assertThat(api.hasDescription(), is(true));
        assertThat(api.hasReleaseDate(), is(true));
        assertThat(api.hasFilterByPlatform(), is(true));
        assertThat(api.hasFilterByGenre(), is(true));
        assertThat(api.hasFilterByTheme(), is(true));
        assertThat(api.hasFilterByYear(), is(true));
        assertThat(api.hasFilterByYears(), is(true));
        assertThat(api.hasSortByOneWay(), is(true));
        assertThat(api.hasSortByTwoWays(), is(true));
    }
}