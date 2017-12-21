package be.henallux.masi.pedagogique.activities.historyActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import be.henallux.masi.pedagogique.R;
import be.henallux.masi.pedagogique.activities.mapActivity.IMapActivityRepository;
import be.henallux.masi.pedagogique.activities.mapActivity.Location;
import be.henallux.masi.pedagogique.activities.mapActivity.SQLiteMapActivityRepository;
import be.henallux.masi.pedagogique.utils.Constants;

public class LocationInfoActivity extends AppCompatActivity {

    private IMapActivityRepository repository = new SQLiteMapActivityRepository(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_location_info);
        int idLocationClicked = getIntent().getExtras().getInt(Constants.KEY_LOCATION_CLICKED);
        Location clickedLocation = repository.getLocationById(idLocationClicked);
    }
}
