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

package marabillas.loremar.gamehunter.framework;

import java.util.Set;

/**
 * This class is for storing query values that you need to pass to an API to get results.
 */
public class Query {
    private String keyword = null;
    private Set<String> fields = null;
    private Set<String> platformFilters = null;
    private Set<String> genreFilters = null;
    private Set<String> themeFilters = null;
    private int releaseYear = -1;
    private int fromYear = -1;
    private int toYear = -1;
    private String sort = null;
    private Order order = Order.DESCENDING;

    public enum Order {ASCENDING, DESCENDING}

    public String getKeyword() {
        return keyword;
    }

    public Set<String> getFields() {
        return fields;
    }

    public Set<String> getPlatformFilters() {
        return platformFilters;
    }

    public Set<String> getGenreFilters() {
        return genreFilters;
    }

    public Set<String> getThemeFilters() {
        return themeFilters;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public int getFromYear() {
        return fromYear;
    }

    public int getToYear() {
        return toYear;
    }

    public String getSort() {
        return sort;
    }

    public Order getOrder() {
        return order;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void setFields(Set<String> fields) {
        this.fields = fields;
    }

    public void setPlatformFilters(Set<String> platformFilters) {
        this.platformFilters = platformFilters;
    }

    public void setGenreFilters(Set<String> genreFilters) {
        this.genreFilters = genreFilters;
    }

    public void setThemeFilters(Set<String> themeFilters) {
        this.themeFilters = themeFilters;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public void setReleaseYearsRange(int fromYear, int toYear) {
        this.fromYear = fromYear;
        this.toYear = toYear;
    }

    public void setFromYear(int fromYear) {
        this.fromYear = fromYear;
    }

    public void setToYear(int toYear) {
        this.toYear = toYear;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
