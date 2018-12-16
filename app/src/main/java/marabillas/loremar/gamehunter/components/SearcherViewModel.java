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

package marabillas.loremar.gamehunter.components;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import marabillas.loremar.gamehunter.apis.BaseAPI;

import static android.view.View.GONE;
import static marabillas.loremar.gamehunter.components.SearcherEvent.HIDE_PROGRESS_VIEW;
import static marabillas.loremar.gamehunter.components.SearcherEvent.HIDE_SEARCH_ICON;
import static marabillas.loremar.gamehunter.components.SearcherEvent.HIDE_SEARCH_OPTIONS_ICON;

public class SearcherViewModel extends ViewModel {
    private BaseAPI api;

    public MutableLiveData<SearcherEvent> eventBus = new MutableLiveData<>();

    // Search options visibilities
    public MutableLiveData<Integer> platformVisible = new MutableLiveData<>();
    public MutableLiveData<Integer> themeVisible = new MutableLiveData<>();
    public MutableLiveData<Integer> genreVisible = new MutableLiveData<>();
    public MutableLiveData<Integer> searchVisible = new MutableLiveData<>();
    public MutableLiveData<Integer> toReleaseYearVisible = new MutableLiveData<>();
    public MutableLiveData<Integer> releaseYearVisible = new MutableLiveData<>();
    public MutableLiveData<Integer> sortByVisible = new MutableLiveData<>();
    public MutableLiveData<Integer> orderByVisible = new MutableLiveData<>();

    public void setApi(BaseAPI api) {
        this.api = api;
    }

    private void postEventToMainThread(SearcherEvent event) {
        Completable.fromRunnable(() -> eventBus.setValue(event))
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public void init() {
        selectAccessibleTools();
        selectAccessibleSearchOptions();

        // End setup by hiding progress view.
        postEventToMainThread(HIDE_PROGRESS_VIEW);
    }

    private void selectAccessibleTools() {
        if (!api.hasSearch()) {
            postEventToMainThread(HIDE_SEARCH_ICON);
        }

        boolean hasSearchOptions =
                api.hasFilterByPlatform()
                        || api.hasFilterByTheme()
                        || api.hasFilterByGenre()
                        || api.hasFilterByYear()
                        || api.hasFilterByYears()
                        || api.hasSortBy()
                        || api.hasSortByReversible()
                        || api.hasSearchAdvanced();

        if (!hasSearchOptions) {
            postEventToMainThread(HIDE_SEARCH_OPTIONS_ICON);
        }
    }

    private void selectAccessibleSearchOptions() {
        if (!api.hasFilterByPlatform() && !api.hasSearchFilterByPlatform()) {
            platformVisible.postValue(GONE);
        }

        if (!api.hasFilterByTheme() && !api.hasSearchFilterByTheme()) {
            themeVisible.postValue(GONE);
        }

        if (!api.hasFilterByGenre() && !api.hasSearchFilterByGenre()) {
            genreVisible.postValue(GONE);
        }

        boolean hasReleaseYears = api.hasFilterByYears() || api.hasSearchFilterByYears();
        boolean hasReleaseYearExact = api.hasFilterByYear() || api.hasSearchFilterByYear();

        if (!hasReleaseYears) {
            toReleaseYearVisible.postValue(GONE);
            if (hasReleaseYearExact) {
                releaseYearVisible.postValue(GONE);
            }
        }

        if (!api.hasSortByReversible() && !api.hasSearchSortByReversible()) {
            orderByVisible.postValue(GONE);
            if (!api.hasSortBy() && !api.hasSearchSortBy()) {
                sortByVisible.postValue(GONE);
            }
        }

        if (!api.hasSearchAdvanced()) {
            searchVisible.postValue(GONE);
        }
    }
}
