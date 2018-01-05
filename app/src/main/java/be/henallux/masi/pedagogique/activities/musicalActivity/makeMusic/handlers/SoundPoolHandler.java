package be.henallux.masi.pedagogique.activities.musicalActivity.makeMusic.handlers;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by hendrikdebeuf2 on 1/01/18.
 *
 * source: http://www.vogella.com/tutorials/AndroidMedia/article.html
 */

public class SoundPoolHandler implements ISoundPoolHandler {

    private Context context;
    private SoundPool soundPool;
    private boolean loaded;
    private int nbSounds;
    private int streamSoundId;
    private ArrayList<Integer> streamSoundIdList;

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
        streamSoundIdList = new ArrayList<Integer>();
    }

    public int loadSample(String fileName, String fileType) {

        int resourceID = context.getResources().getIdentifier(fileName,fileType,context.getPackageName());

        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId,int status) {
                loaded = true;
            }
        });
        nbSounds++;
        return soundPool.load(context, resourceID, 1);
    }

    public void playSample(int soundID){
        if (loaded) {
            streamSoundId = soundPool.play(soundID, 1,1,1,0,1f);
            streamSoundIdList.add(streamSoundId);
        }
    }

    public ArrayList<Integer> getStreamSoundIdList() {
        return this.streamSoundIdList;
    }

    public void setStreamSoundIdList(ArrayList<Integer> streamSoundIdList) {
        this.streamSoundIdList = streamSoundIdList;
    }

    public SoundPool getSoundPool() {
        return soundPool;
    }

    public void stop(int streamSoundId){
        try {
            soundPool.stop(streamSoundId);
        } catch (Exception e){
            streamSoundIdList.remove(streamSoundId);
        }

    }
}
