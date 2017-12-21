package be.henallux.masi.pedagogique.activities.musicalActivity;

import android.net.Uri;

import java.util.ArrayList;

import be.henallux.masi.pedagogique.model.Activity;
import be.henallux.masi.pedagogique.model.Location;

/**
 * Created by Le Roi Arthur on 18-12-17.
 */

public class ActivityMusic extends Activity {
    private Integer id;
    private Uri jsonFileStyleURI; //A json description for the map style, set as an option with the setup of the map
    private ArrayList<Location> pointsOfInterest;

    public ActivityMusic(Integer id, String name,Class associatedClass) {
        super(id, name, associatedClass);
    }


    public ActivityMusic(Integer id, String name, java.lang.Class associatedClass, Uri jsonFileStyle) {
        super(id,name,associatedClass);
        this.id = id;
        this.jsonFileStyleURI = jsonFileStyle;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Uri getStyle() {
        return jsonFileStyleURI;
    }

    public void setStyle(Uri style) {
        this.jsonFileStyleURI = style;
    }

}
