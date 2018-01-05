package be.henallux.masi.pedagogique.activities.musicalActivity.makeMusic.handlers;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.HashMap;
import java.util.Map;

import be.henallux.masi.pedagogique.activities.mapActivity.Location;

/**
 * Created by hendrikdebeuf2 on 5/01/18.
 */

public class MapChangeHandler implements IMapChangeHandler {

    private GoogleMap mMap;
    private HashMap<Marker,Location> hashMapMarkersLocation;
    private LatLng position;
    private Marker marker;
    private Marker goodMarker;
    private Location location;

    public MapChangeHandler(GoogleMap mMap, HashMap<Marker,Location> hashMapMarkersLocation) {
        this.mMap = mMap;
        this.hashMapMarkersLocation = hashMapMarkersLocation;
    }

    public void getPosition(int locationId){
        for (Map.Entry<Marker,Location> entry : hashMapMarkersLocation.entrySet()){
            marker = entry.getKey();
            location = entry.getValue();
            if (locationId == location.getId()) {
                position = location.getLocation();
                goodMarker = marker;
            }
        }
    }

    public void moveToPosition (int locationId) {
        getPosition(locationId);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(position));
        goodMarker.showInfoWindow();
    }

}
