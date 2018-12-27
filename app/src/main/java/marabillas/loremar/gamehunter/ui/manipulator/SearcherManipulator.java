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

import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.PopupMenu;

import java.util.Objects;
import java.util.Set;

import marabillas.loremar.gamehunter.R;
import marabillas.loremar.gamehunter.components.SearcherEvent;
import marabillas.loremar.gamehunter.ui.activity.SearcherActivity;
import marabillas.loremar.gamehunter.ui.components.GoToPageDialog;

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

            case SETUP_ORDER_BY:
                String[] order = {"Descending", "Ascending"};
                ArrayAdapter<String> adapter = new ArrayAdapter<>(activity, android.R.layout
                        .simple_list_item_1, order);
                activity.getBinding().searcherOptions.activitySearcherOptionsOrderDropdown
                        .setAdapter(adapter);
                break;

            case SHOW_PLATFORM_FILTERS:
                AutoCompleteTextView v1 = activity.getBinding().searcherOptions
                        .activitySearcherOptionsPlatformTextbox;
                Set<String> f1 = activity.getViewModel().platformFilters.getValue();
                showFilterDropDownMenu(v1, Objects.requireNonNull(f1));
                break;

            case SHOW_THEME_FILTERS:
                AutoCompleteTextView v2 = activity.getBinding().searcherOptions
                        .activitySearcherOptionsThemeTextbox;
                Set<String> f2 = activity.getViewModel().themeFilters.getValue();
                showFilterDropDownMenu(v2, Objects.requireNonNull(f2));
                break;

            case SHOW_GENRE_FILTERS:
                AutoCompleteTextView v3 = activity.getBinding().searcherOptions
                        .activitySearcherOptionsGenreTextbox;
                Set<String> f3 = activity.getViewModel().genreFilters.getValue();
                showFilterDropDownMenu(v3, Objects.requireNonNull(f3));
                break;

            case SHOW_SORT_CHOICES:
                activity.getBinding().searcherOptions.activitySearcherOptionsSortDropdown
                        .performClick();
                break;

            case SHOW_ORDER_CHOICES:
                activity.getBinding().searcherOptions.activitySearcherOptionsOrderDropdown
                        .performClick();
                break;

            case SHOW_GO_TO_PAGE_DIALOG:
                long totalPages = (long) event.getExtra("total_pages");
                GoToPageDialog pd = new GoToPageDialog(activity, totalPages);
                pd.setOnGoToPageDialogActionListener(activity.getViewModel());
                pd.show();
                break;

            case CLOSE_SEARCH_OPTIONS:
                activity.getBinding().searcherDrawer.closeDrawers();
                break;
        }
    }

    private void showFilterDropDownMenu(AutoCompleteTextView textBox, Set<String> filters) {
        PopupMenu menu = new PopupMenu(activity, textBox);
        for (String filter :
                filters) {
            menu.getMenu().add(filter);
        }

        menu.setOnMenuItemClickListener(item -> {
            textBox.setText(item.getTitle());
            return true;
        });

        menu.show();
    }

    public void setupPlatformFilters(@NonNull Set<String> platformFilters) {
        String[] array = new String[platformFilters.size()];
        platformFilters.toArray(array);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(activity, android.R.layout
                .simple_list_item_1, array);
        activity.getBinding().searcherOptions.activitySearcherOptionsPlatformTextbox.setAdapter
                (adapter);
    }

    public void setupThemeFilters(@NonNull Set<String> themeFilters) {
        String[] array = new String[themeFilters.size()];
        themeFilters.toArray(array);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(activity, android.R.layout
                .simple_list_item_1, array);
        activity.getBinding().searcherOptions.activitySearcherOptionsThemeTextbox.setAdapter
                (adapter);
    }

    public void setupGenreFilters(@NonNull Set<String> genreFilters) {
        String[] array = new String[genreFilters.size()];
        genreFilters.toArray(array);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(activity, android.R.layout
                .simple_list_item_1, array);
        activity.getBinding().searcherOptions.activitySearcherOptionsGenreTextbox.setAdapter(adapter);
    }

    public void setupSortChoices(@NonNull Set<String> sortChoices) {
        String[] array = new String[sortChoices.size()];
        sortChoices.toArray(array);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(activity, android.R.layout
                .simple_list_item_1, array);
        activity.getBinding().searcherOptions.activitySearcherOptionsSortDropdown.setAdapter(adapter);
    }
}
