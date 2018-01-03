package be.henallux.masi.pedagogique.activities.musicalActivity;

import android.content.Context;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

import be.henallux.masi.pedagogique.R;
import be.henallux.masi.pedagogique.activities.musicalActivity.makeMusic.RecordAudio;
import be.henallux.masi.pedagogique.activities.musicalActivity.makeMusic.handlers.IMediaPlayerHandler;
import be.henallux.masi.pedagogique.activities.musicalActivity.makeMusic.Instrument;
import be.henallux.masi.pedagogique.activities.musicalActivity.makeMusic.handlers.IMediaRecorderHandler;
import be.henallux.masi.pedagogique.activities.musicalActivity.makeMusic.handlers.ISoundPoolHandler;
import be.henallux.masi.pedagogique.activities.musicalActivity.makeMusic.handlers.MediaPlayerHandler;
import be.henallux.masi.pedagogique.activities.musicalActivity.makeMusic.handlers.MediaRecorderHandler;
import be.henallux.masi.pedagogique.adapters.InstrumentListAdapter;
import be.henallux.masi.pedagogique.dao.interfaces.IInstrumentRepository;
import be.henallux.masi.pedagogique.dao.sqlite.SQLiteInstrumentRepository;
import be.henallux.masi.pedagogique.utils.IPermissionsHandler;
import be.henallux.masi.pedagogique.utils.PermissionsHandler;

public class MakeMusicActivity extends AppCompatActivity {

    private FloatingActionButton recButton;
    private TextView actualTimeView;
    private TextView reverseActualTimeView;
    private FloatingActionButton deleteButton;
    private FloatingActionButton saveButton;
    private FloatingActionButton playPauseButton;
    private SeekBar progressBar;
    private Context context;
    private Handler recordBlinkHandler;
    private Handler playerAdvancementHandler;
    private Runnable runnable2;

    private IInstrumentRepository instrumentRepository;
    private IMediaPlayerHandler playerHandler;
    private IMediaRecorderHandler recorderHandler;
    private IPermissionsHandler permissionHandler;
    private ISoundPoolHandler soundPoolHandler;

    private RecordAudio recordAudioFile;
    private int maxDuration;
    private boolean userIsSeeking = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_music);

        recButton = (FloatingActionButton) findViewById(R.id.recButton);
        actualTimeView = (TextView) findViewById(R.id.currentTime);
        reverseActualTimeView = (TextView) findViewById(R.id.endTime);
        deleteButton = (FloatingActionButton) findViewById(R.id.deleteButton);
        saveButton = (FloatingActionButton) findViewById(R.id.saveButton);
        playPauseButton = (FloatingActionButton) findViewById(R.id.playPauseButton);
        progressBar = (SeekBar) findViewById(R.id.progressBar);

        context = getApplicationContext();

        instrumentRepository = new SQLiteInstrumentRepository(getApplicationContext());

        ArrayList<Instrument> instrumentArrayList = instrumentRepository.getAllInstruments();

        RecyclerView instrumentRecyclerView = findViewById(R.id.recyclerview_item_instruments);
        instrumentRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager instrumentLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        instrumentRecyclerView.setLayoutManager(instrumentLayoutManager);

        RecyclerView.Adapter instrumentListAdapter = new InstrumentListAdapter(getApplicationContext(), instrumentArrayList);
        instrumentRecyclerView.setAdapter(instrumentListAdapter);

        initializeProgressBar();

        recordAudioFile = new RecordAudio();
        recorderHandler = new MediaRecorderHandler(context, recordAudioFile);
        permissionHandler = new PermissionsHandler();
        playerHandler = new MediaPlayerHandler(context);
        recordBlinkHandler = new Handler();
        playerAdvancementHandler = new Handler();

        deleteButton.setVisibility(View.GONE);
        playPauseButton.setVisibility(View.GONE);
        saveButton.setVisibility(View.GONE);

        recButton.setOnClickListener(new View.OnClickListener() {
            int i = 0;

            @Override
            public void onClick(View view) {

                if (!permissionHandler.isStoragePermissionGranted(MakeMusicActivity.this, context) || !permissionHandler.isAudioRecordPermissionGranted(MakeMusicActivity.this, context)) {
                    permissionHandler.requestPermissions(MakeMusicActivity.this);
                } else if (permissionHandler.isStoragePermissionGranted(MakeMusicActivity.this, context) && permissionHandler.isAudioRecordPermissionGranted(MakeMusicActivity.this, context)) {
                    i++;
                    if (i % 2 != 0) {
                        try {
                            recorderHandler.startRecording();
                            maxDuration = recordAudioFile.getMaxDuration();
                            recordBlink(recButton);
                            recordAdvancement();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        recorderHandler.stopRecording();
                        playerAdvancement();
                        recButton.setImageResource(R.drawable.ic_rec);
                        actualTimeView.setText("0:00");
                        reverseActualTimeView.setText("0:30");
                        progressBar.setProgress(0);
                        recButton.setVisibility(View.INVISIBLE);
                        deleteButton.setVisibility(View.VISIBLE);
                        playPauseButton.setVisibility(View.VISIBLE);
                        saveButton.setVisibility(View.VISIBLE);
                        playerHandler.loadMedia(Uri.parse(recordAudioFile.getFilePath()));
                    }

                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playerAdvancementHandler.removeCallbacks(runnable2);
                playerHandler.reset();
                recordAudioFile.getAudioFile().delete();

                deleteButton.setVisibility(View.GONE);
                playPauseButton.setVisibility(View.GONE);
                saveButton.setVisibility(View.GONE);
                recButton.setVisibility(View.VISIBLE);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playerAdvancementHandler.removeCallbacks(runnable2);
                playerHandler.reset();
                recordAudioFile = new RecordAudio();

                deleteButton.setVisibility(View.GONE);
                playPauseButton.setVisibility(View.GONE);
                saveButton.setVisibility(View.GONE);
                recButton.setVisibility(View.VISIBLE);
            }
        });

        playPauseButton.setOnClickListener(new View.OnClickListener() {
            int j = 0;

            @Override
            public void onClick(View view) {
                j++;
                if (j % 2 != 0) {
                    playerHandler.play();
                    playerAdvancement();
                    playPauseButton.setImageResource(R.drawable.ic_pause_24dp);
                } else {
                    playerHandler.pause();
                    playPauseButton.setImageResource(R.drawable.ic_play_arrow_24dp);
                }

            }
        });
    }

    private void recordBlink(final FloatingActionButton recButton) {

        final int[] imageArray = {R.drawable.ic_rec2, R.drawable.ic_rec};

        Runnable runnable = new Runnable() {
            int i = 0;

            public void run() {
                boolean recordStatus = recorderHandler.getRecordStatus();
                if (recordStatus) {
                    Log.d("record", String.valueOf(recordAudioFile.getActualTimeMs()));

                    recButton.setImageResource(imageArray[i]);
                    i++;
                    if (i > imageArray.length - 1) {
                        i = 0;
                    }
                    recordBlinkHandler.postDelayed(this, 500);  //for interval...
                }
            }
        };
        recordBlinkHandler.postDelayed(runnable, 2000); //for initial delay..
    }

    private void recordAdvancement() {

        new CountDownTimer(maxDuration, 200) {

            public void onTick(long millisUntilFinished) {

                if (recorderHandler.getRecordStatus()) {
                    actualTimeView.setText(recordAudioFile.getActualTime());
                    reverseActualTimeView.setText(recordAudioFile.getReverseActualTime());
                    progressBar.setMax(maxDuration);
                    progressBar.setProgress(recordAudioFile.getActualTimeMs());
                } else {
                    cancel();
                }
            }

            public void onFinish() {
                actualTimeView.setText(recordAudioFile.getActualTime());
                reverseActualTimeView.setText(recordAudioFile.getReverseActualTime());
                progressBar.setProgress(recordAudioFile.getActualTimeMs());
                deleteButton.setVisibility(View.VISIBLE);
                playPauseButton.setVisibility(View.VISIBLE);
                saveButton.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    private void playerAdvancement() {

        int duration = playerHandler.getDuration();
        int currentPosition = playerHandler.getCurrentPosition();
        String timer = milliSecondsToTimer(currentPosition);
        String reverseTimer = milliSecondsToTimer(duration - currentPosition);
        progressBar.setMax(duration);
        progressBar.setProgress(currentPosition);
        actualTimeView.setText(timer);
        reverseActualTimeView.setText(reverseTimer);
        Log.d("tototo",timer);

        runnable2 = new Runnable() {
            public void run() {
                playerAdvancement();
            }
        };
        playerAdvancementHandler.postDelayed(runnable2, 200);
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

    /*
    private void initializePlaybackController() {
        MediaPlayerHandler playerHolder = new MediaPlayerHandler(this);
        Log.d("mediainfo", "initializePlaybackController: created MediaPlayerHandler");
        playerHolder.setPlaybackInfoListener(new PlaybackListener());
        playerHandler = playerHolder;
        Log.d("mediainfo", "initializePlaybackController: MediaPlayerHandler progress callback set");
    }
    */

    private void initializeProgressBar() {
        progressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int userSelectedPosition = 0;

            @Override
            public void onStartTrackingTouch(SeekBar progressBar) {
                userIsSeeking = true;
            }

            @Override
            public void onProgressChanged(SeekBar progressBar, int progress, boolean fromUser) {
                if (fromUser) {
                    userSelectedPosition = progress;
                }
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                userIsSeeking = false;
                playerHandler.seekTo(userSelectedPosition);
            }
        });
    }

    public String milliSecondsToTimer(long milliseconds){
        String finalTimerString = "";
        String secondsString = "";

        // Convert total duration into time
        int hours = (int)( milliseconds / (1000*60*60));
        int minutes = (int)(milliseconds % (1000*60*60)) / (1000*60);
        int seconds = (int) ((milliseconds % (1000*60*60)) % (1000*60) / 1000);
        // Add hours if there
        if(hours > 0){
            finalTimerString = hours + ":";
        }

        // Prepending 0 to seconds if it is one digit
        if(seconds < 10){
            secondsString = "0" + seconds;
        }else{
            secondsString = "" + seconds;}

        finalTimerString = finalTimerString + minutes + ":" + secondsString;

        return finalTimerString;
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();

    }
}

