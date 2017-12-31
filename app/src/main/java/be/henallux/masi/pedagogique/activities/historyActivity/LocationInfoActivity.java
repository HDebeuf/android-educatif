package be.henallux.masi.pedagogique.activities.historyActivity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import be.henallux.masi.pedagogique.R;
import be.henallux.masi.pedagogique.activities.historyActivity.synthesis.Synthesis;
import be.henallux.masi.pedagogique.activities.mapActivity.IMapActivityRepository;
import be.henallux.masi.pedagogique.activities.mapActivity.Location;
import be.henallux.masi.pedagogique.activities.mapActivity.SQLiteMapActivityRepository;
import be.henallux.masi.pedagogique.dao.interfaces.ISynthesisRepository;
import be.henallux.masi.pedagogique.dao.sqlite.SQLSynthesisRepository;
import be.henallux.masi.pedagogique.utils.Constants;
import butterknife.BindView;
import butterknife.ButterKnife;

public class LocationInfoActivity extends AppCompatActivity {

    @BindView(R.id.floatingActionButtonAddLocation)
    public FloatingActionButton floatingActionButtonAdd;
    private IMapActivityRepository mapRepository = new SQLiteMapActivityRepository(this);
    private ISynthesisRepository synthesisRepository = new SQLSynthesisRepository(this);
    private Location clickedLocation;
    private boolean isInDeleteMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_info);

        ButterKnife.bind(this);

        clickedLocation = getIntent().getExtras().getParcelable(Constants.KEY_LOCATION_CLICKED);
        isInDeleteMode = getIntent().getExtras().getBoolean(Constants.KEY_IS_IN_DELETE_MODE);

        ArrayList<Synthesis> synthesis =  synthesisRepository.getAllSynthesisOfLocation(clickedLocation);

        for(Synthesis s : synthesis){
            Log.i("synthesis","Synthesis of type : " + s.getClass().getSimpleName() + " with text : " + s.getText());
        }

        if(isInDeleteMode)
            floatingActionButtonAdd.setImageResource(R.drawable.ic_delete);
        else
            floatingActionButtonAdd.setImageResource(R.drawable.ic_add_white_24dp);


        floatingActionButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra(Constants.RESULT_DATA_HAS_CHOOSEN_LOCATION, true);
                intent.putExtra(Constants.KEY_LOCATION_CLICKED, clickedLocation);
                intent.putExtra(Constants.KEY_IS_IN_DELETE_MODE,isInDeleteMode);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
