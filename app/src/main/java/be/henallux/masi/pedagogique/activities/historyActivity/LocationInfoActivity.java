package be.henallux.masi.pedagogique.activities.historyActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import be.henallux.masi.pedagogique.R;
import be.henallux.masi.pedagogique.activities.historyActivity.synthesis.Synthesis;
import be.henallux.masi.pedagogique.activities.mapActivity.IMapActivityRepository;
import be.henallux.masi.pedagogique.activities.mapActivity.Location;
import be.henallux.masi.pedagogique.activities.mapActivity.SQLiteMapActivityRepository;
import be.henallux.masi.pedagogique.dao.interfaces.ISynthesisRepository;
import be.henallux.masi.pedagogique.dao.sqlite.SQLSynthesisRepository;
import be.henallux.masi.pedagogique.utils.Constants;

public class LocationInfoActivity extends AppCompatActivity {

    private IMapActivityRepository mapRepository = new SQLiteMapActivityRepository(this);
    private ISynthesisRepository synthesisRepository = new SQLSynthesisRepository(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_location_info);
        int idLocationClicked = getIntent().getExtras().getInt(Constants.KEY_LOCATION_CLICKED);
        Location clickedLocation = mapRepository.getLocationById(idLocationClicked);
        ArrayList<Synthesis> synthesis =  synthesisRepository.getAllSynthesisOfLocation(clickedLocation);
    }
}
