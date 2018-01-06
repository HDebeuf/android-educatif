package be.henallux.masi.pedagogique.activities.musicalActivity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import be.henallux.masi.pedagogique.R;
import be.henallux.masi.pedagogique.activities.mapActivity.ActivityMapBase;
import be.henallux.masi.pedagogique.activities.mapActivity.IMapActivityRepository;
import be.henallux.masi.pedagogique.activities.mapActivity.Location;
import be.henallux.masi.pedagogique.activities.mapActivity.SQLiteMapActivityRepository;
import be.henallux.masi.pedagogique.model.Group;
import be.henallux.masi.pedagogique.utils.Constants;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MapMusicalActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private IMapActivityRepository repository;
    private ActivityMapBase activity;
    private Group currentGroup;
    private HashMap<Marker,Location> hashMapMarkersLocation = new HashMap<>();
    private ArrayList<Location> chosenLocations = new ArrayList<>();

    @BindView(R.id.floatingActionButtonMakeSong)
    FloatingActionButton buttonMakeSong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        hashMapMarkersLocation.clear();
        repository = new SQLiteMapActivityRepository(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_musical);

        currentGroup = getIntent().getExtras().getParcelable(Constants.KEY_CURRENT_GROUP);

        ButterKnife.bind(this);

        buttonMakeSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSongEditor();
            }
        });
        //Retrieves the ID of the ActivityMapBase that is currently active
        activity = getIntent().getExtras().getParcelable(Constants.ACTIVITY_KEY);

        chosenLocations.addAll(activity.getPointsOfInterest());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void openSongEditor() {
        Intent intent = new Intent(this, MakeMusicActivity.class);
        intent.putExtra(Constants.ACTIVITY_KEY,activity);
        intent.putExtra(Constants.KEY_CURRENT_GROUP,currentGroup);
        startActivity(intent);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(activity.getDefaultLocation(), (float)activity.getZoom()));


        if(activity.getJsonFileStyleURI() != null) {
            File file = new File(activity.getJsonFileStyleURI().getPath());
            int resourceId = getApplicationContext().getResources().getIdentifier(file.getName(), "raw", getPackageName());

            mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, resourceId));
        }

        for(Location l : activity.getPointsOfInterest()){
            LatLng position = l.getLocation();
            MarkerOptions opts = new MarkerOptions()
                    .position(position)
                    .title(l.getTitle());
            Marker m = mMap.addMarker(opts);


            hashMapMarkersLocation.put(m,l);
        }

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Location l = hashMapMarkersLocation.get(marker);
                Intent intent = new Intent(be.henallux.masi.pedagogique.activities.musicalActivity.MapMusicalActivity.this,l.getClassToThrow());
                intent.putExtra(Constants.KEY_LOCATION_CLICKED,l.getId());
                intent.putExtra(Constants.ACTIVITY_KEY,activity);
                intent.putExtra(Constants.KEY_CURRENT_GROUP,currentGroup);
                startActivity(intent);
                return true;
            }
        });
    }

    public ActivityMapBase getActivity() {
        return activity;
    }
}
