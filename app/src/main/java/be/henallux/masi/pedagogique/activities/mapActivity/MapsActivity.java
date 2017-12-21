package be.henallux.masi.pedagogique.activities.mapActivity;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import be.henallux.masi.pedagogique.R;
import be.henallux.masi.pedagogique.model.Location;
import be.henallux.masi.pedagogique.utils.Constants;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private IHistoryMapRepository repository;
    private int activityID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        repository = new SQLiteHistoryMapRepository(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        //Retrieves the ID of the ActivityMapBase that is currently active
        //This ID will be used later to show the corresponding interest points on the map

        activityID = getIntent().getExtras().getInt(Constants.ACTIVITY_ID);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        // Add a marker in Sydney and move the camera
        for(Location l : repository.getAllPointsOfInterestById(activityID)){
            LatLng position = l.getLocation();
            mMap.addMarker(new MarkerOptions().position(position).title(l.getTitle()));
        }
    }
}
