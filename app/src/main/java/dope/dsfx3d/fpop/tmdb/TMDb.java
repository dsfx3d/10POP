package dope.dsfx3d.fpop.tmdb;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;

import dope.dsfx3d.fpop.R;

/**
 * @dope on 16-07-2016.
 */
public class TMDb {
    private final String BASEURL = "https://api.themoviedb.org/3";
    /**------------Meh--------------*/

    //------------------------------------request method
    public static final String SEARCH = "search";
    public static final String DISCOVER = "discover";
    public static final String FIND = "find";

    //------------------------------------Content type
    public static final String MOVIE = "movie";
    public static final String TV = "tv";

    //------------------------------------SortBY
    public static final String POPULARITY_ACS = "popularity.asc";
    public static final String POPULARITY_DESC = "popularity.desc";
    public static final String RELEASE_DATE_ASC = "release_date.asc";
    public static final String RELEASE_DATE_DESC = "release_date.desc";
    public static final String REVENUE_ASC = "revenue.asc";
    public static final String REVENUE_DESC = "revenue.desc";
    public static final String PRIMARY_RELEASE_DATE_ASC = "primary_release_date.asc";
    public static final String PRIMARY_RELEASE_DATE_DESC = "primary_release_date.desc";
    public static final String ORIGINAL_TITLE_ASC = "original_title.asc";
    public static final String ORIGINAL_TITLE_DESC = "original_title.desc";
    public static final String VOTE_AVG_ASC = "vote_average.asc";
    public static final String VOTE_AVG_DESC = "vote_average.desc";
    public static final String VOTE_COUNT_ASC = "vote_count.asc";
    public static final String VOTE_COUNT_DESC = "vote_count.desc";

    //--------------------------------------COUNTRY
    public static final String ORIGIN_US="US";
    public static final String ORIGIN_IN="IN";


    //--------------------------------------CERT
    public static final String[] CERT_US = {"NR", "G", "PG", "PG-13", "R", "NC-17"};
    public static final String[] CERT_IN = {"U", "UA", "A"};

    //--------------------------------------LANGUAGE
    public static final String LANGUAGE_ENGLISH="en";
    public static final String LANGUAGE_HINDI="hi";

    //--------------------------------------URL VARS
    private String method=DISCOVER;
    private String contentType=MOVIE;
    private String country="";//"certification_country="+ORIGIN_IN;
    private String certification="";//"certification="+CERT_IN[0];
    private String language="";//"language="+LANGUAGE_ENGLISH;
    private String page="";//"page=1";
    private String releaseYear=null;

    private static String sortBy="sort_by="+POPULARITY_DESC;

    public TMDb(){
    }

    public TMDb method(String type) {
        method = type;
        return this;
    }

    public TMDb contentType(String content) {
        contentType=content;
        return this;
    }

    public TMDb country(String origin) {
        country = "certification_country="+origin;
        return this;
    }

    public TMDb certification(String certificate) {
        certification="certification="+certificate;
        return this;
    }

    public TMDb language(String lang) {
        language="original_language="+lang;
        return this;
    }

    public TMDb releaseYear(int YEAR) {
        releaseYear="primary_release_year="+YEAR;
        return this;
    }

    public TMDb sortBy(String key) {
        sortBy="sort_by=" + key;
        return this;
    }

    public URL getUrl(Context context) {
        URL rurl = null;

        if (releaseYear==null) {
            releaseYear = "primary_release_year="+getCurrentYear();
        }

        String url = prepareURLStructure()+context.getString(R.string.TMDb_key);
        try {
            Log.v("__query_url",url);
            rurl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Toast.makeText(context, "Url is incorrectly formatted", Toast.LENGTH_SHORT).show();
        }
        return rurl;
    }

    private String prepareURLStructure() {
        return  BASEURL+"/"+
                method +"/"+
                contentType +"?"+
                country +"&"+
                certification +"&"+
                language +"&"+
                releaseYear +"&"+
                sortBy +"&api_key=";
    }

    private int getCurrentYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }
}
