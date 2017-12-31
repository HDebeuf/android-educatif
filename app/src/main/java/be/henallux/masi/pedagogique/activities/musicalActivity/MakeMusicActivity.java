package be.henallux.masi.pedagogique.activities.musicalActivity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

import be.henallux.masi.pedagogique.R;
import be.henallux.masi.pedagogique.activities.musicalActivity.makeMusic.IMediaPlayerAdapter;
import be.henallux.masi.pedagogique.activities.musicalActivity.makeMusic.Instrument;
import be.henallux.masi.pedagogique.activities.musicalActivity.makeMusic.MediaPlayerAdapter;
import be.henallux.masi.pedagogique.adapters.InstrumentListAdapter;
import be.henallux.masi.pedagogique.dao.interfaces.IInstrumentRepository;
import be.henallux.masi.pedagogique.dao.sqlite.SQLiteInstrumentRepository;

public class MakeMusicActivity extends AppCompatActivity {

    private RecyclerView instrumentRecyclerView;
    private RecyclerView.LayoutManager instrumentLayoutManager;
    private RecyclerView.Adapter instrumentListAdapter;
    private IInstrumentRepository instrumentRepository;
    private ArrayList<Instrument> instrumentArrayList;
    private Context context;
    private IMediaPlayerAdapter playerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_music);

        context = getApplicationContext();

        instrumentRepository = new SQLiteInstrumentRepository(getApplicationContext());

        instrumentArrayList = instrumentRepository.getAllInstruments();

        instrumentRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_item_instruments);
        instrumentRecyclerView.setHasFixedSize(true);
        instrumentLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        instrumentRecyclerView.setLayoutManager(instrumentLayoutManager);
        instrumentListAdapter = new InstrumentListAdapter(getApplicationContext(), instrumentArrayList);
        instrumentRecyclerView.setAdapter(instrumentListAdapter);

        initializeMediaUI();
        //initializeSeekbar();
        initializePlaybackController();


    }

    @Override
    protected void onStart() {
        super.onStart();
        //playerAdapter.loadMedia();
        Log.d("mediainfo", "onStart: create MediaPlayer");
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isChangingConfigurations() && playerAdapter.isPlaying()) {
            Log.d("mediainfo", "onStop: don't release MediaPlayer as screen is rotating & playing");
        } else {
            playerAdapter.release();
            Log.d("mediainfo", "onStop: release MediaPlayer");
        }
    }

    private void initializeMediaUI() {

        // TO DO Player for final music

        /*holder.instrumentImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TO DO manage samples
                playerAdapter.play();
                //playAudio(String.valueOf(instrumentArrayList.get(position).getSamplePath()));
            }
        });*/
    }

    private void initializePlaybackController() {
        MediaPlayerAdapter mediaPlayerAdapter = new MediaPlayerAdapter(context);
        Log.d("mediainfo", "initializePlaybackController: created MediaPlayerHolder");
        //mediaPlayerAdapter.setPlaybackInfoListener(new PlaybackListener());
        playerAdapter = mediaPlayerAdapter;
        Log.d("mediainfo", "initializePlaybackController: MediaPlayerHolder progress callback set");
    }

    /*
    private void initializeSeekbar() {
        mSeekbarAudio.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    int userSelectedPosition = 0;

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        mUserIsSeeking = true;
                    }

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser) {
                            userSelectedPosition = progress;
                        }
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        mUserIsSeeking = false;
                        mPlayerAdapter.seekTo(userSelectedPosition);
                    }
                });
    }

    public class PlaybackListener extends PlaybackInfoListener {

        @Override
        public void onDurationChanged(int duration) {
            mSeekbarAudio.setMax(duration);
            Log.d(TAG, String.format("setPlaybackDuration: setMax(%d)", duration));
        }

        @Override
        public void onPositionChanged(int position) {
            if (!mUserIsSeeking) {
                mSeekbarAudio.setProgress(position, true);
                Log.d(TAG, String.format("setPlaybackPosition: setProgress(%d)", position));
            }
        }

        @Override
        public void onStateChanged(@State int state) {
            String stateToString = PlaybackInfoListener.convertStateToString(state);
            onLogUpdated(String.format("onStateChanged(%s)", stateToString));
        }

        @Override
        public void onPlaybackCompleted() {
        }

        @Override
        public void onLogUpdated(String message) {
            if (mTextDebug != null) {
                mTextDebug.append(message);
                mTextDebug.append("\n");
                // Moves the scrollContainer focus to the end.
                mScrollContainer.post(
                        new Runnable() {
                            @Override
                            public void run() {
                                mScrollContainer.fullScroll(ScrollView.FOCUS_DOWN);
                            }
                        });
            }
        }
    }

*/

}
