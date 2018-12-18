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

import java.util.Set;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import marabillas.loremar.gamehunter.apis.BaseAPI;

import static android.view.View.GONE;
import static marabillas.loremar.gamehunter.components.SearcherEvent.HIDE_PROGRESS_VIEW;
import static marabillas.loremar.gamehunter.components.SearcherEvent.HIDE_SEARCH_ICON;
import static marabillas.loremar.gamehunter.components.SearcherEvent.HIDE_SEARCH_OPTIONS_ICON;

public class SearcherViewModel extends ViewModel {
    private BaseAPI api;

    public MutableLiveData<SearcherEvent> eventBus = new MutableLiveData<>();

    private Disposable disposable;

    // Search options visibilities
    public MutableLiveData<Integer> platformVisible = new MutableLiveData<>();
    public MutableLiveData<Integer> themeVisible = new MutableLiveData<>();
    public MutableLiveData<Integer> genreVisible = new MutableLiveData<>();
    public MutableLiveData<Integer> searchVisible = new MutableLiveData<>();
    public MutableLiveData<Integer> toReleaseYearVisible = new MutableLiveData<>();
    public MutableLiveData<Integer> releaseYearVisible = new MutableLiveData<>();
    public MutableLiveData<Integer> sortByVisible = new MutableLiveData<>();
    public MutableLiveData<Integer> orderByVisible = new MutableLiveData<>();

    // Search options choices
    public MutableLiveData<Set<String>> platformFilters = new MutableLiveData<>();
    public MutableLiveData<Set<String>> themeFilters = new MutableLiveData<>();
    public MutableLiveData<Set<String>> genreFilters = new MutableLiveData<>();
    public MutableLiveData<Set<String>> sortChoices = new MutableLiveData<>();

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

        disposable = Completable.fromRunnable(() -> {
            if (api.hasFilterByPlatform() || api.hasSearchFilterByPlatform()) {
                api.getPlatformFilters()
                        .blockingSubscribe(filters -> platformFilters.postValue(filters));
            }

            if (api.hasFilterByTheme() || api.hasSearchFilterByTheme()) {
                api.getThemeFilters()
                        .blockingSubscribe(filters -> themeFilters.postValue(filters));
            }

            if (api.hasFilterByGenre() || api.hasSearchFilterByGenre()) {
                api.getGenreFilters()
                        .blockingSubscribe(filters -> genreFilters.postValue(filters));
            }

            if (api.hasSortBy()) {
                api.getSortChoices()
                        .blockingSubscribe(choices -> sortChoices.postValue(choices));
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    // End setup by hiding progress view.
                    postEventToMainThread(HIDE_PROGRESS_VIEW);
                    disposable.dispose();
                    disposable = null;
                });
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
        } else {
            postEventToMainThread(SearcherEvent.SETUP_ORDER_BY);
        }

        if (!api.hasSearchAdvanced()) {
            searchVisible.postValue(GONE);
        }
    }

    public void onPlatformFilterDropDownClick() {
        postEventToMainThread(SearcherEvent.SHOW_PLATFORM_FILTERS);
    }

    public void onThemeFilterDropDownClick() {
        postEventToMainThread(SearcherEvent.SHOW_THEME_FILTERS);
    }

    public void onGenreFilterDropDownClick() {
        postEventToMainThread(SearcherEvent.SHOW_GENRE_FILTERS);
    }

    public void onSortByDropDownClick() {
        postEventToMainThread(SearcherEvent.SHOW_SORT_CHOICES);
    }

    public void onOrderByDropDownClick() {
        postEventToMainThread(SearcherEvent.SHOW_ORDER_CHOICES);
    }
}
