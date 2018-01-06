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

import be.henallux.masi.pedagogique.R;
import be.henallux.masi.pedagogique.activities.QuestionnaireActivity;
import be.henallux.masi.pedagogique.activities.mapActivity.IMapActivityRepository;
import be.henallux.masi.pedagogique.activities.mapActivity.Location;
import be.henallux.masi.pedagogique.activities.mapActivity.SQLiteMapActivityRepository;
import be.henallux.masi.pedagogique.activities.musicalActivity.makeMusic.Instrument;
import be.henallux.masi.pedagogique.dao.interfaces.IInstrumentRepository;
import be.henallux.masi.pedagogique.dao.sqlite.SQLiteInstrumentRepository;
import be.henallux.masi.pedagogique.utils.Constants;

public class MusicalActivity extends AppCompatActivity {

    private IMapActivityRepository repository = new SQLiteMapActivityRepository(this);
    private IInstrumentRepository instrumentRepository;
    private Instrument instrument = new Instrument();
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musical);

        context = getApplicationContext();

        int idLocationClicked = getIntent().getExtras().getInt(Constants.KEY_LOCATION_CLICKED);
        Location clickedLocation = repository.getLocationById(idLocationClicked);

        instrumentRepository = new SQLiteInstrumentRepository(getApplicationContext());
        instrument = instrumentRepository.getOneInstrument(clickedLocation.getId());

        TextView hello = (TextView) findViewById(R.id.TextHello);
        hello.setText("Bonjour et Bienvenue en " + clickedLocation.getTitle());

        TextView description = (TextView) findViewById(R.id.descriptionText);
        description.setText(instrument.getDescription());

        ImageView imgmusic = (ImageView) findViewById(R.id.imageInstrument);
        Picasso.with(context).load(instrument.getImagePath()).into(imgmusic);

        Button question = (Button) findViewById(R.id.questionnaireButton);
        question.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(context, QuestionnaireActivity.class);
                startActivity(intent);
            }

        });
    }
}