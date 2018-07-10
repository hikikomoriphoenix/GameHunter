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

package marabillas.loremar.gamehunter.utils;

import android.util.Log;

public class LogUtils {
    private static final String TAG = "GameHunterLogs";

    private LogUtils() {
    }

    /**
     * log a verbose message using GameHunterLogs tag
     *
     * @param message message to log
     */
    public static void log(String message) {
        Log.v(TAG, message);
    }

    /**
     * log an error message using GameHunterLogs tag
     *
     * @param message messge to log
     */
    public static void logError(String message) {
        Log.e(TAG, message);
    }

    /**
     * log a warning message using GameHunterLogs tag
     *
     * @param message meddage to log
     */
    public static void logWarning(String message) {
        Log.w(TAG, message);
    }
}
