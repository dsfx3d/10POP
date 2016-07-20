package dope.dsfx3d.fpop.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @dope on 16-07-2016.
 */
public class MovieListParser {

    private JSONObject response;
    private JSONArray results;

    public MovieListParser(JSONObject response) {
        try {
            this.response = response;
            results = response.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public String getPosterUrl(int position) {
        String path = null;
        try {
            path = "http://image.tmdb.org/t/p/w185"+results.getJSONObject(position).get("poster_path").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return path;
    }

    public JSONObject getDetails(int position) {
        try {
            return results.getJSONObject(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int length() {
        try {
            return response.getJSONArray("results").length();
        } catch (JSONException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
