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
    private Set<Field> fields = null;
    private String platformFilter = null;
    private String genreFilter = null;
    private String themeFilter = null;
    private int releaseYear = -1;
    private int fromYear = -1;
    private int toYear = -1;
    private String sort = null;
    private Order order = Order.DESCENDING;
    private int resultsPerPage = 20;
    private int pageNumber = 1;

    public enum Field {THUMBNAIL, DESCRIPTION, RELEASE_DATE, ID}

    public enum Order {ASCENDING, DESCENDING}

    public String getKeyword() {
        return keyword;
    }

    public Set<Field> getFields() {
        return fields;
    }

    public String getPlatformFilter() {
        return platformFilter;
    }

    public String getGenreFilter() {
        return genreFilter;
    }

    public String getThemeFilter() {
        return themeFilter;
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

    public int getResultsPerPage() {
        return resultsPerPage;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public Query setKeyword(String keyword) {
        this.keyword = keyword;
        return this;
    }

    public Query setFields(Set<Field> fields) {
        this.fields = fields;
        return this;
    }

    public Query setPlatformFilter(String platformFilter) {
        this.platformFilter = platformFilter;
        return this;
    }

    public Query setGenreFilter(String genreFilter) {
        this.genreFilter = genreFilter;
        return this;
    }

    public Query setThemeFilter(String themeFilter) {
        this.themeFilter = themeFilter;
        return this;
    }

    public Query setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
        return this;
    }

    public Query setReleaseYearsRange(int fromYear, int toYear) {
        this.fromYear = fromYear;
        this.toYear = toYear;
        return this;
    }

    public Query setFromYear(int fromYear) {
        this.fromYear = fromYear;
        return this;
    }

    public Query setToYear(int toYear) {
        this.toYear = toYear;
        return this;
    }

    public Query setSort(String sort) {
        this.sort = sort;
        return this;
    }

    public Query setOrder(Order order) {
        this.order = order;
        return this;
    }

    public void setResultsPerPage(int resultsPerPage) {
        this.resultsPerPage = resultsPerPage;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }
}
