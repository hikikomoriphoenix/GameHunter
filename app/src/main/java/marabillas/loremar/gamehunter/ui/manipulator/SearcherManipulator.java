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

package marabillas.loremar.gamehunter.ui.manipulator;

import android.view.MenuItem;

import marabillas.loremar.gamehunter.R;
import marabillas.loremar.gamehunter.components.SearcherEvent;
import marabillas.loremar.gamehunter.ui.activity.SearcherActivity;

public class SearcherManipulator {
    private SearcherActivity activity;

    public SearcherManipulator(SearcherActivity activity) {
        this.activity = activity;
    }

    public void handleEvent(SearcherEvent event) {
        switch (event) {
            case HIDE_SEARCH_ICON:
                MenuItem searchTool = activity.getMenu().findItem(R.id.searcher_menu_search);
                searchTool.setVisible(false);
                break;
            case HIDE_SEARCH_OPTIONS_ICON:
                MenuItem searchOptionsTool = activity.getMenu().findItem(R.id.searcher_menu_searchoptions);
                searchOptionsTool.setVisible(false);
                break;
            case HIDE_PROGRESS_VIEW:
                activity.getProgressView().dismiss();
                break;
        }
    }
}
