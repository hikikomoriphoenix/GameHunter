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

package marabillas.loremar.gamehunter.parsers.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import marabillas.loremar.gamehunter.parsers.FailedToGetFieldException;

/**
 * This class wraps the JSON data. Use the getter methods to get each field. An instance of this
 * class is created by parsing through JSONParser.
 */
public class JSON {
    private JSONObject json;

    // This constructor should be package private to limit its instantiation to JSONParser
    JSON(JSONObject json) {
        this.json = json;
    }

    public JSON_Array getArray(String name) throws FailedToGetFieldException {
        try {
            JSONArray array = json.getJSONArray(name);
            return new JSON_Array(array);
        } catch (JSONException e) {
            throw new FailedToGetFieldException(e);
        }
    }

    public boolean getBoolean(String name) throws FailedToGetFieldException {
        try {
            return json.getBoolean(name);
        } catch (JSONException e) {
            throw new FailedToGetFieldException(e);
        }
    }

    public double getDouble(String name) throws FailedToGetFieldException {
        try {
            return json.getDouble(name);
        } catch (JSONException e) {
            throw new FailedToGetFieldException(e);
        }
    }

    public int getInt(String name) throws FailedToGetFieldException {
        try {
            return json.getInt(name);
        } catch (JSONException e) {
            throw new FailedToGetFieldException(e);
        }
    }

    public long getLong(String name) throws FailedToGetFieldException {
        try {
            return json.getLong(name);
        } catch (JSONException e) {
            throw new FailedToGetFieldException(e);
        }
    }

    public JSON getObject(String name) throws FailedToGetFieldException {
        try {
            JSONObject object = json.getJSONObject(name);
            return new JSON(object);
        } catch (JSONException e) {
            throw new FailedToGetFieldException(e);
        } catch (Exception e) {
            throw new FailedToGetFieldException(e);
        }
    }

    public String getString(String name) throws FailedToGetFieldException {
        try {
            return json.getString(name);
        } catch (JSONException e) {
            throw new FailedToGetFieldException(e);
        }
    }

    /**
     * @return the whole JSON data as a string
     */
    public String toString() {
        return json.toString();
    }
}
