package be.henallux.masi.pedagogique.activities.musicalActivity.makeMusic.handlers;

import android.view.View;

import java.io.IOException;

/**
 * Created by hendrikdebeuf2 on 1/01/18.
 */

public interface IMediaRecorderHandler {

    void startRecording(View view) throws IOException;

    void stopRecording(View view);

    boolean getRecordStatus();

}
