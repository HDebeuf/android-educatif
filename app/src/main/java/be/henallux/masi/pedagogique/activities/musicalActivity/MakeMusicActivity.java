package be.henallux.masi.pedagogique.activities.musicalActivity;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.OnMapReadyCallback;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import be.henallux.masi.pedagogique.R;
import be.henallux.masi.pedagogique.activities.mapActivity.ActivityMapBase;
import be.henallux.masi.pedagogique.activities.mapActivity.IMapActivityRepository;
import be.henallux.masi.pedagogique.activities.mapActivity.Location;
import be.henallux.masi.pedagogique.activities.mapActivity.SQLiteMapActivityRepository;
import be.henallux.masi.pedagogique.activities.musicalActivity.makeMusic.RecordAudio;
import be.henallux.masi.pedagogique.activities.musicalActivity.makeMusic.handlers.IMediaPlayerHandler;
import be.henallux.masi.pedagogique.activities.musicalActivity.makeMusic.Instrument;
import be.henallux.masi.pedagogique.activities.musicalActivity.makeMusic.handlers.IMapChangeHandler;
import be.henallux.masi.pedagogique.activities.musicalActivity.makeMusic.handlers.IMediaRecorderHandler;
import be.henallux.masi.pedagogique.activities.musicalActivity.makeMusic.handlers.ISoundPoolHandler;
import be.henallux.masi.pedagogique.activities.musicalActivity.makeMusic.handlers.MapChangeHandler;
import be.henallux.masi.pedagogique.activities.musicalActivity.makeMusic.handlers.MediaPlayerHandler;
import be.henallux.masi.pedagogique.activities.musicalActivity.makeMusic.handlers.MediaRecorderHandler;
import be.henallux.masi.pedagogique.activities.musicalActivity.makeMusic.handlers.SoundPoolHandler;
import be.henallux.masi.pedagogique.adapters.InstrumentListAdapter;
import be.henallux.masi.pedagogique.dao.interfaces.IInstrumentRepository;
import be.henallux.masi.pedagogique.dao.sqlite.SQLiteInstrumentRepository;
import be.henallux.masi.pedagogique.model.Group;
import be.henallux.masi.pedagogique.model.User;
import be.henallux.masi.pedagogique.utils.Constants;
import be.henallux.masi.pedagogique.utils.IMailSender;
import be.henallux.masi.pedagogique.utils.IPermissionsHandler;
import be.henallux.masi.pedagogique.utils.MailSender;
import be.henallux.masi.pedagogique.utils.PermissionsHandler;

public class MakeMusicActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private IMapActivityRepository mapRepository;
    private ActivityMapBase activity;
    private Integer group;
    private HashMap<Marker,Location> hashMapMarkersLocation = new HashMap<>();
    private ArrayList<Location> chosenLocations = new ArrayList<>();

    private FloatingActionButton recButton;
    private TextView actualTimeView;
    private TextView reverseActualTimeView;
    private FloatingActionButton deleteButton;
    private FloatingActionButton saveButton;
    private FloatingActionButton playPauseButton;
    private SeekBar progressBar;
    private Context context;
    private Handler recordBlinkHandler;

    private IInstrumentRepository instrumentRepository;
    private IMediaPlayerHandler playerHandler;
    private IMediaRecorderHandler recorderHandler;
    private IPermissionsHandler permissionHandler;
    private ISoundPoolHandler soundPoolHandler;
    private IMapChangeHandler mapChangeHandler;
    private IMailSender mailSender;

    private RecordAudio recordAudioFile;
    private int maxDuration;
    private boolean userIsSeeking = false;
    private Group currentGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        hashMapMarkersLocation.clear();
        mapRepository = new SQLiteMapActivityRepository(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_music);

        currentGroup = getIntent().getExtras().getParcelable(Constants.KEY_CURRENT_GROUP);
        ArrayList<User> teamMembers = currentGroup.getMembers();
        final StringBuilder teamMembersString = new StringBuilder();
        for (User user: teamMembers) {
            teamMembersString.append(user.getFirstName() + " " + user.getLastName() + ", ");
        }

        activity = getIntent().getExtras().getParcelable(Constants.ACTIVITY_KEY);
        chosenLocations.addAll(activity.getPointsOfInterest());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        recButton = (FloatingActionButton) findViewById(R.id.recButton);
        actualTimeView = (TextView) findViewById(R.id.currentTime);
        reverseActualTimeView = (TextView) findViewById(R.id.endTime);
        deleteButton = (FloatingActionButton) findViewById(R.id.deleteButton);
        saveButton = (FloatingActionButton) findViewById(R.id.saveButton);
        playPauseButton = (FloatingActionButton) findViewById(R.id.playPauseButton);
        progressBar = (SeekBar) findViewById(R.id.progressBar);

        context = getApplicationContext();

        soundPoolHandler = new SoundPoolHandler(context);
        instrumentRepository = new SQLiteInstrumentRepository(getApplicationContext());

        initializeProgressBar();

        recordAudioFile = new RecordAudio();
        recorderHandler = new MediaRecorderHandler(context, recordAudioFile);
        permissionHandler = new PermissionsHandler();
        playerHandler = new MediaPlayerHandler(context);
        recordBlinkHandler = new Handler();

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
                        playerHandler.loadMedia(Uri.parse(recordAudioFile.getFilePath()));
                        Log.d("soundtoto",String.valueOf(soundPoolHandler.getStreamSoundIdList()));
                        new StopSoundpool().execute(soundPoolHandler.getStreamSoundIdList());

                        recButton.setImageResource(R.drawable.ic_rec);
                        actualTimeView.setText("0:00");
                        reverseActualTimeView.setText("0:30");
                        progressBar.setProgress(0);
                        recButton.setVisibility(View.INVISIBLE);
                        deleteButton.setVisibility(View.VISIBLE);
                        playPauseButton.setVisibility(View.VISIBLE);
                        saveButton.setVisibility(View.VISIBLE);
                    }

                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playerHandler.reset();
                recordAudioFile.getAudioFile().delete();

                deleteButton.setVisibility(View.GONE);
                playPauseButton.setVisibility(View.GONE);
                saveButton.setVisibility(View.GONE);
                recButton.setVisibility(View.VISIBLE);
                actualTimeView.setText("0:00");
                reverseActualTimeView.setText("0:30");
                progressBar.setProgress(0);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playerHandler.reset();
                mailSender = new MailSender(context);
                mailSender.sendMailFile(R.string.group + currentGroup.getId().toString(), String.valueOf(R.string.email_music_subject), R.string.email_body_start + teamMembersString.toString() + R.string.email_body_end, recordAudioFile.getFileName() + ".mp4");

                deleteButton.setVisibility(View.GONE);
                playPauseButton.setVisibility(View.GONE);
                saveButton.setVisibility(View.GONE);
                recButton.setVisibility(View.VISIBLE);
                actualTimeView.setText("0:00");
                reverseActualTimeView.setText("0:30");
                progressBar.setProgress(0);
            }
        });

        playPauseButton.setOnClickListener(new View.OnClickListener() {
            int j = 0;

            @Override
            public void onClick(View view) {
                j++;
                if (j % 2 != 0) {
                    playerHandler.play();
                    new PlayerAdvancement().execute();
                    playPauseButton.setImageResource(R.drawable.ic_pause_24dp);
                } else {
                    playerHandler.pause();
                    playPauseButton.setImageResource(R.drawable.ic_play_arrow_24dp);
                }

            }
        });
    }

    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(activity.getDefaultLocation(), (float)activity.getZoom()));


        if(activity.getJsonFileStyleURI() != null) {
            File file = new File(activity.getJsonFileStyleURI().getPath());
            int resourceId = getApplicationContext().getResources().getIdentifier(file.getName(), "raw", getPackageName());

            mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, resourceId));
        }

        for(Location l : activity.getPointsOfInterest()){
            LatLng position = l.getLocation();
            MarkerOptions opts = new MarkerOptions()
                    .position(position)
                    .title(l.getTitle());
            Marker m = mMap.addMarker(opts);

            hashMapMarkersLocation.put(m,l);
            mapChangeHandler = new MapChangeHandler(mMap, hashMapMarkersLocation);
        }
        recyclerViewLoader();
    }

    public void recyclerViewLoader(){

        final RecyclerView instrumentRecyclerView = findViewById(R.id.recyclerview_item_instruments);
        instrumentRecyclerView.setHasFixedSize(true);
        final RecyclerView.LayoutManager instrumentLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        instrumentRecyclerView.setLayoutManager(instrumentLayoutManager);

        ArrayList<Instrument> unlockedInstruments = instrumentRepository.getInstrumentsOfGroup(currentGroup);
        ArrayList<Instrument> allInstruments = instrumentRepository.getAllInstruments();
        RecyclerView.Adapter instrumentListAdapter = new InstrumentListAdapter(context, allInstruments,unlockedInstruments, (SoundPoolHandler) soundPoolHandler, mapChangeHandler);
        instrumentRecyclerView.setAdapter(instrumentListAdapter);
    }

    private void recordBlink(final FloatingActionButton recButton) {

        final int[] imageArray = {R.drawable.ic_rec2, R.drawable.ic_rec};

        Runnable runnable = new Runnable() {
            int i = 0;

            public void run() {
                if (recorderHandler.getRecordStatus()) {
                    Log.d("record", String.valueOf(recordAudioFile.getActualTimeMs()));

                    recButton.setImageResource(imageArray[i]);
                    i++;
                    if (i > imageArray.length - 1) {
                        i = 0;
                    }
                    recordBlinkHandler.postDelayed(this, 500);  //for interval...
                } else {
                    return;
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
                if (recorderHandler.getRecordStatus()){
                    recorderHandler.stopRecording();
                    new StopSoundpool().execute(soundPoolHandler.getStreamSoundIdList());

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
        }.start();
    }

    private class PlayerAdvancement extends AsyncTask<RecordAudio, Void, Void> {

        @Override
        protected Void doInBackground(RecordAudio... recordAudios) {
            while (playerHandler.isPlaying()) {
                publishProgress();
            }
            cancel(!playerHandler.isPlaying());
            return null;
        }


        protected void onProgressUpdate(Void... voids) {
            int duration = playerHandler.getDuration();
            int currentPosition = playerHandler.getCurrentPosition();
            String timer = milliSecondsToTimer(currentPosition);
            String reverseTimer = milliSecondsToTimer(duration - currentPosition);

            progressBar.setMax(duration);
            progressBar.setProgress(currentPosition);
            actualTimeView.setText(timer);
            reverseActualTimeView.setText(reverseTimer);
            Log.d("tototo",timer);

        }

    }

    private class StopSoundpool extends AsyncTask<ArrayList<Integer>, Integer, Void> {

        protected Void doInBackground(ArrayList<Integer>... streamSoundIdList) {
            for (int streamSoundId:streamSoundIdList[0]) {
                publishProgress(streamSoundId);
            }
            cancel(true);
            return null;
        }

        protected void onProgressUpdate(Integer... streamSoundId) {
            soundPoolHandler.stop(streamSoundId[0]);
        }

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

