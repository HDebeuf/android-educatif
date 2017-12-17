package be.henallux.masi.pedagogique.model;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Le Roi Arthur on 17-12-17.
 * A point of interest on a map
 */

public class Location {
    private String title;
    private LatLng location;

    public Location(String title, LatLng location) {
        this.title = title;
        this.location = location;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }
}
