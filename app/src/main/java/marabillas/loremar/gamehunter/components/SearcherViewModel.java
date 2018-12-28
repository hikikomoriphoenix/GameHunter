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

import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import marabillas.loremar.gamehunter.apis.BaseAPI;
import marabillas.loremar.gamehunter.ui.components.GoToPageDialog;
import marabillas.loremar.gamehunter.ui.components.SearchBox;

import static android.view.View.GONE;
import static marabillas.loremar.gamehunter.components.SearcherEvent.CLOSE_SEARCH_OPTIONS;
import static marabillas.loremar.gamehunter.components.SearcherEvent.HIDE_PROGRESS_VIEW;
import static marabillas.loremar.gamehunter.components.SearcherEvent.HIDE_SEARCH_ICON;
import static marabillas.loremar.gamehunter.components.SearcherEvent.HIDE_SEARCH_OPTIONS_ICON;
import static marabillas.loremar.gamehunter.components.SearcherEvent.SET_DEFAULT_SORT_BY_SELECTION;
import static marabillas.loremar.gamehunter.components.SearcherEvent.SHOW_GO_TO_PAGE_DIALOG;
import static marabillas.loremar.gamehunter.utils.LogUtils.log;

public class SearcherViewModel extends ViewModel implements SearchBox.OnSearchBoxActionListener,
        GoToPageDialog.OnGoToPageDialogActionListener {
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

    public MutableLiveData<Query> query = new MutableLiveData<>();
    public MutableLiveData<Integer> fromYear = new MutableLiveData<>();
    public MutableLiveData<Integer> toYear = new MutableLiveData<>();
    public MutableLiveData<Integer> selectedOrderPos = new MutableLiveData<>();
    public MutableLiveData<List<ResultsItem>> results = new MutableLiveData<>();
    public MutableLiveData<String> pageStatus = new MutableLiveData<>();

    private Query lastQuery;

    public void setApi(BaseAPI api) {
        this.api = api;
    }

    private void postEventToMainThread(SearcherEvent event) {
        Completable.fromRunnable(() -> eventBus.setValue(event))
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public void init() {
        query.postValue(new Query());
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

            // Show default list of games
            Completable.fromRunnable(() -> {
                query.setValue(api.getDefaultQuery());
                setDefaultSearchOptionsValues();
            })
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .blockingAwait();
            if (query.getValue() != null) {
                logQuery(query.getValue());
            }
            api.query(query.getValue())
                    .blockingSubscribe(results -> {
                        SearcherViewModel.this.results.postValue(results);

                        Query q = query.getValue();
                        int page = Objects.requireNonNull(q).getPageNumber();
                        int total = (int) api.getTotalPages(q.getResultsPerPage());
                        pageStatus.postValue(page + " / " + total);
                    });
            lastQuery = query.getValue();
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

        if (hasNoReleaseYearsRange()) {
            toReleaseYearVisible.postValue(GONE);
            if (hasReleaseYearExact()) {
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

    public void onOrderSelected(String order) {
        switch (order) {
            case "Ascending":
                Objects.requireNonNull(query.getValue()).setOrder(Query.Order.ASCENDING);
                break;
            case "Descending":
                Objects.requireNonNull(query.getValue()).setOrder(Query.Order.DESCENDING);
                break;
        }
    }

    public void onApplyButtonClick() {
        validateQuery();

        if (query.getValue() != null) {
            logQuery(query.getValue());
        }

        // TODO Set fields as set by the user and that are available for the specific api.
        Set<Query.Field> fields = EnumSet.of(Query.Field.THUMBNAIL, Query.Field.DESCRIPTION, Query
                .Field.RELEASE_DATE, Query.Field.ID);
        query.getValue().setFields(fields);
        disposable = api.query(query.getValue())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(results -> {
                    eventBus.setValue(CLOSE_SEARCH_OPTIONS);
                    SearcherViewModel.this.results.setValue(results);
                    updatePageStatus();
                    disposable.dispose();
                    disposable = null;
                });
        lastQuery = query.getValue();
    }

    private void validateQuery() {
        String keyword = query.getValue().getKeyword();
        if (keyword != null && keyword.equals("")) {
            query.getValue().setKeyword(null);
        }

        String platformFilter = query.getValue().getPlatformFilter();
        if (noFilter(platformFilters.getValue(), platformFilter)) {
            query.getValue().setPlatformFilter(null);
        }

        String themeFilter = query.getValue().getThemeFilter();
        if (noFilter(themeFilters.getValue(), themeFilter)) {
            query.getValue().setThemeFilter(null);
        }

        String genreFilter = query.getValue().getGenreFilter();
        if (noFilter(genreFilters.getValue(), genreFilter)) {
            query.getValue().setGenreFilter(null);
        }

        if (hasNoReleaseYearsRange() && hasReleaseYearExact()) {
            query.getValue().setReleaseYear(fromYear.getValue());
        } else {
            query.getValue().setFromYear(fromYear.getValue());
            query.getValue().setToYear(toYear.getValue());
        }
    }

    private boolean noFilter(Set<String> filters, String filter) {
        if (filters != null && filters.size() > 0 && filter != null) {
            return !filters.contains(filter);
        } else {
            return true;
        }
    }

    public void onFromYearValueChange(int newValue) {
        if (hasNoReleaseYearsRange() && hasReleaseYearExact()) {
            Objects.requireNonNull(query.getValue()).setReleaseYear(newValue);
            return;
        }

        if (toYear.getValue() == null || toYear.getValue() < newValue) {
            toYear.setValue(newValue);
            Objects.requireNonNull(query.getValue()).setToYear(newValue);
        }

        query.getValue().setFromYear(newValue);
    }

    public void onToYearValueChange(int newValue) {
        if (fromYear.getValue() == null || fromYear.getValue() > newValue) {
            fromYear.setValue(newValue);
            Objects.requireNonNull(query.getValue()).setFromYear(newValue);
        }

        query.getValue().setToYear(newValue);
    }

    private boolean hasNoReleaseYearsRange() {
        return !api.hasFilterByYears() && !api.hasSearchFilterByYears();
    }

    private boolean hasReleaseYearExact() {
        return api.hasFilterByYear() || api.hasSearchFilterByYear();
    }

    @Override
    public void onSearchBoxAction(String keyword) {
        Query query = new Query();
        query.setKeyword(keyword);
        log("search:" + keyword);

        // TODO Set fields as set by the user and that are available for the specific api.
        Set<Query.Field> fields = EnumSet.of(Query.Field.THUMBNAIL, Query.Field.DESCRIPTION, Query
                .Field.RELEASE_DATE, Query.Field.ID);
        query.setFields(fields);
        performQuery(query);

        lastQuery = query;
    }

    public void goToFirstPage() {
        if (lastQuery != null) {
            lastQuery.setPageNumber(1);
            performQuery(lastQuery);
        }
    }

    public void goToPreviousPage() {
        if (lastQuery != null) {
            int currentPage = lastQuery.getPageNumber();
            if (currentPage <= 1) {
                return;
            }

            lastQuery.setPageNumber(currentPage - 1);
            performQuery(lastQuery);
        }
    }

    public void goToNextPage() {
        if (lastQuery != null) {
            int currentPage = lastQuery.getPageNumber();
            int lastPage = (int) api.getTotalPages(lastQuery.getResultsPerPage());
            if (currentPage >= lastPage) {
                return;
            }

            lastQuery.setPageNumber(currentPage + 1);
            performQuery(lastQuery);
        }
    }

    public void goToLastPage() {
        if (lastQuery != null) {
            int lastPage = (int) api.getTotalPages(lastQuery.getResultsPerPage());
            lastQuery.setPageNumber(lastPage);
            performQuery(lastQuery);
        }
    }

    private void goToPage(int targetPage) {
        if (lastQuery != null) {
            lastQuery.setPageNumber(targetPage);
            performQuery(lastQuery);
        }
    }

    public void onPageStatusClick() {
        if (lastQuery != null) {
            SearcherEvent ev = SHOW_GO_TO_PAGE_DIALOG;
            int rpp = lastQuery.getResultsPerPage();
            ev.putExtra("total_pages", api.getTotalPages(rpp));
            postEventToMainThread(ev);
        }
    }

    private void performQuery(Query query) {
        logQuery(query);

        disposable = api.query(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(results -> {
                    SearcherViewModel.this.results.setValue(results);
                    updatePageStatus();
                    disposable.dispose();
                    disposable = null;
                });
    }

    private void updatePageStatus() {
        int page = lastQuery.getPageNumber();
        int total = (int) api.getTotalPages(lastQuery.getResultsPerPage());
        pageStatus.setValue(page + " / " + total);
    }

    @Override
    public void onGoToPageDialogAction(long pageNumber) {
        goToPage((int) pageNumber);
    }

    private void setDefaultSearchOptionsValues() {
        if (query.getValue() != null) {
            if (hasNoReleaseYearsRange() && hasReleaseYearExact()) {
                int ry = query.getValue().getReleaseYear();
                fromYear.setValue(ry);
            } else if (!hasNoReleaseYearsRange()) {
                int fy = query.getValue().getFromYear();
                int ty = query.getValue().getToYear();
                fromYear.setValue(fy);
                toYear.setValue(ty);
            }

            // Set default selection for sort by spinner.
            if (api.hasSortBy() && query.getValue().getSort() != null) {
                SearcherEvent ev = SET_DEFAULT_SORT_BY_SELECTION;
                String defS = query.getValue().getSort();
                ev.putExtra("default_selection", defS);
                postEventToMainThread(ev);
            }

            if (api.hasSortByReversible() && query.getValue().getOrder() != null) {
                Query.Order o = query.getValue().getOrder();
                switch (o) {
                    case DESCENDING:
                        selectedOrderPos.setValue(0);
                        break;
                    case ASCENDING:
                        selectedOrderPos.setValue(1);
                        break;
                }
            }
        }
    }

    private void logQuery(Query query) {
        log("Query:\nkeyword=" + query.getKeyword() +
                "\nplatform=" + query.getPlatformFilter() +
                "\ntheme=" + query.getThemeFilter() +
                "\ngenre=" + query.getGenreFilter() +
                "\nreleaseYear=" + query.getReleaseYear() +
                "\nfromYear=" + query.getFromYear() +
                "\ntoYear=" + query.getToYear() +
                "\nsort=" + query.getSort() +
                "\norder=" + query.getOrder().toString()
        );
    }
}
