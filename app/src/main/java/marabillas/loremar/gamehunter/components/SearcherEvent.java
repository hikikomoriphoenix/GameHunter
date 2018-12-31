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

package marabillas.loremar.gamehunter.components;

import java.util.HashMap;
import java.util.Map;

public enum SearcherEvent {
    HIDE_SEARCH_ICON,
    HIDE_SEARCH_OPTIONS_ICON,
    SHOW_PROGRESS_VIEW,
    HIDE_PROGRESS_VIEW,
    SETUP_ORDER_BY,
    SHOW_PLATFORM_FILTERS,
    SHOW_THEME_FILTERS,
    SHOW_GENRE_FILTERS,
    SHOW_SORT_CHOICES,
    SHOW_ORDER_CHOICES,
    SHOW_GO_TO_PAGE_DIALOG,
    CLOSE_SEARCH_OPTIONS,
    SET_DEFAULT_SORT_BY_SELECTION;

    private Map<String, Object> extras = new HashMap<>();

    public void putExtra(String key, Object value) {
        extras.put(key, value);
    }

    public Object getExtra(String key) {
        return extras.get(key);
    }
}
