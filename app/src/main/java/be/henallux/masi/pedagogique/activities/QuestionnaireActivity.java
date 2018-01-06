package be.henallux.masi.pedagogique.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import be.henallux.masi.pedagogique.R;
import be.henallux.masi.pedagogique.activities.mapActivity.IMapActivityRepository;
import be.henallux.masi.pedagogique.activities.mapActivity.Location;
import be.henallux.masi.pedagogique.activities.mapActivity.SQLiteMapActivityRepository;
import be.henallux.masi.pedagogique.activities.musicalActivity.makeMusic.Instrument;
import be.henallux.masi.pedagogique.dao.interfaces.IQuestionnaireRepository;
import be.henallux.masi.pedagogique.utils.Constants;

/**
 * Created by Angele on 04/01/2018.
 */

public class QuestionnaireActivity extends AppCompatActivity {
    private IMapActivityRepository repository = new SQLiteMapActivityRepository(this);
    private IQuestionnaireRepository questionnaireRepository;
    private Instrument instrument = new Instrument();
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musical);

        context = getApplicationContext();

        int idLocationClicked = getIntent().getExtras().getInt(Constants.KEY_LOCATION_CLICKED);
        Location clickedLocation = repository.getLocationById(idLocationClicked);

    }
}
