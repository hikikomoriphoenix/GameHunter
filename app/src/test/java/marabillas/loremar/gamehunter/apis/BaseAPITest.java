package marabillas.loremar.gamehunter.apis;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class BaseAPITest {
    class TestAPI extends BaseAPI {
        TestAPI() {
            configure(SEARCH | THUMBNAIL);
        }
    }

    @Test
    public void testConfigurations() {
        TestAPI api = new TestAPI();
        assertThat(api.hasSearch(), is(true));
        assertThat(api.hasThumbnails(), is(true));
    }
}