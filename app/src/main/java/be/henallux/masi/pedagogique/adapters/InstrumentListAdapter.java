package be.henallux.masi.pedagogique.adapters;

import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import be.henallux.masi.pedagogique.R;
import be.henallux.masi.pedagogique.activities.musicalActivity.MusicalActivity;
import be.henallux.masi.pedagogique.activities.musicalActivity.makeMusic.Instrument;
import be.henallux.masi.pedagogique.utils.Constants;

/**
 * Created by hendrikdebeuf2 on 30/12/17.
 */

public class InstrumentListAdapter extends RecyclerView.Adapter<InstrumentListAdapter.ViewHolder>{

    private Context context;
    private ArrayList<Instrument> instrumentArrayList;
    private SoundPool soundPool;
    private int soundID;
    private boolean loaded;

    public InstrumentListAdapter(Context context, ArrayList<Instrument> instrumentArrayList) {
        this.context = context;
        this.instrumentArrayList = instrumentArrayList;
        buildSoundPool();
    }

    @Override
    public InstrumentListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.single_instrument,parent,false);
        InstrumentListAdapter.ViewHolder holder = new InstrumentListAdapter.ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final InstrumentListAdapter.ViewHolder holder, final int position) {
        if(position % 2 == 0){
            holder.instrumentImage.setBackgroundColor(ContextCompat.getColor(context,R.color.recyclerview_background_1));
        }
        else
        {
            holder.instrumentImage.setBackgroundColor(ContextCompat.getColor(context,R.color.recyclerview_background_2));
        }

        holder.instrumentName.setText(instrumentArrayList.get(position).getName());
        Picasso.with(context).load(instrumentArrayList.get(position).getImagePath()).into(holder.instrumentImage);


        //player = MediaPlayer.create(context, instrumentArrayList.get(position).getSamplePath());

        holder.lockedImage.setImageResource(R.drawable.ic_locked);
        Log.d("mediainfo",String.valueOf(instrumentArrayList.get(position).isUnlocked()));
        Log.d("mediainfo",String.valueOf(instrumentArrayList.get(position).getSamplePath()));
        if (instrumentArrayList.get(position).isUnlocked()){
            holder.lockedImage.setVisibility(View.GONE);
            // Load the sound
            soundID = loadSample(instrumentArrayList.get(position).getSamplePath());
            holder.instrumentImage.setTag(soundID);
            holder.instrumentImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    playSample(Integer.valueOf(holder.instrumentImage.getTag().toString()));
                }
            });

        } else {
            holder.instrumentInfoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openContinentQuizz(instrumentArrayList.get(position).getLocationId());
                }
            });
        }

        holder.instrumentInfoButton.setImageResource(R.drawable.ic_info);
        holder.instrumentInfoButton.setId(instrumentArrayList.get(position).getLocationId());
        holder.instrumentInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openContinentQuizz(instrumentArrayList.get(position).getLocationId());
            }
        });

    }

    private void buildSoundPool(){
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();

        soundPool = new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .setMaxStreams(10)
                .build();
    }

    private int loadSample(Uri samplePath) {

        int resourceID = context.getResources().getIdentifier("djembe_sample","raw",context.getPackageName());

        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId,int status) {
                loaded = true;
            }
        });
        soundID = soundPool.load(context, resourceID,1);
        return soundID;
    }

    private void playSample(int soundID){
        if (loaded) {
            soundPool.play(soundID, 1,1,1,0,1f);
            Log.e("Test", "Played sound");
        }
    }


    @Override
    public int getItemCount() {
        return instrumentArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView instrumentName;
        ImageView instrumentImage;
        ImageView lockedImage;
        ImageButton instrumentInfoButton;

        public ViewHolder (View itemView){
            super(itemView);
            instrumentName = (TextView) itemView.findViewById(R.id.instrumentName);
            instrumentImage = (ImageView) itemView.findViewById(R.id.instrumentImage);
            instrumentInfoButton = (ImageButton) itemView.findViewById(R.id.informationButton);
            lockedImage = itemView.findViewById(R.id.locked);

        }

    }

    private void openContinentQuizz (int locationId){
        Intent intent = new Intent(context, MusicalActivity.class);
        intent.putExtra(Constants.KEY_LOCATION_CLICKED,locationId);
        context.startActivity(intent);
    }

    /*public class PlaybackListener extends PlaybackInfoListener {

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
    }*/

    /*
    private void playAudio(String media) {
        //Check is service is active
        if (!serviceBound) {
            Intent playerIntent = new Intent(context, MediaPlayerService.class);
            playerIntent.putExtra("media", media);
            context.startService(playerIntent);
            context.bindService(playerIntent, MakeMusicActivity.serviceConnection, Context.BIND_AUTO_CREATE);
        } else {
            //Service is active
            //Send media with BroadcastReceiver
        }
        */
}

