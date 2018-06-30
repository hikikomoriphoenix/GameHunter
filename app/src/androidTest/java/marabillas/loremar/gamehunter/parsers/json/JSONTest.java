package marabillas.loremar.gamehunter.parsers.json;

import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import marabillas.loremar.gamehunter.parsers.FailedToGetFieldException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class JSONTest {
    @Test
    public void testGetters() {
        String url = "https://raw.githubusercontent" +
                ".com/hikikomoriphoenix/some-random-static-files/master/json/simple-all-datatypes.json";
        JSON data = new JSONParser().parse(url);

        try {
            assertThat(data.getArray("array").toString(), is("[1,2,3,4,5]"));
        } catch (FailedToGetFieldException e) {
            Assert.fail(e.toString());
        }

        try {
            assertThat(data.getBoolean("boolean"), is(true));
        } catch (FailedToGetFieldException e) {
            Assert.fail(e.toString());
        }

        try {
            assertThat(data.getDouble("decimal"), is(-52.434));
        } catch (FailedToGetFieldException e) {
            Assert.fail(e.toString());
        }

        try {
            assertThat(data.getInt("integer"), is(-20));
        } catch (FailedToGetFieldException e) {
            Assert.fail(e.toString());
        }

        try {
            assertThat(data.getLong("long"), is(-394857989234242545L));
        } catch (FailedToGetFieldException e) {
            Assert.fail(e.toString());
        }

        String objectTest = "{\"shape\":\"circle\", \"size\":50}";
        objectTest = objectTest.replaceAll("\\s", "");
        try {
            assertThat(data.getObject("object").toString(), is(objectTest));
        } catch (FailedToGetFieldException e) {
            Assert.fail(e.toString());
        }

        try {
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
        JSON data = new JSONParser().parse(url);

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
