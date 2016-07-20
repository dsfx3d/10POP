package dope.dsfx3d.fpop;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import dope.dsfx3d.fpop.adapters.GridAdapter;


/**
 * @dope on 18-07-2016
 */
public class DetailsFragment extends Fragment {

    View rootView;
    TextView titleTF;
    ImageView poster, backdrop;
    EditText overview;
    RatingBar ratingBar;

    public DetailsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_details, container, false);
        try {
            Details details = new Details(getDetails());
            initUI();
            prepareUI(details);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return rootView;
    }

    void initUI() {
        titleTF = (TextView) rootView.findViewById(R.id.title);
        poster = (ImageView) rootView.findViewById(R.id.poster);
        overview = (EditText) rootView.findViewById(R.id.overview);
        ratingBar = (RatingBar)rootView.findViewById(R.id.rating_bar);
        backdrop = (ImageView)rootView.findViewById(R.id.backdrop);
    }

    void prepareUI(Details details) throws JSONException {
        titleTF.setText(details.getTitle());
        overview.setText(details.getOverView());
        ratingBar.setRating(details.getRating());

        Picasso
                .with(getContext())
                .load(details.getPoster())
                .resize((int) (GridAdapter.getScreenWidth() / 2.5), (int) (GridAdapter.getScreenHeight() / 2.5))
                .into(poster);

        String backdropPath = details.getBackdrop();
        if (backdropPath!=null) {
            Picasso
                    .with(getContext())
                    .load(backdropPath)
                    .resize(GridAdapter.getScreenWidth(), GridAdapter.getScreenHeight() / 3)
                    .into(backdrop);
        }
    }

    JSONObject getDetails() throws JSONException {
        return new JSONObject(getArguments().getString("details"));
    }

    class Details {

        JSONObject details;

        public Details(JSONObject object) {
            details = object;
        }

        public String getTitle() {
            try {
                return details.getString("title");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        public String getPoster() {
            try {
                return "http://image.tmdb.org/t/p/w185"+details.getString("poster_path");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        public String getOverView() {
            try {
                return details.getString("overview");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        public float getRating() {
            try {
                return (float)(details.getDouble("vote_average")/2) + 1;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return 0;
        }

        public String getBackdrop() {
            try {
                return "http://image.tmdb.org/t/p/w1280"+details.getString("backdrop_path");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
