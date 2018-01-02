package be.henallux.masi.pedagogique.activities.musicalActivity.makeMusic.handlers;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.net.rtp.AudioStream;
import android.util.Log;

import be.henallux.masi.pedagogique.activities.musicalActivity.makeMusic.handlers.ISoundPoolHandler;

/**
 * Created by hendrikdebeuf2 on 1/01/18.
 */

public class SoundPoolHandler implements ISoundPoolHandler {

    private Context context;
    private SoundPool soundPool;
    private int soundID;
    private boolean loaded;

    public SoundPoolHandler(Context context) {
        this.context = context;
    }

    public void buildSoundPool(){
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();

        soundPool = new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .setMaxStreams(10)
                .build();
    }

    public int loadSample(String fileName, String fileType) {

        int resourceID = context.getResources().getIdentifier(fileName,fileType,context.getPackageName());

        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId,int status) {
                loaded = true;
            }
        });
        soundID = soundPool.load(context, resourceID,1);
        return soundID;
    }

    public void playSample(int soundID){
        if (loaded) {
            soundPool.play(soundID, 1,1,1,0,1f);
            Log.e("Test", "Played sound");
        }
    }
}
