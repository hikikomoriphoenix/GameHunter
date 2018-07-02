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
 * This class wraps an array in a JSON data. Use the getter methods to get the value of each
 * element.
 */
public class JSON_Array {
    private JSONArray array;

    JSON_Array(JSONArray array) {
        this.array = array;
    }

    public JSON_Array getArray(int index) throws FailedToGetFieldException {
        try {
            JSONArray subArray = array.getJSONArray(index);
            return new JSON_Array(subArray);
        } catch (JSONException e) {
            throw new FailedToGetFieldException(e);
        }
    }

    public boolean getBoolean(int index) throws FailedToGetFieldException {
        try {
            return array.getBoolean(index);
        } catch (JSONException e) {
            throw new FailedToGetFieldException(e);
        }
    }

    public int getCount() {
        return array.length();
    }

    public double getDouble(int index) throws FailedToGetFieldException {
        try {
            return array.getDouble(index);
        } catch (JSONException e) {
            throw new FailedToGetFieldException(e);
        }
    }

    public int getInt(int index) throws FailedToGetFieldException {
        try {
            return array.getInt(index);
        } catch (JSONException e) {
            throw new FailedToGetFieldException(e);
        }
    }

    public long getLong(int index) throws FailedToGetFieldException {
        try {
            return array.getLong(index);
        } catch (JSONException e) {
            throw new FailedToGetFieldException(e);
        }
    }

    public JSON getObject(int index) throws FailedToGetFieldException {
        try {
            JSONObject object = array.getJSONObject(index);
            return new JSON(object);
        } catch (JSONException e) {
            throw new FailedToGetFieldException(e);
        }
    }

    public String getString(int index) throws FailedToGetFieldException {
        try {
            return array.getString(index);
        } catch (JSONException e) {
            throw new FailedToGetFieldException(e);
        }
    }

    /**
     * @return the JSON array as a string
     */
    public String toString() {
        return array.toString();
    }
}
