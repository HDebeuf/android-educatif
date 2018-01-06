package be.henallux.masi.pedagogique.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import be.henallux.masi.pedagogique.R;
import be.henallux.masi.pedagogique.activities.mapActivity.IMapActivityRepository;
import be.henallux.masi.pedagogique.activities.mapActivity.Location;
import be.henallux.masi.pedagogique.activities.mapActivity.SQLiteMapActivityRepository;
import be.henallux.masi.pedagogique.activities.musicalActivity.makeMusic.Instrument;
import be.henallux.masi.pedagogique.dao.interfaces.IQuestionnaireRepository;
import be.henallux.masi.pedagogique.model.Questionnaire;
import be.henallux.masi.pedagogique.utils.Constants;

/**
 * Created by Angele on 04/01/2018.
 */

public class QuestionnaireActivity extends AppCompatActivity {
    private IMapActivityRepository repository = new SQLiteMapActivityRepository(this);
    private IQuestionnaireRepository questionnaireRepository;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musical_questionnaire);

        context = getApplicationContext();

        Questionnaire questionnaire = getIntent().getExtras().getParcelable(Constants.KEY_LOCATION_CLICKED);
        Log.i("ok","okl");
    }
}
