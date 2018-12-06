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
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import marabillas.loremar.gamehunter.BuildConfig;
import marabillas.loremar.gamehunter.apis.APICallback;
import marabillas.loremar.gamehunter.apis.BaseAPI;
import marabillas.loremar.gamehunter.apis.BaseAPIFailedQueryException;
import marabillas.loremar.gamehunter.apis.Feature;
import marabillas.loremar.gamehunter.parsers.FailedToGetFieldException;
import marabillas.loremar.gamehunter.parsers.FailedToParseException;
import marabillas.loremar.gamehunter.parsers.json.JSON;
import marabillas.loremar.gamehunter.parsers.json.JSONParser;
import marabillas.loremar.gamehunter.parsers.json.JSON_Array;
import marabillas.loremar.gamehunter.program.Query;
import marabillas.loremar.gamehunter.program.ResultsItem;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static marabillas.loremar.gamehunter.utils.LogUtils.logError;
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
    public Set<String> getGenreFilters() {
        return null;
    }

    @Override
    public void getPlatformFilters(APICallback callback) {
        platforms = new TreeMap<>();
        api
                .getPlatformFilters(KEY, 0)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Observer<GiantBombResponse<GiantBombPlatformFilter>>() {
                    private Disposable disposable;
                    private int page = 0;
                    Set<String> platformFilters = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);

                    @Override
                    public void onSubscribe(Disposable d) {
                        if (disposable != null && !disposable.isDisposed()) {
                            disposable.dispose();
                        }
                        disposable = d;
                        // TODO Add disposable to be disposed later
                    }

                    @Override
                    public void onNext(GiantBombResponse<GiantBombPlatformFilter>
                                               giantBombPlatformFilterGiantBombResponse) {
                        ArrayList<GiantBombPlatformFilter> results =
                                giantBombPlatformFilterGiantBombResponse.getResults();

                        boolean morePlatformFiltersToGet = savePlatformFilters(results,
                                platformFilters);
                        if (!morePlatformFiltersToGet) {
                            callback.onPlatformFiltersObtained(platformFilters);
                            disposable.dispose();
                            return;
                        }

                        // Get platform filters for next page.
                        ++page;
                        api
                                .getPlatformFilters(KEY, page * 100)
                                .subscribeOn(Schedulers.io())
                                .observeOn(Schedulers.io())
                                .subscribe(this);
                    }

                    @Override
                    public void onError(Throwable e) {
                        disposable.dispose();
                    }

                    @Override
                    public void onComplete() {
                        disposable.dispose();
                    }
                });
    }

    private boolean savePlatformFilters(ArrayList<GiantBombPlatformFilter> results, Set<String>
            platformFilters) {
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
            platformFilters.add(name);
        }

        // If number of returned platform filters is less than 100, then there is
        // no more platform filter to get. Proceed to finish.
        return results.size() >= 100;
    }

    @Override
    public Set<String> getSortChoices() {
        return new GiantBombCollections().getSortChoices().keySet();
    }

    @Override
    public Set<String> getThemeFilters() {
        return null;
    }

    @Override
    public long getTotatResultsFromLastQuery() {
        return totalResultsFromLastQuery;
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
            String keyword = query.getKeyword();
            try {
                keyword = encodeURL(keyword);
            } catch (UnsupportedEncodingException e) {
                throw new BaseAPIFailedQueryException(e);
            }
            url = "https://www.giantbomb.com/api/search/?api_key=" + KEY +
                    "&format=json" +
                    "&resources=game" +
                    "&query=" + keyword +
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
            String dateTimeBegin;
            String dateTimeEnd;
            try {
                dateTimeBegin = encodeURL("-01-01 00:00:00");
                dateTimeEnd = encodeURL("-12-31 23:59:59");
            } catch (UnsupportedEncodingException e) {
                throw new BaseAPIFailedQueryException(e);
            }

            if (releaseYear >= 0) {
                if (platformFilter != null) {
                    sb.append(",");
                }

                sb.append("original_release_date:")
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
                    sb.append(",");
                }
                sb.append("original_release_date:")
                        .append(fromYear)
                        .append(dateTimeBegin)
                        .append("|")
                        .append(toYear)
                        .append(dateTimeEnd);
            }

            // Append sort
            String sortSelection = query.getSort();
            if (sortSelection != null) {
                sortSelection = new GiantBombCollections().getSortChoices().get(sortSelection);
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

        try {
            totalResultsFromLastQuery = data.getLong("number_of_total_results");
        } catch (FailedToGetFieldException e) {
            totalResultsFromLastQuery = -1;
            logError("Can't retrieve number of total results.\n" + e.getMessage());
        }

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

        // If total results was not retrieved, set the value to the number of results in the
        // current page
        if (totalResultsFromLastQuery < 0) {
            totalResultsFromLastQuery = results.size();
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
