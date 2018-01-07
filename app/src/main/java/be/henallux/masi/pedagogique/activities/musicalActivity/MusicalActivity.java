package be.henallux.masi.pedagogique.activities.musicalActivity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import be.henallux.masi.pedagogique.R;
import be.henallux.masi.pedagogique.activities.QuestionnaireActivity;
import be.henallux.masi.pedagogique.activities.mapActivity.IMapActivityRepository;
import be.henallux.masi.pedagogique.activities.mapActivity.Location;
import be.henallux.masi.pedagogique.activities.mapActivity.SQLiteMapActivityRepository;
import be.henallux.masi.pedagogique.activities.musicalActivity.makeMusic.Instrument;
import be.henallux.masi.pedagogique.dao.interfaces.IInstrumentRepository;
import be.henallux.masi.pedagogique.dao.sqlite.SQLiteInstrumentRepository;
import be.henallux.masi.pedagogique.model.Group;
import be.henallux.masi.pedagogique.model.Questionnaire;
import be.henallux.masi.pedagogique.utils.Constants;

public class MusicalActivity extends AppCompatActivity {

    private IMapActivityRepository mapRepository = new SQLiteMapActivityRepository(this);
    private IInstrumentRepository instrumentRepository = new SQLiteInstrumentRepository(this);
    private Context context;
    private Instrument instrument;
    private Group currentGroup;
    private Questionnaire questionnaire;
    protected ArrayList<Location> locationsClick = new ArrayList<Location>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_musical);

        context = getApplicationContext();

        final Location clickedLocation = getIntent().getExtras().getParcelable(Constants.KEY_LOCATION_CLICKED);

        currentGroup = getIntent().getExtras().getParcelable(Constants.KEY_CURRENT_GROUP);

        instrument = instrumentRepository.getInstrumentOfLocation(clickedLocation.getId());

        TextView hello = (TextView) findViewById(R.id.TextHello);
        hello.setText(getResources().getString(R.string.musical_welcome_title) + " " + clickedLocation.getTitle());

        TextView description = (TextView) findViewById(R.id.descriptionText);
        description.setText(instrument.getDescription());

        ImageView imgmusic = (ImageView) findViewById(R.id.imageInstrument);
        Picasso.with(context).load(instrument.getImagePath()).into(imgmusic);

        Button question = (Button) findViewById(R.id.questionnaireButton);

        locationsClick.add(clickedLocation);

        question.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(context, QuestionnaireActivity.class);
                intent.putExtra(Constants.KEY_LOCATIONS_CHOSEN,locationsClick);
                intent.putExtra(Constants.KEY_CURRENT_GROUP,currentGroup);
                startActivity(intent);
            }
        });
    }
}