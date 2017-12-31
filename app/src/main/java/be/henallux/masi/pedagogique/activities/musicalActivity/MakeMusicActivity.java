package be.henallux.masi.pedagogique.activities.musicalActivity;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import be.henallux.masi.pedagogique.R;
import be.henallux.masi.pedagogique.activities.musicalActivity.makeMusic.Instrument;
import be.henallux.masi.pedagogique.adapters.InstrumentListAdapter;
import be.henallux.masi.pedagogique.dao.interfaces.IInstrumentRepository;
import be.henallux.masi.pedagogique.dao.sqlite.SQLiteInstrumentRepository;

public class MakeMusicActivity extends AppCompatActivity {

    private RecyclerView instrumentRecyclerView;
    private RecyclerView.LayoutManager instrumentLayoutManager;
    private RecyclerView.Adapter instrumentListAdapter;
    private IInstrumentRepository instrumentRepository;
    private ArrayList<Instrument> instrumentArrayList;

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_music);

        instrumentRepository = new SQLiteInstrumentRepository(getApplicationContext());

        instrumentArrayList = instrumentRepository.getAllInstruments();

        instrumentRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_item_instruments);
        instrumentRecyclerView.setHasFixedSize(true);
        instrumentLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        instrumentRecyclerView.setLayoutManager(instrumentLayoutManager);
        instrumentListAdapter = new InstrumentListAdapter(getApplicationContext(), instrumentArrayList);
        instrumentRecyclerView.setAdapter(instrumentListAdapter);



    }






}
