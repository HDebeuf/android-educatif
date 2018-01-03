package be.henallux.masi.pedagogique.activities.musicalActivity.makeMusic.handlers;

import android.content.Context;
import android.view.View;

import java.io.IOException;

import be.henallux.masi.pedagogique.activities.musicalActivity.makeMusic.RecordAudio;

/**
 * Created by hendrikdebeuf2 on 1/01/18.
 */

public interface IMediaRecorderHandler {

    void startRecording() throws IOException;

    void stopRecording();

    boolean getRecordStatus();

}
