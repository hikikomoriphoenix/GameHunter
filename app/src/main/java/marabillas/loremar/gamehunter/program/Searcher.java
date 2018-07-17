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

import marabillas.loremar.gamehunter.apis.BaseAPI;
import marabillas.loremar.gamehunter.ui.activity.MainActivity;

/**
 * This class will manage the page for searching video games.
 */
public class Searcher implements MainActivity.SearchEventsListener, GameHunterApp
        .ActivityChangeListener {
    private MainActivity activity;
    private BaseAPI api;

    /**
     * Initializes this class with the selected site's API
     *
     * @param activity the activity that manages the search screen
     * @param api the API of the selected site.
     */
    Searcher(MainActivity activity, BaseAPI api) {
        this.activity = activity;
        this.api = api;
        GameHunterApp.getInstance().addActivityChangeListener(this);
    }

    @Override
    public void onActivityChange(MainActivity activity) {
        this.activity = activity;
    }
}
