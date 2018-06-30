package marabillas.loremar.gamehunter.parsers;

import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class JSONParserTest {
    @Test
    public void parse() {
        JSON json = new JSONParser().parse("https://raw.githubusercontent" +
                ".com/hikikomoriphoenix/android-libraries-lab/master/test.json");
        String test = "{\n" +
                "  \"fruits\":[\n" +
                "    {\n" +
                "      \"name\":\"mango\",\n" +
                "      \"color\":\"yellow\",\n" +
                "      \"shape\":\"oval\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\":\"appe\",\n" +
                "      \"color\":\"red\",\n" +
                "      \"shape\":\"round\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\":\"banana\",\n" +
                "      \"color\":\"yellow\",\n" +
                "      \"shape\":\"elongated\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\":\"pineapple\",\n" +
                "      \"color\":\"yellow\",\n" +
                "      \"shape\":\"oval\"\n" +
                "    }\n" +
                "    ]\n" +
                "}";
        test = test.replaceAll("\\s", "");
        assertThat(json.toString(), is(test));
    }
}
