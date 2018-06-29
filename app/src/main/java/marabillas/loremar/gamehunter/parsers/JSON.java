package marabillas.loremar.gamehunter.parsers;

import org.json.JSONObject;

/**
 * This class wraps the JSON data. An instance of this class is created by parsing through
 * JSONParser
 */
public class JSON {
    private JSONObject json;

    // This constructor Should be package private to limit its instantiation to JSONParser
    JSON(JSONObject json) {
        this.json = json;
    }

    // todo various getters and putters go here
}
