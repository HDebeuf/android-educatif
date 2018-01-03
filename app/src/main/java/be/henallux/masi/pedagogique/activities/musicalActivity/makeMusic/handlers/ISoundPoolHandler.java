package be.henallux.masi.pedagogique.activities.musicalActivity.makeMusic.handlers;


/**
 * Created by hendrikdebeuf2 on 1/01/18.
 */

public interface ISoundPoolHandler {

    void buildSoundPool();

    int loadSample(String fileName, String fileType);

    void playSample(int soundID);

}
