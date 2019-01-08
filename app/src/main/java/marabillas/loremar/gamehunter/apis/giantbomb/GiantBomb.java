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

package marabillas.loremar.gamehunter.apis.giantbomb;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import marabillas.loremar.gamehunter.BuildConfig;
import marabillas.loremar.gamehunter.apis.BaseAPI;
import marabillas.loremar.gamehunter.apis.BaseAPIFailedQueryException;
import marabillas.loremar.gamehunter.apis.Feature;
import marabillas.loremar.gamehunter.components.Query;
import marabillas.loremar.gamehunter.components.ResultsItem;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static marabillas.loremar.gamehunter.components.Query.Field.DESCRIPTION;
import static marabillas.loremar.gamehunter.components.Query.Field.ID;
import static marabillas.loremar.gamehunter.components.Query.Field.RELEASE_DATE;
import static marabillas.loremar.gamehunter.components.Query.Field.THUMBNAIL;
import static marabillas.loremar.gamehunter.utils.StringUtils.encodeURL;

/**
 *  This class is used for getting access to GiantBomb.com's database using their api.
 */
public class GiantBomb extends BaseAPI {
    private final String KEY = BuildConfig.giantbomb_api_key;
    private Map<String, Integer> platforms;
    private long totalResultsFromLastQuery;

    private GiantBombInterface api;

    public GiantBomb() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.giantbomb.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        api = retrofit.create(GiantBombInterface.class);
    }

    @Override
    protected Set<Feature> configure() {
        return EnumSet.of(Feature.THUMBNAIL, Feature.DESCRIPTION, Feature
                .RELEASE_DATE, Feature.FILTER_BY_PLATFORM, Feature.FILTER_BY_YEAR, Feature
                        .FILTER_BY_YEARS, Feature.SORT_BY_REVERSIBLE, Feature.RESULTS_PER_PAGE, Feature
                        .SEARCH_THUMBNAIL, Feature.SEARCH_DESCRIPTION, Feature.SEARCH_RELEASE_DATE,
                Feature.SEARCH_RESULTS_PER_PAGE);
    }

    @Override
    public Query getDefaultQuery() {
        Query query = new Query();
        Set<Query.Field> fields = EnumSet.of(THUMBNAIL, DESCRIPTION, RELEASE_DATE, ID);

        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        query
                .setFromYear(1950)
                .setToYear(currentYear)
                .setSort("Original release date")
                .setOrder(Query.Order.DESCENDING)
                .setFields(fields);

        return query;
    }

    @Override
    public Observable<Set<String>> getGenreFilters() {
        return null;
    }

    @Override
    public Observable<Set<String>> getPlatformFilters() {
        platforms = new TreeMap<>();
        return getRecursiveObservable(0)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .concatMap(object -> {
                    Set<String> platformFilters = platforms.keySet();
                    return Observable.just(platformFilters);
                });
    }

    private Observable<Object> getRecursiveObservable
            (int page) {
        return api
                .getPlatformFilters(KEY, page * 100)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .concatMap(response -> {
                    ArrayList<GiantBombPlatformFilter> results = response.getResults();
                    boolean morePlatformFiltersToGet = savePlatformFilters(results);
                    if (morePlatformFiltersToGet) {
                        return getRecursiveObservable(page + 1);
                    } else {
                        return Observable.just(new Object());
                    }
                });
    }

    private boolean savePlatformFilters(ArrayList<GiantBombPlatformFilter> results) {
        // If no more platform filters, proceed to finish.
        if (results.size() <= 0) {
            return false;
        }

        // Get each platform filter and add to collection.
        for (GiantBombPlatformFilter result :
                results) {
            String name = result.getName();
            int id = result.getId();

            platforms.put(name, id);
        }

        // If number of returned platform filters is less than 100, then there is
        // no more platform filter to get. Proceed to finish.
        return results.size() >= 100;
    }

    @Override
    public Observable<Set<String>> getSortChoices() {
        Set<String> choices = new GiantBombCollections().getSortChoices().keySet();
        return Observable.just(choices);
    }

    @Override
    public Observable<Set<String>> getThemeFilters() {
        return null;
    }

    @Override
    public long getTotatResultsFromLastQuery() {
        return totalResultsFromLastQuery;
    }

    @Override
    public Observable<List<ResultsItem>> query(Query query) {
        return Observable.fromCallable(() -> prepareQueryParameters(query))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .concatMap(queryMap -> {
                    String keyword = queryMap.get("query");
                    if (keyword != null) {
                        return api.search(queryMap);
                    } else {
                        return api.getGames(queryMap);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .concatMap(response -> {
                    totalResultsFromLastQuery = response.getNumTotalResults();
                    List<ResultsItem> results = getResults(response.getResults(), query.getFields
                            ());
                    return Observable.just(results);
                });
    }

    private Map<String, String> prepareQueryParameters(Query query) throws
            BaseAPIFailedQueryException {
        // Prepare field_list parameter.
        Set<Query.Field> fields = query.getFields();
        StringBuilder fieldSB = new StringBuilder("name");
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
        Map<String, String> queryMap = new HashMap<>();
        if (query.getKeyword() != null) {
            String keyword = query.getKeyword();
            try {
                keyword = encodeURL(keyword);
            } catch (UnsupportedEncodingException e) {
                throw new BaseAPIFailedQueryException(e);
            }

            queryMap.put("api_key", KEY);
            queryMap.put("format", "json");
            queryMap.put("resources", "game");
            queryMap.put("query", keyword);
            queryMap.put("limit", String.valueOf(query.getResultsPerPage()));
            queryMap.put("page", String.valueOf(query.getPageNumber()));
            queryMap.put("field_list", fieldSB.toString());
        } else {
            queryMap.put("api_key", KEY);
            queryMap.put("format", "json");
            queryMap.put("limit", String.valueOf(query.getResultsPerPage()));
            queryMap.put("field_list", fieldSB.toString());

            long pageNumber = query.getPageNumber();
            long limit = query.getResultsPerPage();
            long offset = (pageNumber - 1) * limit;
            queryMap.put("offset", String.valueOf(offset));

            // Prepare values for filter field
            String platformFilter = query.getPlatformFilter();
            int releaseYear = query.getReleaseYear();
            int fromYear = query.getFromYear();
            int toYear = query.getToYear();
            StringBuilder filterSB = new StringBuilder();

            // Append platform
            if (platformFilter != null) {
                int platformId = platforms.get(platformFilter);
                filterSB.append("platforms:").append(platformId);
            }

            // Append release years
            String dateTimeBegin;
            String dateTimeEnd;
            try {
                dateTimeBegin = encodeURL("-01-00 00:00:00");
                dateTimeEnd = encodeURL("-12-31 23:59:59");
            } catch (UnsupportedEncodingException e) {
                throw new BaseAPIFailedQueryException(e);
            }

            if (releaseYear >= 0) {
                if (platformFilter != null) {
                    filterSB.append(",");
                }

                filterSB.append("original_release_date:")
                        .append(releaseYear)
                        .append(dateTimeBegin)
                        .append("|")
                        .append(releaseYear)
                        .append(dateTimeEnd);
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
                    filterSB.append(",");
                }

                filterSB.append("original_release_date:")
                        .append(fromYear)
                        .append(dateTimeBegin)
                        .append("|")
                        .append(toYear)
                        .append(dateTimeEnd);
            }

            // Set filter field to the prepared values if filter field values exist.
            if (platformFilter != null || query.getReleaseYear() >= 0 || query.getFromYear() >= 0
                    || query.getToYear() >= 0) {
                queryMap.put("filter", filterSB.toString());
            }

            // Set sort field
            String sortSelection = query.getSort();
            if (sortSelection != null) {
                sortSelection = new GiantBombCollections().getSortChoices().get(sortSelection);
                String sort;
                Query.Order order = query.getOrder();
                switch (order) {
                    case DESCENDING:
                        sort = sortSelection + ":desc";
                        break;

                    case ASCENDING:
                    default:
                        sort = sortSelection + ":asc";
                        break;
                }
                queryMap.put("sort", sort);
            }
        }

        return queryMap;
    }

    private List<ResultsItem> getResults(ArrayList<GiantBombQueryResultsItem> resultsArray,
                                         Set<Query.Field> fields) {
        List<ResultsItem> results = new ArrayList<>();
        for (int i = 0; i < resultsArray.size(); ++i) {
            GiantBombQueryResultsItem item = resultsArray.get(i);
            ResultsItem resultsItem = new ResultsItem();

            resultsItem.title = item.getTitle();

            if (fields.contains(THUMBNAIL)) {
                GiantBombImageItem imageItem = item.getImage();
                resultsItem.thumbnailURL = imageItem.getThumbnailUrl();
            }

            if (fields.contains(DESCRIPTION)) {
                resultsItem.description = item.getDescription();
            }

            if (fields.contains(Query.Field.RELEASE_DATE)) {
                String originalReleaseDate = item.getOriginalReleaseDate();
                String expectedReleaseYear = item.getExpectedReleaseYear();

                if (originalReleaseDate != null) {
                    SimpleDateFormat f1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                    SimpleDateFormat f2 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    try {
                        Date dt = f1.parse(originalReleaseDate);
                        resultsItem.releaseDate = f2.format(dt);
                    } catch (ParseException e) {
                        e.printStackTrace();
                        resultsItem.releaseDate = originalReleaseDate;
                    }
                } else if (expectedReleaseYear != null) {
                    resultsItem.releaseDate = expectedReleaseYear;
                }
            }

            if (fields.contains(Query.Field.ID)) {
                resultsItem.id = item.getId();
            }

            results.add(resultsItem);
        }

        // If total results was not retrieved, set the value to the number of results in the
        // current page
        if (totalResultsFromLastQuery < 0) {
            totalResultsFromLastQuery = results.size();
        }

        return results;
    }
}
