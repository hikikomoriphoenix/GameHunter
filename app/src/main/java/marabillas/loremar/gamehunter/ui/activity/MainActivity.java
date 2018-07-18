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

package marabillas.loremar.gamehunter.ui.activity;

import android.app.Activity;
import android.os.Bundle;

import java.util.List;
import java.util.Set;

import marabillas.loremar.gamehunter.R;
import marabillas.loremar.gamehunter.program.Chooser;
import marabillas.loremar.gamehunter.program.GameHunterApp;
import marabillas.loremar.gamehunter.program.Query;
import marabillas.loremar.gamehunter.program.ResultsItem;

public class MainActivity extends Activity {
    private ChooseEventsListener chooseEventsListener;
    private SearchEventsListener searchEventsListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_activity_main_choose);

        GameHunterApp.getInstance().setActivity(this);
    }

    public void setChooseEventsListener(ChooseEventsListener chooseEventsListener) {
        this.chooseEventsListener = chooseEventsListener;
    }

    public void setSearchEventsListener(SearchEventsListener searchEventsListener) {
        this.searchEventsListener = searchEventsListener;
    }

    public void displayError(String message) {
    }

    public void updateResults(List<ResultsItem> results) {
    }

    public interface ChooseEventsListener {
        /**
         * This is called when the user has selected a website to search for video games.
         *
         * @param site the selected website.
         */
        void choose(Chooser.Site site);
    }

    public interface SearchEventsListener {
        /**
         * This is called when the user decides to close the search screen
         */
        void close();

        /**
         * This is called when the user wants to query the database.
         */
        void goForQuery();

        /*
           The following methods are called when the user changes one of the options
         */
        void onKeyWordChange(String keyword);

        void onFieldsChange(Set<Query.Field> fields);

        void onPlatformFilterChange(String platformFilter);

        void onGenreFilterChange(String genreFilter);

        void OnThemeFilterChange(String themeFilter);

        void onReleaseYearChange(int releaseYear);

        void onFromYearChange(int fromYear);

        void onToYeatChange(int toYear);

        void onSortChange(String sort);

        void onOrderChange(Query.Order order);

        void onResultsPerPageChange(int resultsPerPage);

        void onPageNumberChange(int pageNumber);
    }
}
