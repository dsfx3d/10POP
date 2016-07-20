package dope.dsfx3d.fpop.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import dope.dsfx3d.fpop.R;
import dope.dsfx3d.fpop.interfaces.TheGridInterface;
import dope.dsfx3d.fpop.json.MovieListParser;

/**
 * Created by yashodhan on 15-07-2016.
 */
public class GridAdapter extends BaseAdapter {
    private Context context;
    private MovieListParser listParser;

    private ImageView posterView;
    private TheGridInterface gridInterface;

    public GridAdapter(Context c, JSONObject response, TheGridInterface gridInterface) {
        context = c;
        listParser = new MovieListParser(response);
        this.gridInterface = gridInterface;
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View theView, ViewGroup parent) {
        if(theView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            theView = inflater.inflate(R.layout.single_grid_element, parent, false);
        }
        prepareUI(theView);

        if(position%2==0) {
            posterView.setPadding(0,-1,1,0);
        } else {
            posterView.setPadding(0,0,0,1);
        }

        Picasso
            .with(context)
            .load(listParser.getPosterUrl(position))
            .resize(getScreenWidth()/2, getScreenHeight()/2)
            .centerCrop()
            .placeholder(R.drawable.placeholder_image)
            .into(posterView);

        Log.v("__poster", listParser.getPosterUrl(position));
        gridInterface.handleItemClick(theView, listParser.getDetails(position));
        return theView;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public void prepareUI(View view) {
        posterView = (ImageView) view.findViewById(R.id.poster);
        posterView.setMinimumHeight(getScreenHeight() / 2);
        posterView.setMinimumWidth(getScreenWidth()/2);
    }

}
