package be.henallux.masi.pedagogique.activities.musicalActivity.makeMusic.handlers;


import android.media.SoundPool;

import java.util.ArrayList;

/**
 * Created by hendrikdebeuf2 on 1/01/18.
 */

public interface ISoundPoolHandler {

    void buildSoundPool();

    int loadSample(String fileName, String fileType);

    void playSample(int soundID);

    SoundPool getSoundPool();

    void stop(int soundId);

    ArrayList<Integer> getStreamSoundIdList();

    void setStreamSoundIdList(ArrayList<Integer> streamSoundIdList);

}
