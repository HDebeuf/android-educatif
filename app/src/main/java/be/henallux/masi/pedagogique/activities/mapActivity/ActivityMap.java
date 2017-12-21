package be.henallux.masi.pedagogique.activities.mapActivity;

import android.net.Uri;
import android.os.Bundle;

import com.google.android.gms.maps.model.LatLng;

import java.lang.*;
import java.net.URI;
import java.util.ArrayList;

import be.henallux.masi.pedagogique.model.Activity;
import be.henallux.masi.pedagogique.model.Category;
import be.henallux.masi.pedagogique.model.Location;

/**
 * Created by Le Roi Arthur on 17-12-17.
 */

public class ActivityMap extends Activity {

    private Integer id;
    private Uri jsonFileStyleURI; //A json description for the map style, set as an option with the setup of the map
    private ArrayList<Location> pointsOfInterest;
    private LatLng defaultLocation; // The center of the map when the app is started
    private double zoom; // The default zoom

    public ActivityMap(Integer id, String name,Class associatedClass) {
        super(id, name, associatedClass);
    }


    public ActivityMap(Integer id, String name, Class associatedClass, Uri jsonFileStyle, LatLng defaultLocation, double zoom) {
        super(id,name,associatedClass);
        this.id = id;
        this.jsonFileStyleURI = jsonFileStyle;
        this.defaultLocation = defaultLocation;
        this.zoom = zoom;
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

    public LatLng getDefaultLocation() {
        return defaultLocation;
    }

    public void setDefaultLocation(LatLng defaultLocation) {
        this.defaultLocation = defaultLocation;
    }

    public double getZoom() {
        return zoom;
    }

    public void setZoom(double zoom) {
        this.zoom = zoom;
    }
}
