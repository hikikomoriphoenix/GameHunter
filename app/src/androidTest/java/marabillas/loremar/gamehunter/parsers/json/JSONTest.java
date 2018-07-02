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

import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import marabillas.loremar.gamehunter.parsers.FailedToGetFieldException;
import marabillas.loremar.gamehunter.parsers.FailedToParseException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class JSONTest {
    @Test
    public void testGetters() {
        String url = "https://raw.githubusercontent" +
                ".com/hikikomoriphoenix/some-random-static-files/master/json/simple-all-datatypes.json";
        JSON data = null;
        try {
            data = new JSONParser().parse(url);
        } catch (FailedToParseException e) {
            Assert.fail(e.toString());
        }

        try {
            assertThat(data.getArray("array").toString(), is("[1,2,3,4,5]"));

            assertThat(data.getBoolean("boolean"), is(true));

            assertThat(data.getDouble("decimal"), is(-52.434));

            assertThat(data.getInt("integer"), is(-20));

            assertThat(data.getLong("long"), is(-394857989234242545L));

            String objectTest = "{\"shape\":\"circle\", \"size\":50}";
            objectTest = objectTest.replaceAll("\\s", "");
            assertThat(data.getObject("object").toString(), is(objectTest));

            assertThat(data.getString("string"), is("Planet Earth"));
        } catch (FailedToGetFieldException e) {
            Assert.fail(e.toString());
        }
    }

    /**
     * Tests if getters can throw FailedToGetFieldExceptions
     */
    @Test
    public void testGettersForFailedToGetFieldExceptions() {
        String url = "https://raw.githubusercontent" +
                ".com/hikikomoriphoenix/some-random-static-files/master/json/simple-all-datatypes.json";
        JSON data = null;
        try {
            data = new JSONParser().parse(url);
        } catch (FailedToParseException e) {
            Assert.fail(e.toString());
        }

        String shouldThrow = "Should have thrown FailedToGetFieldException";

        try {
            data.getArray("string");
            Assert.fail(shouldThrow);
        } catch (FailedToGetFieldException e) {
            e.printStackTrace();
        }

        try {
            data.getBoolean("string");
            Assert.fail(shouldThrow);
        } catch (FailedToGetFieldException e) {
            e.printStackTrace();
        }

        try {
            data.getDouble("string");
            Assert.fail(shouldThrow);
        } catch (FailedToGetFieldException e) {
            e.printStackTrace();
        }

        try {
            data.getInt("string");
            Assert.fail(shouldThrow);
        } catch (FailedToGetFieldException e) {
            e.printStackTrace();
        }

        try {
            data.getLong("string");
            Assert.fail();
        } catch (FailedToGetFieldException e) {
            e.printStackTrace();
        }

        try {
            data.getObject("string");
            Assert.fail(shouldThrow);
        } catch (FailedToGetFieldException e) {
            e.printStackTrace();
        }

        try {
            // Apparently, any field can return as a string as long as its name exists in the data
            data.getString("assume this field does not exist");
            Assert.fail(shouldThrow);
        } catch (FailedToGetFieldException e) {
            e.printStackTrace();
        }

        try {
            data.getObject("null");
            Assert.fail(shouldThrow);
        } catch (FailedToGetFieldException e) {
            e.printStackTrace();
        }
    }
}
