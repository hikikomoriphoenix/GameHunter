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
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

import marabillas.loremar.gamehunter.apis.giantbomb.GiantBomb;
import marabillas.loremar.gamehunter.program.Query;
import marabillas.loremar.gamehunter.program.ResultsItem;

import static marabillas.loremar.gamehunter.utils.LogUtils.log;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class GiantBombTest {
    private BaseAPI api;
    private Query query;
    private List<ResultsItem> results;
    private Set<Query.Field> fields;

    @Before
    public void init() {
        api = new GiantBomb();
        query = new Query();
        results = new ArrayList<>();
        fields = EnumSet.of(Query.Field.THUMBNAIL, Query.Field.DESCRIPTION, Query.Field
                .RELEASE_DATE, Query.Field.ID);
        query.setFields(fields);
    }

    @Test
    public void getPlatformFilters() {
        Set<String> filters;

        APICallback apiCallback = new APICallbackTest();
        api.getPlatformFilters(apiCallback);

        CountDownLatch cd = new CountDownLatch(1);
        ((APICallbackTest) apiCallback).setCountDownLatch(cd);

        try {
            log("Waiting for platform filters to be obtained.");
            cd.await();
        } catch (InterruptedException e) {
            Assert.fail("Await interrupted: " + e.getMessage());
        }

        filters = ((APICallbackTest) apiCallback).getPlatformFilters();

        assertThat(filters.size(), is(157));
        String[] filtersArray = new String[filters.size()];
        filters.toArray(filtersArray);
        assertThat(filtersArray[0], is("3DO"));
        assertThat(filtersArray[156], is("ZX Spectrum"));
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
        assertThat(choicesArray[6], is("Original release date"));
    }

    @Test
    public void query() {
        // Query via search
        query.setKeyword("robot");
        results = queryCall(query);

        assertThat(results.size(), is(20));

        assertThat(results.get(0).title, is("Robot"));
        assertThat(results.get(0).thumbnailURL, is("https://www.giantbomb.com/api/image/scale_avatar/2853576-gblogo.png"));
        assertThat(results.get(0).description, is(nullValue()));
        assertThat(results.get(0).releaseDate, is(nullValue()));
        assertThat(results.get(0).id, is("3030-3840"));

        assertThat(results.get(19).title, is("Super Robot Wars X"));
        assertThat(results.get(19).thumbnailURL, is("https://www.giantbomb.com/api/image/scale_avatar/3017680-main.png"));
        assertThat(results.get(19).description, is("An entry in the Super Robot Wars series of " +
                "turn based mecha strategy, new anime entries include Gundam Reconguista In G, " +
                "Mashin Hero Wataru, Nadia: The Secret of Blue Water, and Buddy Complex."));
        assertThat(results.get(19).releaseDate, is("2018-03-29 00:00:00"));
        assertThat(results.get(19).id, is("3030-65986"));

        // Query via 'game' resource
        query = new Query();
        query.setFields(fields);
        results = queryCall(query);
        assertThat(results.size(), is(20));
        assertThat(results.get(0).title, is("Desert Strike: Return to the Gulf"));
        assertThat(results.get(19).title, is("Burntime"));
    }

    @Test
    public void queryFilter() {
        APICallbackTest callback = new APICallbackTest();
        CountDownLatch cd = new CountDownLatch(1);
        callback.setCountDownLatch(cd);
        api.getPlatformFilters(callback);
        try {
            cd.await();
        } catch (InterruptedException e) {
            Assert.fail("Await interrupted: " + e.getMessage());
        }

        query.setPlatformFilter("Android");
        results = queryCall(query);
        assertThat(results.get(0).title, is("Metal Slug X: Super Vehicle - 001"));
        assertThat(results.get(19).title, is("Comix Zone"));

        // Test platform filter with sort
        query.setSort("Name");
        query.setOrder(Query.Order.DESCENDING);
        results = queryCall(query);
        assertThat(results.get(0).title, is("Zyon"));
        assertThat(results.get(19).title, is("Zombie Flick"));

        // Test platform filter with sort and release year filter
        query.setReleaseYear(2018);
        query.setSort("Original release date");
        query.setOrder(Query.Order.ASCENDING);
        results = queryCall(query);
        assertThat(results.get(0).title, is("Pathfinder Duels"));
        assertThat(results.get(0).releaseDate, is("2018-01-04 00:00:00"));
        assertThat(results.get(19).title, is("Onmyoji Arena"));
        assertThat(results.get(19).releaseDate, is("2018-01-21 00:00:00"));

        // Test platform filter with release year
        query = new Query()
                .setFields(fields)
                .setPlatformFilter("PC")
                .setReleaseYear(2017);
        results = queryCall(query);
        assertThat(results.get(0).title, is("Pizza Tycoon"));
        assertThat(results.get(0).releaseDate, is("2017-01-05 00:00:00"));
        assertThat(results.get(19).title, is("River City Ransom: Underground"));
        assertThat(results.get(19).releaseDate, is("2017-02-27 00:00:00"));
    }

    @Test
    public void querySort() {
        query.setOrder(Query.Order.ASCENDING);

        query.setSort("Date added");
        results = queryCall(query);
        assertThat(results.get(0).title, is("Desert Strike: Return to the Gulf"));
        assertThat(results.get(19).title, is("Burntime"));

        query.setSort("Date last updated");
        results = queryCall(query);
        assertThat(results.get(0).title, is("Björnes magasin"));
        assertThat(results.get(19).title, is("The New Castle"));

        query.setSort("id");
        results = queryCall(query);
        assertThat(results.get(0).title, is("Desert Strike: Return to the Gulf"));
        assertThat(results.get(19).title, is("Burntime"));

        query.setSort("Number of user reviews");
        results = queryCall(query);
        assertThat(results.get(0).title, is("Terminators: The Video Game"));
    }

    @Test
    public void queryReleaseYear() {
        query.setReleaseYear(2013);
        results = queryCall(query);
        assertThat(results.get(0).title, is("Terror of the Catacombs"));
        assertThat(results.get(19).title, is("Metal Gear Rising: Revengeance"));

        // Test if fromYear and toYear are ignored if releaseYear is set
        query.setFromYear(2018);
        query.setToYear(2020);
        results = queryCall(query);
        assertThat(results.get(0).title, is("Terror of the Catacombs"));
        assertThat(results.get(19).title, is("Metal Gear Rising: Revengeance"));

        query.setSort("Original release date");
        query.setOrder(Query.Order.ASCENDING);
        results = queryCall(query);
        assertThat(results.get(0).title, is("Riajuu Plus ver. Komatsuzaki Kaname"));
        assertThat(results.get(0).releaseDate, is("2013-01-01 00:00:00"));

        query.setOrder(Query.Order.DESCENDING);
        results = queryCall(query);
        assertThat(results.get(0).releaseDate, is("2013-12-31 00:00:00"));

        // Test with fromYear set and toYear not set
        query = new Query()
                .setFields(fields)
                .setFromYear(2017)
                .setSort("Original release date")
                .setOrder(Query.Order.ASCENDING);
        results = queryCall(query);
        assertThat(results.get(0).title, is("Big Buck Hunter Arcade"));
        assertThat(results.get(0).releaseDate, is("2017-01-01 00:00:00"));
        assertThat(results.get(19).title, is("Saddies: Attack!!"));
        assertThat(results.get(19).releaseDate, is("2017-01-10 00:00:00"));

        // Test with toYear set and fromYear not set
        query = new Query()
                .setFields(fields)
                .setToYear(2017)
                .setSort("Original release date")
                .setOrder(Query.Order.ASCENDING);
        results = queryCall(query);
        assertThat(results.get(0).title, is("Billiard Japonais"));
        assertThat(results.get(0).releaseDate, is("1750-01-01 00:00:00"));

        query.setOrder(Query.Order.DESCENDING);
        results = queryCall(query);
        assertThat(results.get(0).releaseDate, is("2017-12-31 00:00:00"));

        // Test with both toYear and fromYear set
        query = new Query()
                .setFields(fields)
                .setFromYear(2015)
                .setToYear(2017)
                .setSort("Original release date")
                .setOrder(Query.Order.ASCENDING);
        results = queryCall(query);
        assertThat(results.get(0).title, is("Overture"));
        assertThat(results.get(0).releaseDate, is("2015-01-01 00:00:00"));

        query.setOrder(Query.Order.DESCENDING);
        results = queryCall(query);
        assertThat(results.get(0).releaseDate, is("2017-12-31 00:00:00"));
    }

    private List<ResultsItem> queryCall(Query query) {
        APICallbackTest callback = new APICallbackTest();
        CountDownLatch cd = new CountDownLatch(1);
        callback.setCountDownLatch(cd);

        api.query(query, callback);
        try {
            log("Waiting for query results.");
            cd.await();
        } catch (InterruptedException e) {
            Assert.fail("Await interrupted: " + e.getMessage());
        }

        return callback.getResults();
    }

    @Test
    public void testGetTotalResultsFromLastQuery() {
        query.setReleaseYear(2000);
        queryCall(query);
        assertThat(api.getTotatResultsFromLastQuery(), is(1360L));
        assertThat(api.getTotalPages(20), is(68L));
    }
}