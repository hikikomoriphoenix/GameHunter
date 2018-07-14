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

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import marabillas.loremar.gamehunter.BuildConfig;
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
    private final String KEY = BuildConfig.giantbomb_api_key;
    private Map<String, Integer> platforms;

    @Override
    protected Set<Feature> configure() {
        return EnumSet.of(Feature.THUMBNAIL, Feature.DESCRIPTION, Feature
                .RELEASE_DATE, Feature.FILTER_BY_PLATFORM, Feature.FILTER_BY_YEAR, Feature
                        .FILTER_BY_YEARS, Feature.SORT_BY_REVERSIBLE, Feature.RESULTS_PER_PAGE, Feature
                        .SEARCH_THUMBNAIL, Feature.SEARCH_DESCRIPTION, Feature.SEARCH_RELEASE_DATE,
                Feature.SEARCH_RESULTS_PER_PAGE);
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
        Set<String> choices = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        Collections.addAll(choices, GameHunterApp.getInstance().getResources().getStringArray(R
                .array.apis_giantbomb_sort_choices));
        return choices;
    }

    @Override
    public Set<String> getThemeFilters() {
        return null;
    }

    @Override
    public List<ResultsItem> query(Query query) throws BaseAPIFailedQueryException {
        Set<Query.Field> fields = query.getFields();
        StringBuilder fieldSB = new StringBuilder("&field_list=name");
        if (fields != null) {
            for (Query.Field field :
                    fields) {
                fieldSB.append(",");
                switch (field) {
                    case THUMBNAIL:
                        fieldSB.append("image");
                        break;
                    case DESCRIPTION:
                        fieldSB.append("deck");
                        break;
                    case RELEASE_DATE:
                        fieldSB.append("original_release_date")
                                .append(",expected_release_year");
                        break;
                    case ID:
                        fieldSB.append("guid");
                        break;
                }
            }
        }

        /*
         * There are two ways to get a list of games from GiantBomb's database using their API.
         * One is to use the 'search' resource and pass a keyword in the 'query' parameter. This
         * will return a response containing games relevant to this keyword. The other one is to
         * use the 'game' resource. This provides the ability to use filters or to sort results
         * in ascending or descending order based on a selected field.
         */
        String url;
        if (query.getKeyword() != null) {
            url = "https://www.giantbomb.com/api/search/?api_key=" + KEY +
                    "&format=json" +
                    "&resources=game" +
                    "&query=" + query.getKeyword() +
                    "&limit=" + query.getResultsPerPage() +
                    "&page=" + query.getPageNumber() +
                    fieldSB;
        } else {
            url = "https://www.giantbomb.com/api/games/?api_key=" + KEY +
                    "&format=json" +
                    "&limit=" + query.getResultsPerPage() +
                    "&page=" + query.getPageNumber() +
                    fieldSB;

            StringBuilder sb = new StringBuilder(url);

            // Check if query needs the filter parameter
            String platformFilter = query.getPlatformFilter();
            int releaseYear = query.getReleaseYear();
            int fromYear = query.getFromYear();
            int toYear = query.getToYear();
            if (platformFilter != null || releaseYear >= 0 || fromYear >= 0 || toYear >= 0) {
                sb.append("&filter=");
            }

            // Append platform
            if (platformFilter != null) {
                int platformId = platforms.get(platformFilter);
                sb.append("platforms:").append(platformId);
            }

            // Append release years
            if (releaseYear >= 0) {
                if (platformFilter != null) {
                    sb.append(",");
                }
                sb.append("original_release_date:")
                        .append(releaseYear)
                        .append("-01-01 00:00:00")
                        .append("|")
                        .append(releaseYear + 1)
                        .append("-01-01 00:00:00");
            } else if (fromYear >= 0 || toYear >= 0) {
                if (fromYear < 0) {
                    fromYear = 0;
                }
                if (toYear < 0) {
                    toYear = 3000;
                }
                if (toYear <= fromYear) {
                    toYear = fromYear;
                }
                if (platformFilter != null) {
                    sb.append(",");
                }
                sb.append("original_release_date:")
                        .append(fromYear)
                        .append("-01-01 00:00:00")
                        .append("|")
                        .append(toYear + 1)
                        .append("-01-01 00:00:00");
            }

            // Append sort
            String sortSelection = query.getSort();
            if (sortSelection != null) {
                String sort;
                Query.Order order = query.getOrder();
                if (order == Query.Order.ASCENDING) {
                    sort = "&sort=" + sortSelection + ":asc";
                } else if (order == Query.Order.DESCENDING) {
                    sort = "&sort=" + sortSelection + ":desc";
                } else {
                    sort = "&sort=" + sortSelection + ":asc";
                }
                sb.append(sort);
            }

            // Complete query url
            url = sb.toString();
        }
        JSON data = getData(url);
        return getResults(data, fields);
    }

    private JSON getData(String url) throws BaseAPIFailedQueryException {
        JSON json;
        try {
            json = new JSONParser().parse(url);
        } catch (FailedToParseException e) {
            throw new BaseAPIFailedQueryException(e);
        }
        return json;
    }

    private List<ResultsItem> getResults(JSON data, Set<Query.Field> fields) throws
            BaseAPIFailedQueryException {
        List<ResultsItem> results = new ArrayList<>();
        JSON_Array resultsArray;
        try {
            resultsArray = data.getArray("results");
        } catch (FailedToGetFieldException e) {
            throw new BaseAPIFailedQueryException(e);
        }
        for (int i = 0; i < resultsArray.getCount(); ++i) {
            JSON result;
            try {
                result = resultsArray.getObject(i);
            } catch (FailedToGetFieldException e) {
                continue;
            }

            ResultsItem resultsItem = new ResultsItem();

            resultsItem.title = getTitle(result);

            if (fields.contains(Query.Field.THUMBNAIL)) {
                resultsItem.thumbnailURL = getThumbnailUrl(result);
            }

            if (fields.contains(Query.Field.DESCRIPTION)) {
                resultsItem.description = getDescription(result);
            }

            if (fields.contains(Query.Field.RELEASE_DATE)) {
                resultsItem.releaseDate = getReleaseDate(result);
            }

            if (fields.contains(Query.Field.ID)) {
                resultsItem.id = getId(result);
            }

            results.add(resultsItem);
        }

        return results;
    }

    private String getTitle(JSON result) {
        try {
            return result.getString("name");
        } catch (FailedToGetFieldException e) {
            return null;
        }
    }

    private String getThumbnailUrl(JSON result) {
        try {
            return result.getObject("image").getString("thumb_url");
        } catch (FailedToGetFieldException e) {
            return null;
        }
    }

    private String getDescription(JSON result) {
        try {
            return result.getString("deck");
        } catch (FailedToGetFieldException e) {
            return null;
        }
    }

    private String getReleaseDate(JSON result) {
        String originalReleaseDate;
        String expectedReleaseYear;
        try {
            expectedReleaseYear = result.getString("expected_release_year");
        } catch (FailedToGetFieldException e) {
            expectedReleaseYear = null;
        }
        if (expectedReleaseYear == null) {
            try {
                originalReleaseDate = result.getString("original_release_date");
            } catch (FailedToGetFieldException e) {
                originalReleaseDate = null;
            }
        } else {
            originalReleaseDate = null;
        }
        if (originalReleaseDate != null) {
            return originalReleaseDate;
        } else if (expectedReleaseYear != null) {
            return expectedReleaseYear;
        } else {
            return null;
        }
    }

    private String getId(JSON result) {
        try {
            return result.getString("guid");
        } catch (FailedToGetFieldException e) {
            return null;
        }
    }
}
