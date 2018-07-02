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

/**
 * This class attempts to wrap any available api from any game database website. It is meant to
 * be subclassed for each specific api.
 */
public abstract class BaseAPI {
    // List of features that may be provided by API
    protected static final int SEARCH = 0x10000000;            // allows keyword input for searching
    protected static final int THUMBNAIL = 0x20000000;         // provides thumbnails in the list

    private int configuration;

    protected BaseAPI() {
        configuration = configure();
    }

    /**
     * The extending class must explicitly specify which features are available in the api.
     * Example:
     * <pre>
     * <code>
     * {@literal @}Override
     *              protected int configure() {
     *                  return SEARCH | THUMBNAIL;
     *              }
     * </code>
     * </pre>
     *
     * @return a set of features separated by a pipe{|} operator
     */
    protected abstract int configure();

    /**
     * Checks if this API allows user to input a keyword to search the database
     *
     * @return true if this feature is available
     */
    public boolean hasSearch() {
        return (configuration & SEARCH) == SEARCH;
    }

    /**
     * Checks if this API can provide thumbnails for the returned list of games
     *
     * @return true if thumbnails can be provided.
     */
    public boolean hasThumbnails() {
        return (configuration & THUMBNAIL) == THUMBNAIL;
    }
}
