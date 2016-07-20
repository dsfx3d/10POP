package dope.dsfx3d.fpop.interfaces;

import org.json.JSONObject;

/**
 * @dope on 16-07-2016.
 */
public interface TMDbRequestListener {
    void onRequestComplete(JSONObject response);
}
