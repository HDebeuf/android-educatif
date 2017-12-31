package be.henallux.masi.pedagogique.activities.musicalActivity.makeMusic;

import android.net.Uri;

/**
 * Created by hendrikdebeuf2 on 31/12/17.
 */

public interface IMediaPlayerAdapter {

    // Implements PlaybackControl.
    void loadMedia(Uri sampleUri);

    void release();

    boolean isPlaying();

    void play();

    void reset();

    void pause();

    void initializeProgressCallback();

    void seekTo(int position);

}
