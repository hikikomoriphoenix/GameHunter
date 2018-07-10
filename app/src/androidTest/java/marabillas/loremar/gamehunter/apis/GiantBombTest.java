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

import org.junit.Assert;
import org.junit.Test;

import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class GiantBombTest {

    @Test
    public void getPlatformFilters() {
        BaseAPI api = new GiantBomb();
        Set<String> filters = null;
        try {
            filters = api.getPlatformFilters();
        } catch (BaseAPIGetterFailedToGetException e) {
            Assert.fail(e.getMessage());
        }
        assertThat(filters.size(), is(155));
        String[] filtersArray = new String[filters.size()];
        filters.toArray(filtersArray);
        assertThat(filtersArray[0], is("3DO"));
        assertThat(filtersArray[154], is("ZX Spectrum"));
    }
}