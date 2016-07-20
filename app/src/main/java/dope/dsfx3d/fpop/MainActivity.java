package dope.dsfx3d.fpop;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.GridView;
import android.widget.RelativeLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

import dope.dsfx3d.fpop.adapters.GridAdapter;
import dope.dsfx3d.fpop.interfaces.TMDbRequestListener;
import dope.dsfx3d.fpop.interfaces.TheGridInterface;
import dope.dsfx3d.fpop.tmdb.TMDb;
import dope.dsfx3d.fpop.tmdb.TMDbRequestListTask;

public class MainActivity extends BaseActivity {

    private Dialog dialog;
    private GridView gridView;
    private RelativeLayout detailsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prepareUI();

        URL url = (new TMDb())
                .method(TMDb.DISCOVER)
                .contentType(TMDb.MOVIE)
                .country(TMDb.ORIGIN_IN)
                .language(TMDb.LANGUAGE_HINDI)
                .sortBy(TMDb.POPULARITY_DESC)
                .getUrl(this);

        TMDbRequestListTask apiRequestTask = new TMDbRequestListTask(url, tmDbRequestListener);
        apiRequestTask.execute();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        if(gridView.getVisibility()==View.GONE) {
            gridView.setVisibility(View.VISIBLE);
        }
    }

    private void prepareUI() {
        gridView = (GridView)findViewById(R.id.grid);
        detailsLayout = (RelativeLayout)findViewById(R.id.details);

        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.search_dailog);
    }

    public void loadDetailsInFragment(JSONObject details) {
        DetailsFragment detailsFragment = new DetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("details",details.toString());

        detailsFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.details_fragment,detailsFragment).commit();
    }

    //////////////////////////////////////////////////////////////////////////////

    TMDbRequestListener tmDbRequestListener = new TMDbRequestListener() {
        @Override
        public void onRequestComplete(JSONObject result) {
            try {
                Log.v("__GridFragment", "Result: " + result.getJSONArray("results").length() + " items");
                GridAdapter gridAdapter = new GridAdapter(MainActivity.this, result, gridInterface);
                gridView.setAdapter(gridAdapter);
                loadDetailsInFragment((JSONObject)(result.getJSONArray("results").get(0)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    TheGridInterface gridInterface = new TheGridInterface() {
        @Override
        public void handleItemClick(View root, final JSONObject details) {
            root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gridView.setVisibility(View.GONE);
                    loadDetailsInFragment(details);
                }
            });
        }
    };
    //////////////////////////////////////////////////////////////////////////////////

}
