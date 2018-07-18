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

package marabillas.loremar.gamehunter.program;

import android.os.Handler;
import android.os.HandlerThread;

import java.util.List;
import java.util.Set;

import marabillas.loremar.gamehunter.apis.BaseAPI;
import marabillas.loremar.gamehunter.apis.BaseAPIFailedQueryException;
import marabillas.loremar.gamehunter.ui.activity.MainActivity;

import static marabillas.loremar.gamehunter.utils.LogUtils.logError;

/**
 * This class will manage the page for searching video games.
 */
public class Searcher implements MainActivity.SearchEventsListener, GameHunterApp
        .ActivityChangeListener {
    private MainActivity activity;
    private BaseAPI api;
    private Query currentValues;
    private HandlerThread thread;
    private Handler handler;

    /**
     * Initializes this class with the selected site's API
     *
     * @param activity the activity that manages the search screen
     * @param api the API of the selected site.
     */
    Searcher(MainActivity activity, BaseAPI api) {
        this.activity = activity;
        this.activity.setSearchEventsListener(this);
        this.api = api;
        GameHunterApp.getInstance().addActivityChangeListener(this);
        currentValues = new Query();
        thread = new HandlerThread("Searcher Thread");
        thread.start();
        handler = new Handler(thread.getLooper());
    }

    @Override
    public void onActivityChange(MainActivity activity) {
        this.activity = activity;
        this.activity.setSearchEventsListener(this);
    }

    @Override
    public void close() {
        new Chooser(activity);
        handler.removeCallbacksAndMessages(null);
        thread.interrupt();
        thread.quit();
        GameHunterApp.getInstance().removeActivityChangeListener(this);
        activity.setSearchEventsListener(null);
    }

    @Override
    public void goForQuery() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    final List<ResultsItem> results = api.query(currentValues);
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            activity.updateResults(results);
                        }
                    });
                } catch (final BaseAPIFailedQueryException e) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            activity.displayError("Query failed!");
                            logError(e.getMessage());
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onKeyWordChange(String keyword) {
        currentValues.setKeyword(keyword);
    }

    @Override
    public void onFieldsChange(Set<Query.Field> fields) {
        currentValues.setFields(fields);
    }

    @Override
    public void onPlatformFilterChange(String platformFilter) {
        currentValues.setPlatformFilter(platformFilter);
    }

    @Override
    public void onGenreFilterChange(String genreFilter) {
        currentValues.setGenreFilter(genreFilter);
    }

    @Override
    public void OnThemeFilterChange(String themeFilter) {
        currentValues.setThemeFilter(themeFilter);
    }

    @Override
    public void onReleaseYearChange(int releaseYear) {
        currentValues.setReleaseYear(releaseYear);
    }

    @Override
    public void onFromYearChange(int fromYear) {
        currentValues.setFromYear(fromYear);
    }

    @Override
    public void onToYeatChange(int toYear) {
        currentValues.setToYear(toYear);
    }

    @Override
    public void onSortChange(String sort) {
        currentValues.setSort(sort);
    }

    @Override
    public void onOrderChange(Query.Order order) {
        currentValues.setOrder(order);
    }

    @Override
    public void onResultsPerPageChange(int resultsPerPage) {
        currentValues.setResultsPerPage(resultsPerPage);
    }

    @Override
    public void onPageNumberChange(int pageNumber) {
        currentValues.setPageNumber(pageNumber);
    }
}
