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
public class JSON_ArrayTest {
    @Test
    public void testGetters() {
        String url = "https://raw.githubusercontent" +
                ".com/hikikomoriphoenix/some-random-static-files/master/json/arrays-all-data-types.json";
        JSON json = new JSONParser().parse(url);
        try {
            JSON_Array arrays = json.getArray("arrays");
            assertThat(arrays.getArray(0).toString(), is("[1,2,3,4,5]"));
            assertThat(arrays.getArray(2).toString(), is("[true,false,true]"));

            JSON_Array booleans = json.getArray("booleans");
            assertThat(booleans.getBoolean(0), is(true));
            assertThat(booleans.getBoolean(3), is(false));

            JSON_Array doubles = json.getArray("doubles");
            assertThat(doubles.getDouble(0), is(12.324));
            assertThat(doubles.getDouble(2), is(63.786));

            JSON_Array ints = json.getArray("ints");
            assertThat(ints.getInt(0), is(123));
            assertThat(ints.getInt(2), is(789));

            JSON_Array longs = json.getArray("longs");
            assertThat(longs.getLong(0), is(9734953498465L));
            assertThat(longs.getLong(2), is(347856754684654L));

            JSON_Array objects = json.getArray("objects");
            assertThat(objects.getObject(0).toString(), is("{\"subject\":\"Math\",\"grade\":99}"));
            assertThat(objects.getObject(1).toString(), is("{\"subject\":\"Filipino\",\"grade\":72}"));

            JSON_Array strings = json.getArray("strings");
            assertThat(strings.getString(0), is("Doctor"));
            assertThat(strings.getString(3), is("Janitor"));

            // try an array containing different types of value
            url = "https://raw.githubusercontent" +
                    ".com/hikikomoriphoenix/some-random-static-files/master/json/arrays-contain-different-types.json";
            json = new JSONParser().parse(url);
            assertThat(json.getArray("array").getInt(1), is(25));

            assertThat(json.getArray("array").getCount(), is(4));
        } catch (FailedToGetFieldException e) {
            Assert.fail(e.toString());
        }
    }

    @Test
    public void testJSON_ArrayForFailedToGetFieldException() {
        String url = "https://raw.githubusercontent" +
                ".com/hikikomoriphoenix/some-random-static-files/master/json/arrays-all-data-types.json";
        JSON json = new JSONParser().parse(url);

        String shouldThrow = "Should have thrown FailedToGetFieldException";

        // Exception in casting classes
        try {
            json.getArray("arrays").getObject(0);
            Assert.fail(shouldThrow);
        } catch (FailedToGetFieldException e) {
            e.printStackTrace();
        }

        url = "https://raw.githubusercontent" +
                ".com/hikikomoriphoenix/some-random-static-files/master/json/arrays-contain-different-types.json";
        json = new JSONParser().parse(url);
        try {
            json.getArray("array").getObject(3);
            Assert.fail(shouldThrow);
        } catch (FailedToGetFieldException e) {
            e.printStackTrace();
        }
    }
}
