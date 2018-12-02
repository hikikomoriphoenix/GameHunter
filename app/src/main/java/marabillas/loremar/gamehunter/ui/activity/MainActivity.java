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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;
import java.util.Set;

import marabillas.loremar.gamehunter.R;
import marabillas.loremar.gamehunter.program.GameHunterApp;
import marabillas.loremar.gamehunter.program.Query;
import marabillas.loremar.gamehunter.program.ResultsItem;
import marabillas.loremar.gamehunter.ui.adapter.WebsiteSelectionAdapter;

import static marabillas.loremar.gamehunter.utils.MeasurementUtils.convertPixelsToDp;
import static marabillas.loremar.gamehunter.utils.MeasurementUtils.getScreenWidthInPixels;

public class MainActivity extends Activity implements View.OnClickListener {
    private ChooseEventsListener chooseEventsListener;
    private SearchEventsListener searchEventsListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_choose);

        GameHunterApp.getInstance().setActivity(this);
    }

    public void setChooseEventsListener(ChooseEventsListener chooseEventsListener) {
        this.chooseEventsListener = chooseEventsListener;
    }

    public void setSearchEventsListener(SearchEventsListener searchEventsListener) {
        this.searchEventsListener = searchEventsListener;
    }

    /**
     * Sets up the layout of the screen for website selection. Should be called in MainActivity's
     * onCreate along with setChooseEventsListener after a configuration change while the user is
     * selecting website.
     */
    public void setScreenToWebsiteSelectionScreen() {
        setContentView(R.layout.activity_main_choose);

        // Set up the grid layout for selectable websites
        RecyclerView websitesView = findViewById(R.id.activity_main_websites_view);

        // The following code enforces maximum width for each element
        int screenWidthInPixels = getScreenWidthInPixels();
        float screenWidthInDp = convertPixelsToDp(screenWidthInPixels, this);
        int spanCount = (int) (screenWidthInDp / 180);
        if (screenWidthInDp % 180 > 0) {
            ++spanCount;
        }

        websitesView.setLayoutManager(new GridLayoutManager(this, spanCount));
        websitesView.setAdapter(new WebsiteSelectionAdapter(this));
    }

    public void displayError(String message) {
    }

    public void updateResults(List<ResultsItem> results) {
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id >= 0) {
            switch (id) {
                // If the user clicks a thumbnail, then a website has been chosen. The Chooser
                // should handle this choose event based on the tag set on the ImageView,
                // corresponding to the selected website.
                case R.id.adapter_website_selection_item_view_logo:
                    chooseEventsListener.choose((String) v.getTag());
                    break;
            }
        }
    }

    public interface ChooseEventsListener {
        /**
         * This is called when the user has selected a website to search for video games.
         *
         * @param website the selected website
         */
        void choose(String website);
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
