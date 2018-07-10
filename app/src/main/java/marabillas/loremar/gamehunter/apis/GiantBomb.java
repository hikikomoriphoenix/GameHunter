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

import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import marabillas.loremar.gamehunter.GameHunterApp;
import marabillas.loremar.gamehunter.R;
import marabillas.loremar.gamehunter.framework.Query;
import marabillas.loremar.gamehunter.framework.ResultsItem;
import marabillas.loremar.gamehunter.parsers.FailedToGetFieldException;
import marabillas.loremar.gamehunter.parsers.FailedToParseException;
import marabillas.loremar.gamehunter.parsers.json.JSON;
import marabillas.loremar.gamehunter.parsers.json.JSONParser;
import marabillas.loremar.gamehunter.parsers.json.JSON_Array;

/**
 *  This class is used for getting access to GiantBomb.com's database using their api.
 */
public class GiantBomb extends BaseAPI {
    private final String KEY = GameHunterApp.getInstance().getResources().getString(R.string
            .giantbomb_api_key);
    private Map<String, Integer> platforms;

    @Override
    protected Set<Feature> configure() {
        return EnumSet.of(Feature.SEARCH, Feature.THUMBNAIL, Feature.DESCRIPTION, Feature
                .RELEASE_DATE, Feature.FILTER_BY_PLATFORM, Feature.FILTER_BY_YEAR, Feature
                .FILTER_BY_YEARS, Feature.SORT_BY_REVERSIBLE);
    }

    @Override
    public Set<String> getGenreFilters() {
        return null;
    }

    @Override
    public Set<String> getPlatformFilters() throws BaseAPIGetterFailedToGetException {
        platforms = new TreeMap<>();
        Set<String> platformFilters = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        int page = 0;
        while (true) {
            String url = "https://www.giantbomb.com/api/platforms/?api_key=" + KEY +
                    "&format=json&field_list=name,id&offset=" + (page * 100);
            JSON json;
            try {
                json = new JSONParser().parse(url);
            } catch (FailedToParseException e) {
                throw new BaseAPIGetterFailedToGetException(e);
            }
            JSON_Array results;
            try {
                results = json.getArray("results");
                if (results.getCount() <= 0) {
                    break;
                }
                for (int i = 0; i < results.getCount(); ++i) {
                    String name;
                    int id;
                    try {
                        JSON platform = results.getObject(i);
                        name = platform.getString("name");
                        id = platform.getInt("id");
                    } catch (FailedToGetFieldException e) {
                        continue;
                    }
                    platforms.put(name, id);
                    platformFilters.add(name);
                }
                if (results.getCount() < 100) {
                    break;
                }
            } catch (FailedToGetFieldException e) {
                break;
            }
            ++page;
        }
        return platformFilters;
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
}
