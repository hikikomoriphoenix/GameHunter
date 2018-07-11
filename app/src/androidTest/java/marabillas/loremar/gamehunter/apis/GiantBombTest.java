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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import marabillas.loremar.gamehunter.framework.Query;
import marabillas.loremar.gamehunter.framework.ResultsItem;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class GiantBombTest {
    private BaseAPI api;

    @Before
    public void init() {
        api = new GiantBomb();
    }

    @Test
    public void getPlatformFilters() {
        Set<String> filters = null;

        try {
            filters = api.getPlatformFilters();
        } catch (BaseAPIGetterFailedToGetException e) {
            Assert.fail(e.getMessage());
        }

        assertThat(filters.size(), is(155));
        String[] filtersArray = new String[filters.size()];
        filters.toArray(filtersArray);
        assertThat(filtersArray[0], is("3DO"));
        assertThat(filtersArray[154], is("ZX Spectrum"));
    }

    @Test
    public void getSortChoices() {
        Set<String> choices = null;

        try {
            choices = api.getSortChoices();
        } catch (BaseAPIGetterFailedToGetException e) {
            Assert.fail(e.getMessage());
        }

        assertThat(choices.size(), is(7));
        String[] choicesArray = new String[choices.size()];
        choices.toArray(choicesArray);
        assertThat(choicesArray[0], is("Date added"));
        assertThat(choicesArray[6], is("Original game release date"));
    }

    @Test
    public void query() {
        Query query = new Query();
        query.setKeyword("robot");
        BaseAPI api = new GiantBomb();
        List<ResultsItem> results = new ArrayList<>();
        try {
            results = api.query(query);
        } catch (BaseAPIFailedQueryException e) {
            Assert.fail(e.getMessage());
        }
        assertThat(results.get(0).title, is("Robot"));
        assertThat(results.get(0).thumbnailURL, is("https://www.giantbomb.com/api/image/scale_avatar/2853576-gblogo.png"));
        assertThat(results.get(0).description, is(nullValue()));
        assertThat(results.get(0).releaseDate, is(nullValue()));
        assertThat(results.get(0).id, is("3030-3840"));
    }
}