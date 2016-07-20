package dope.dsfx3d.fpop.tmdb;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import dope.dsfx3d.fpop.interfaces.TMDbRequestListener;

/**
 * @dope on 15-07-2016.
 */
public class TMDbRequestListTask extends AsyncTask<Void,Void,JSONObject> {

    static String LOG="__"+TMDbRequestListTask.class.getSimpleName();

    //stuff
    private URL url;
    private InputStream inputStream;
    private ByteArrayOutputStream byteArrayOutputStream;

    private TMDbRequestListener taskListener;

    public TMDbRequestListTask(URL u, TMDbRequestListener taskListener) {
        url = u;
        this.taskListener = taskListener;
    }

    @Override
    protected JSONObject doInBackground(Void... params) {
        Log.v(LOG,"Executing...");
        JSONObject response = new JSONObject();
        try {
            response = getResponse();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    protected void onPostExecute(JSONObject rootObj) {
        taskListener.onRequestComplete(rootObj);
    }

    private JSONObject getResponse() throws IOException, JSONException {
        Log.v(LOG,"Preparing Response");
        int nRead;
        byte[] data = new byte[16384];

        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        inputStream = connection.getInputStream();
        byteArrayOutputStream = new ByteArrayOutputStream();

        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
            byteArrayOutputStream.write(data, 0, nRead);
        }
        byteArrayOutputStream.flush();
        return new JSONObject(byteArrayOutputStream.toString());
    }
}
