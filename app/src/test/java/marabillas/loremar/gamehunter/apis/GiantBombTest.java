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

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import marabillas.loremar.gamehunter.apis.giantbomb.GiantBomb;
import marabillas.loremar.gamehunter.components.Query;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class GiantBombTest {
    private BaseAPI api;
    private Query query;
    private Set<Query.Field> fields;

    @Before
    public void init() {
        api = new GiantBomb();
        query = new Query();
        fields = EnumSet.of(Query.Field.THUMBNAIL, Query.Field.DESCRIPTION, Query.Field
                .RELEASE_DATE, Query.Field.ID);
        query.setFields(fields);
    }

    @Test
    public void getPlatformFilters() {
        api.getPlatformFilters().blockingSubscribe(filters -> {
            assertThat(filters.size(), is(157));
            String[] filtersArray = new String[filters.size()];
            filters.toArray(filtersArray);
            assertThat(filtersArray[0], is("3DO"));
            assertThat(filtersArray[156], is("ZX Spectrum"));
        });
    }

    @Test
    public void getSortChoices() {
        api.getSortChoices().blockingSubscribe(choices -> {
            assertThat(choices.size(), is(7));
            String[] choicesArray = new String[choices.size()];
            choices.toArray(choicesArray);
            assertThat(choicesArray[0], is("Date added"));
            assertThat(choicesArray[6], is("Original release date"));
        });
    }

    @Test
    public void query() {
        // Query via search
        query.setKeyword("robot");
        api.query(query).blockingSubscribe(results -> {
            assertThat(results.size(), is(20));

            assertThat(results.get(0).title, is("Robot"));
            assertThat(results.get(0).thumbnailURL, is("https://www.giantbomb.com/api/image/scale_avatar/2853576-gblogo.png"));
            assertThat(results.get(0).description, is(nullValue()));
            assertThat(results.get(0).releaseDate, is(nullValue()));
            assertThat(results.get(0).id, is("3030-3840"));

            assertThat(results.get(19).title, is("Nintendo Labo Robot Kit"));
            assertThat(results.get(19).thumbnailURL, is("https://www.giantbomb.com/api/image/scale_avatar/2993022-7794510154-box_r.jpg"));
            String desc = results.get(19).description.replace("\\", "");
            assertThat(desc, is("Build a wearable \"robot backpack\" from pre-cut cardboard and " +
                    "smash through several motion-controlled game modes with Nintendo Labo's " +
                    "second construction kit."));
            assertThat(results.get(19).releaseDate, is("2018-04-20 00:00:00"));
            assertThat(results.get(19).id, is("3030-65430"));
        });

        // Query via 'game' resource
        query = new Query();
        query.setFields(fields);
        api.query(query).blockingSubscribe(results -> {
            assertThat(results.size(), is(20));
            assertThat(results.get(0).title, is("Desert Strike: Return to the Gulf"));
            assertThat(results.get(19).title, is("Burntime"));
        });
    }

    @Test
    public void queryFilter() {
        api.getPlatformFilters().blockingSubscribe(filters -> {
        });

        query.setPlatformFilter("Android");
        api.query(query).blockingSubscribe(results -> {
            assertThat(results.get(0).title, is("Metal Slug X: Super Vehicle - 001"));
            assertThat(results.get(19).title, is("Comix Zone"));
        });

        // Test platform filter with sort
        query.setSort("Name");
        query.setOrder(Query.Order.DESCENDING);
        api.query(query).blockingSubscribe(results -> {
            assertThat(results.get(0).title, is("Zyon"));
            assertThat(results.get(19).title, is("Zombie Flick"));
        });

        // Test platform filter with sort and release year filter
        query.setReleaseYear(2018);
        query.setSort("Original release date");
        query.setOrder(Query.Order.ASCENDING);
        api.query(query).blockingSubscribe(results -> {
            assertThat(results.get(0).title, is("Pathfinder Duels"));
            assertThat(results.get(0).releaseDate, is("2018-01-04 00:00:00"));
            assertThat(results.get(19).title, is("Onmyoji Arena"));
            assertThat(results.get(19).releaseDate, is("2018-01-21 00:00:00"));
        });

        // Test platform filter with release year
        query = new Query()
                .setFields(fields)
                .setPlatformFilter("PC")
                .setReleaseYear(2017);
        api.query(query).blockingSubscribe(results -> {
            assertThat(results.get(0).title, is("Pizza Tycoon"));
            assertThat(results.get(0).releaseDate, is("2017-01-05 00:00:00"));
            assertThat(results.get(19).title, is("The Unlikely Legend of Rusty Pup"));
            assertThat(results.get(19).releaseDate, is("2017-12-31 00:00:00"));
        });
    }

    @Test
    public void querySort() {
        query.setOrder(Query.Order.ASCENDING);
        query.setSort("Date added");
        api.query(query).blockingSubscribe(results -> {
            assertThat(results.get(0).title, is("Desert Strike: Return to the Gulf"));
            assertThat(results.get(19).title, is("Burntime"));
        });

        query.setSort("Date last updated");
        api.query(query).blockingSubscribe(results -> {
            assertThat(results.get(0).title, is("Björnes magasin"));
            assertThat(results.get(19).title, is("The New Castle"));
        });

        query.setSort("id");
        api.query(query).blockingSubscribe(results -> {
            assertThat(results.get(0).title, is("Desert Strike: Return to the Gulf"));
            assertThat(results.get(19).title, is("Burntime"));
        });

        query.setSort("Number of user reviews");
        api.query(query).blockingSubscribe(results -> assertThat(results.get(0).title, is("Terminators: The Video Game")));
    }

    @Test
    public void queryReleaseYear() {
        query.setReleaseYear(2013);
        api.query(query).blockingSubscribe(results -> {
            assertThat(results.get(0).title, is("Terror of the Catacombs"));
            assertThat(results.get(19).title, is("Metal Gear Rising: Revengeance"));
        });

        // Test if fromYear and toYear are ignored if releaseYear is set
        query.setFromYear(2018);
        query.setToYear(2020);
        api.query(query).blockingSubscribe(results -> {
            assertThat(results.get(0).title, is("Terror of the Catacombs"));
            assertThat(results.get(19).title, is("Metal Gear Rising: Revengeance"));
        });

        query.setSort("Original release date");
        query.setOrder(Query.Order.ASCENDING);
        api.query(query).blockingSubscribe(results -> {
            assertThat(results.get(0).title, is("Riajuu Plus ver. Komatsuzaki Kaname"));
            assertThat(results.get(0).releaseDate, is("2013-01-01 00:00:00"));
        });

        query.setOrder(Query.Order.DESCENDING);
        api.query(query).blockingSubscribe(results -> assertThat(results.get(0).releaseDate, is("2013-12-31 00:00:00")));

        // Test with fromYear set and toYear not set
        query = new Query()
                .setFields(fields)
                .setFromYear(2017)
                .setSort("Original release date")
                .setOrder(Query.Order.ASCENDING);
        api.query(query).blockingSubscribe(results -> {
            assertThat(results.get(0).title, is("Big Buck Hunter Arcade"));
            assertThat(results.get(0).releaseDate, is("2017-01-01 00:00:00"));
            assertThat(results.get(19).title, is("Saddies: Attack!!"));
            assertThat(results.get(19).releaseDate, is("2017-01-10 00:00:00"));
        });

        // Test with toYear set and fromYear not set
        query = new Query()
                .setFields(fields)
                .setToYear(2017)
                .setSort("Original release date")
                .setOrder(Query.Order.ASCENDING);
        api.query(query).blockingSubscribe(results -> {
            assertThat(results.get(0).title, is("Billiard Japonais"));
            assertThat(results.get(0).releaseDate, is("1750-01-01 00:00:00"));
        });

        query.setOrder(Query.Order.DESCENDING);
        api.query(query).blockingSubscribe(results -> assertThat(results.get(0).releaseDate, is("2017-12-31 00:00:00")));

        // Test with both toYear and fromYear set
        query = new Query()
                .setFields(fields)
                .setFromYear(2015)
                .setToYear(2017)
                .setSort("Original release date")
                .setOrder(Query.Order.ASCENDING);
        api.query(query).blockingSubscribe(results -> {
            assertThat(results.get(0).title, is("Overture"));
            assertThat(results.get(0).releaseDate, is("2015-01-01 00:00:00"));
        });

        query.setOrder(Query.Order.DESCENDING);
        api.query(query).blockingSubscribe(results -> assertThat(results.get(0).releaseDate, is("2017-12-31 00:00:00")));
    }

    @Test
    public void testGetTotalResultsFromLastQuery() {
        query.setReleaseYear(2000);
        api.query(query).blockingSubscribe(results -> {
            assertThat(api.getTotatResultsFromLastQuery(), is(1380L));
            assertThat(api.getTotalPages(20), is(69L));
        });
    }

    @Test
    public void testGetGameDetails() {
        api.getGameDetails("3030-13053")
                .blockingSubscribe(gameDetailsData -> {
                    assertThat(gameDetailsData.getTitle(), is("Final Fantasy VII"));
                    assertThat(gameDetailsData.getReleaseDate(), is("1997-01-31"));

                    String desc0 = "<h2>Overview</h2><p>Final Fantasy VII is a " +
                            "role-playing game developed by <a href=\"/squaresoft/3010-6323/\" " +
                            "data-ref-id=\"3010-6323\">Squaresoft</a> (now known as <a href=\"" +
                            "../../square-enix/65-104/\" rel=\"nofollow\">Square Enix</a>) and " +
                            "originally released for <a href=\"/playstation/3045-22/\" data-ref-id=";
                    assertThat(gameDetailsData.getDescription(), containsString(desc0));

                    String desc1 = "It is a 25 minute video which basically shows the ending the " +
                            "Crisis Core in anime form. The ending for Crisis Core was different" +
                            " from the Last Order OVA which was released first.</p>";
                    assertThat(gameDetailsData.getDescription(), containsString(desc1));

                    String[] platforms = {"PlayStation", "PlayStation Network (PS3)", "PC",
                            "iPhone", "PlayStation Network (PSP)", "iPad", "Android"};
                    List<String> pList = Arrays.asList(platforms);
                    assertTrue(gameDetailsData.getPlatforms().containsAll(pList));

                    String[] genres = {"Role-Playing"};
                    List<String> gList = Arrays.asList(genres);
                    assertTrue(gameDetailsData.getGenres().containsAll(gList));

                    String[] themes = {"Fantasy", "Sci-Fi", "Anime"};
                    List<String> tList = Arrays.asList(themes);
                    assertTrue(gameDetailsData.getThemes().containsAll(tList));

                    String[] developers = {"Squaresoft", "Square Visual Works"};
                    List<String> dList = Arrays.asList(developers);
                    assertTrue(gameDetailsData.getDevelopers().containsAll(dList));

                    String[] publishers = {"Squaresoft"};
                    List<String> puList = Arrays.asList(publishers);
                    assertTrue(gameDetailsData.getPublishers().containsAll(puList));

                    String[] characters = {"Cloud Strife", "Sephiroth", "Zack Fair", "Aerith " +
                            "Gainsborough", "Vincent Valentine", "Barret Wallace", "Tifa " +
                            "Lockhart", "Red XIII", "Cid Highwind", "Yuffie Kisaragi"};
                    List<String> cList = Arrays.asList(characters);
                    assertTrue(gameDetailsData.getCharacters().containsAll(cList));

                    String[] imageURLs = {"https://static.giantbomb" +
                            ".com/uploads/original/36/369565/2995549-ffvii-twitch-icon.jpg",
                            "https://static.giantbomb" +
                                    ".com/uploads/original/0/201/1535180-epsxe_2010_09_21_14_33_04_86.jpg",
                            "https://static.giantbomb.com/uploads/original/0/201/1535177-epsxe_2010_09_21_14_29_43_76.jpg"
                    };
                    List<String> iList = Arrays.asList(imageURLs);
                    assertTrue(gameDetailsData.getImages().containsAll(iList));
                });
    }
}