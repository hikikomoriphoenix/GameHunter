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

import marabillas.loremar.gamehunter.ui.activity.MainActivity;

/**
 * This class will manage the selection of sites which hosts the database to be used in searching
 * for video games.
 */
public class Chooser implements MainActivity.ChooseEventsListener, GameHunterApp.ActivityChangeListener {
    private MainActivity activity;

    /**
     * The site that hosts the video game database
     */
    public enum Site {
        GIANTBOMB
    }

    Chooser() {
        GameHunterApp.getInstance().addActivityChangeListener(this);
    }

    @Override
    public void onActivityChange(MainActivity activity) {
        this.activity = activity;
        this.activity.setChooseEventsListener(this);
    }
}
