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

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import marabillas.loremar.gamehunter.GameHunterApp;

/**
 * Utility class related to measurements
 */
public class MeasurementUtils {
    public static int getScreenWidthInPixels() {
        DisplayMetrics displayMetrics = GameHunterApp.getInstance().getResources()
                .getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    /**
     * @param px      value in pixels to convert
     * @param context context required to get access to the display metrics
     * @return calculated value in dp
     */
    public static float convertPixelsToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }
}
