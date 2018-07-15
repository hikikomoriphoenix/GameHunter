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

package marabillas.loremar.gamehunter.apis.collections;

import java.util.Map;
import java.util.TreeMap;

public class GiantBombCollections {
    private Map<String, String> sortChoices;

    public GiantBombCollections() {
        sortChoices = new TreeMap<>();
        sortChoices.put("Date added", "dated_added");
        sortChoices.put("Date last updated", "date_last_updated");
        sortChoices.put("ID", "id");
        sortChoices.put("Name", "name");
        sortChoices.put("Number of user reviews", "number_of_user_reviews");
        sortChoices.put("Original game rating", "original_game_rating");
        sortChoices.put("Original release date", "original_release_date");
    }

    public Map<String, String> getSortChoices() {
        return sortChoices;
    }
}
