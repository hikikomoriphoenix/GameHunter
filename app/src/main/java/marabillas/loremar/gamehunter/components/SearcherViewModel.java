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

public class SearcherViewModel extends ViewModel {
    private BaseAPI api;

    public MutableLiveData<SearcherEvent> eventBus = new MutableLiveData<>();

    public void setApi(BaseAPI api) {
        this.api = api;
    }

    public void init() {
        if (!api.hasSearch()) {
            Completable.fromRunnable(() -> eventBus.setValue(SearcherEvent.HIDE_SEARCH_ICON))
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .subscribe();
        }

        boolean hasSearchOptions =
                api.hasFilterByPlatform()
                        || api.hasFilterByTheme()
                        || api.hasFilterByGenre()
                        || api.hasFilterByYear()
                        || api.hasFilterByYears()
                        || api.hasSort()
                        || api.hasSortByReversible()
                        || api.hasSearchFilterByPlatform()
                        || api.hasSearchFilterByTheme()
                        || api.hasSearchFilterByGenre()
                        || api.hasSearchFilterByYear()
                        || api.hasSearchFilterByYears()
                        || api.hasSearchSortBy()
                        || api.hasSortByReversible();

        if (!hasSearchOptions) {
            Completable.fromRunnable(() -> eventBus.setValue(SearcherEvent.HIDE_SEARCH_OPTIONS_ICON))
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .subscribe();
        }

        Completable.fromRunnable(() -> eventBus.setValue(SearcherEvent.HIDE_PROGRESS_VIEW))
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }
}
