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

import marabillas.loremar.gamehunter.apis.giantbomb.GiantBomb;
import marabillas.loremar.gamehunter.ui.activity.MainActivity;

/**
 * This class will manage the selection of sites which hosts the database to be used in searching
 * for video games.
 */
public class Chooser implements MainActivity.ChooseEventsListener, GameHunterApp.ActivityChangeListener {
    private MainActivity activity;

    /**
     * This constructor should be called before MainActivity's onCreate
     */
    Chooser() {
        GameHunterApp.getInstance().addActivityChangeListener(this);
    }

    /**
     * Constructor for Chooser. Sets the Chooser to listen to MainActivity's events when
     * selecting sites.
     *
     * @param activity MainActivity
     */
    Chooser(MainActivity activity) {
        GameHunterApp.getInstance().addActivityChangeListener(this);
        this.activity = activity;
        this.activity.setChooseEventsListener(this);
        this.activity.setScreenToWebsiteSelectionScreen();
    }

    @Override
    public void onActivityChange(MainActivity activity) {
        this.activity = activity;
        this.activity.setChooseEventsListener(this);
        this.activity.setScreenToWebsiteSelectionScreen();
    }

    @Override
    public void choose(String website) {
        //Switch to the search screen to start finding games in the selected website's database.
        // A new Searcher instance is created to listen to user events and an instance of an API
        // class corresponding to the selected website's API will handle queries for the database.
        switch (website) {
            case "Giant Bomb":
                new Searcher(activity, new GiantBomb());

                // Remove references to this instance
                GameHunterApp.getInstance().removeActivityChangeListener(this);
                activity.setChooseEventsListener(null);
                break;
        }
    }
}
