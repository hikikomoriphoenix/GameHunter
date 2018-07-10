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

package marabillas.loremar.gamehunter.apis;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import marabillas.loremar.gamehunter.GameHunterApp;
import marabillas.loremar.gamehunter.R;
import marabillas.loremar.gamehunter.framework.Query;
import marabillas.loremar.gamehunter.framework.ResultsItem;

/**
 *  This class is used for getting access to GiantBomb.com's database using their api.
 */
public class GiantBomb extends BaseAPI {
    private final String KEY = GameHunterApp.getInstance().getResources().getString(R.string
            .giantbomb_api_key);

    @Override
    protected Set<Feature> configure() {
        return EnumSet.of(Feature.SEARCH, Feature.THUMBNAIL, Feature.DESCRIPTION, Feature
                .RELEASE_DATE, Feature.FILTER_BY_PLATFORM, Feature.FILTER_BY_YEAR, Feature
                .FILTER_BY_YEARS, Feature.SORT_BY_REVERSIBLE);
    }

    @Override
    public Set<String> getGenreFilters() {
        return null;
    }

    @Override
    public Set<String> getPlatformFilters() {
        return null;
    }

    @Override
    public Set<String> getSortChoices() {
        return null;
    }

    @Override
    public Set<String> getThemeFilters() {
        return null;
    }

    @Override
    public List<ResultsItem> query(Query query) {
        return null;
    }
}
