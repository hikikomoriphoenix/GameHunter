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

import android.app.Application;

import java.util.List;

import marabillas.loremar.gamehunter.ui.activity.MainActivity;

/**
 * GameHunter's main application class
 */
public class GameHunterApp extends Application {
    private static GameHunterApp thisInstance;
    private List<ActivityChangeListener> activityChangeListeners;

    @Override
    public void onCreate() {
        super.onCreate();
        thisInstance = this;
        new Chooser();
    }

    /**
     * Main singleton getter for this app.
     *
     * @return the app's object as a GameHunterApp instance.
     */
    public static GameHunterApp getInstance() {
        return thisInstance;
    }

    /**
     * Adds a listener to MainActivity's onCreate call events
     *
     * @param activityChangeListener an object implementing ActivityChangeListener
     */
    public void addActivityChangeListener(ActivityChangeListener activityChangeListener) {
        activityChangeListeners.add(activityChangeListener);
    }

    /**
     * Removes this listener from activityChangeListeners list. This method should also be called
     * especially if you want to remove any reference to this listener.
     *
     * @param activityChangeListener an object implementing ActivityChangeListener
     */
    public void removeActivityChangeListener(ActivityChangeListener activityChangeListener) {
        activityChangeListeners.remove(activityChangeListener);
    }

    /**
     * Sets a new reference to MainActivity
     *
     * @param activity new instance of MainActivity
     */
    public void setActivity(MainActivity activity) {
        for (ActivityChangeListener activityChangeListener : activityChangeListeners) {
            activityChangeListener.onActivityChange(activity);
        }
    }

    /**
     * If implemented, the object will be notified when MainActivity calls onCreate passing a new
     * reference to MainActivity
     */
    public interface ActivityChangeListener {
        /**
         * Whenever MainActivity's onCreate is called, this listener gets a new reference to
         * MainActivity
         *
         * @param activity new reference to MainActivity
         */
        void onActivityChange(MainActivity activity);
    }
}
