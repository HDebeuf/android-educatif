package be.henallux.masi.pedagogique.activities.musicalActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.io.IOException;
import java.util.ArrayList;

import be.henallux.masi.pedagogique.R;
import be.henallux.masi.pedagogique.activities.musicalActivity.makeMusic.handlers.IMediaPlayerHandler;
import be.henallux.masi.pedagogique.activities.musicalActivity.makeMusic.Instrument;
import be.henallux.masi.pedagogique.activities.musicalActivity.makeMusic.handlers.IMediaRecorderHandler;
import be.henallux.masi.pedagogique.activities.musicalActivity.makeMusic.handlers.MediaPlayerHandler;
import be.henallux.masi.pedagogique.activities.musicalActivity.makeMusic.handlers.MediaRecorderHandler;
import be.henallux.masi.pedagogique.adapters.InstrumentListAdapter;
import be.henallux.masi.pedagogique.dao.interfaces.IInstrumentRepository;
import be.henallux.masi.pedagogique.dao.sqlite.SQLiteInstrumentRepository;
import be.henallux.masi.pedagogique.model.Activity;
import be.henallux.masi.pedagogique.utils.IPermissionsHandler;
import be.henallux.masi.pedagogique.utils.PermissionsHandler;

public class MakeMusicActivity extends AppCompatActivity {

    private RecyclerView instrumentRecyclerView;
    private RecyclerView.LayoutManager instrumentLayoutManager;
    private RecyclerView.Adapter instrumentListAdapter;
    private ArrayList<Instrument> instrumentArrayList;
    private Context context;

    private IInstrumentRepository instrumentRepository;
    private IMediaPlayerHandler playerHandler;
    private IMediaRecorderHandler recorderHandler;
    private IPermissionsHandler permissionHandler;

    private FloatingActionButton recButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_music);
        recButton = (FloatingActionButton) findViewById(R.id.recButton);

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

        recorderHandler = new MediaRecorderHandler(context);
        permissionHandler = new PermissionsHandler();

        recButton.setOnClickListener(new View.OnClickListener() {
            int i = 0;
            @Override
            public void onClick (View view){

                if (!permissionHandler.isStoragePermissionGranted(MakeMusicActivity.this,context) || !permissionHandler.isAudioRecordPermissionGranted(MakeMusicActivity.this, context)) {
                    permissionHandler.requestPermissions(MakeMusicActivity.this);
                } else if (permissionHandler.isStoragePermissionGranted(MakeMusicActivity.this, context) && permissionHandler.isAudioRecordPermissionGranted(MakeMusicActivity.this, context)) {
                    i++;
                    if (i % 2 != 0) {
                        try {
                            Log.d("recordinfo", "rec");
                            recorderHandler.startRecording(view);
                            recordBlink(recButton, i);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        recorderHandler.stopRecording(view);
                        recButton.setImageResource(R.drawable.ic_rec);
                    }
                }



            }
        });
    }

    private void recordBlink (final FloatingActionButton recButton, int i){

        final int []imageArray={R.drawable.ic_rec2,R.drawable.ic_rec};

        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            int i=0;
            public void run() {
                boolean recordStatus = recorderHandler.getRecordStatus();
                if (recordStatus){
                    recButton.setImageResource(imageArray[i]);
                    i++;
                    if (i > imageArray.length - 1) {
                        i = 0;
                    }
                    handler.postDelayed(this, 500);  //for interval...
                }
            }
        };
        handler.postDelayed(runnable, 2000); //for initial delay..


    /*here the button click counter start */

    }

    @Override
    protected void onStart() {
        super.onStart();
        //playerHandler.loadMedia();
        Log.d("mediainfo", "onStart: create MediaPlayer");
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isChangingConfigurations() && playerHandler.isPlaying()) {
            Log.d("mediainfo", "onStop: don't release MediaPlayer as screen is rotating & playing");
        } else {
            playerHandler.release();
            Log.d("mediainfo", "onStop: release MediaPlayer");
        }
    }

    private void initializeMediaUI() {

        // TO DO Player for final music

        /*holder.instrumentImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TO DO manage samples
                playerHandler.play();
                //playAudio(String.valueOf(instrumentArrayList.get(position).getSampleFileName()));
            }
        });*/
    }

    private void initializePlaybackController() {
        MediaPlayerHandler mediaPlayerAdapter = new MediaPlayerHandler(context);
        Log.d("mediainfo", "initializePlaybackController: created MediaPlayerHolder");
        //mediaPlayerAdapter.setPlaybackInfoListener(new PlaybackListener());
        playerHandler = mediaPlayerAdapter;
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
