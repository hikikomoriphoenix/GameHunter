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

package marabillas.loremar.gamehunter.ui.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;

import marabillas.loremar.gamehunter.R;

public class DarkOrLightLayout extends ConstraintLayout {
    private Drawable lightThemeBackground;
    private Drawable darkThemeBackground;
    private int lightThemeTextColor;
    private int darkThemeTextColor;
    private Theme theme;

    public enum Theme {LIGHT, DARK}

    public DarkOrLightLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public DarkOrLightLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray array = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable
                .DarkOrLightLayout, 0, 0);

        lightThemeBackground = array.getDrawable(R.styleable
                .DarkOrLightLayout_searcher_results_item_light_themed_background);

        darkThemeBackground = array.getDrawable(R.styleable
                .DarkOrLightLayout_searcher_results_item_dark_themed_background);

        int lightDef = ResourcesCompat.getColor(getResources(), R.color.white, null);
        lightThemeTextColor = array.getColor(R.styleable
                .DarkOrLightLayout_searcher_results_item_light_themed_text_color, lightDef);

        int darkDef = ResourcesCompat.getColor(getResources(), R.color.lighterBlack, null);
        darkThemeTextColor = array.getColor(R.styleable
                .DarkOrLightLayout_searcher_results_item_dark_themed_text_color, darkDef);
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
        switch (theme) {
            case LIGHT:
                ViewCompat.setBackground(this, lightThemeBackground);
                break;
            case DARK:
                ViewCompat.setBackground(this, darkThemeBackground);
                break;
        }
    }

    public Drawable getLightThemeBackground() {
        return lightThemeBackground;
    }

    public Drawable getDarkThemeBackground() {
        return darkThemeBackground;
    }

    public Drawable getDefaultBackground() {
        switch (theme) {
            case LIGHT:
                return lightThemeBackground;
            case DARK:
                return darkThemeBackground;
            default:
                return lightThemeBackground;
        }
    }

    public int getLightThemeTextColor() {
        return lightThemeTextColor;
    }

    public int getDarkThemeTextColor() {
        return darkThemeTextColor;
    }

    public int getDefaultTextColor() {
        switch (theme) {
            case LIGHT:
                return lightThemeTextColor;
            case DARK:
                return darkThemeTextColor;
            default:
                return darkThemeTextColor;
        }
    }

    public Theme getTheme() {
        return theme;
    }
}
