package be.henallux.masi.pedagogique.activities.musicalActivity.makeMusic.handlers;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import be.henallux.masi.pedagogique.activities.musicalActivity.MakeMusicActivity;

import static android.support.v4.app.ShareCompat.getCallingActivity;

/**
 * Created by hendrikdebeuf2 on 1/01/18.
 */

public class MediaRecorderHandler implements IMediaRecorderHandler {

    private Context context;
    private MediaRecorder recorder;
    private File audiofile = null;
    private boolean recordStatus = false;

    public MediaRecorderHandler(Context context) {
        this.context = context;
    }

    public void startRecording(View view) throws IOException {

        File sampleDir = Environment.getExternalStorageDirectory();
        Log.d("mediaRecord", String.valueOf(sampleDir));

        try {
            audiofile = File.createTempFile("sound", ".mp4", sampleDir);
        } catch (IOException e) {
            Log.e("mediaRecord", "sdcard access error");
            Log.e("mediaRecord", String.valueOf(e));
        }
        recorder = new MediaRecorder();
        //recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        recorder.setOutputFile(audiofile.getAbsolutePath());
        recorder.setMaxDuration(30000);
        recorder.prepare();
        recorder.start();
        recordStatus = true;
    }

    public void stopRecording(View view) {
        //startButton.setEnabled(true);
        recorder.stop();
        recorder.release();
        addRecordingToMediaLibrary();
        recordStatus = false;
    }

    protected void addRecordingToMediaLibrary() {
        ContentValues values = new ContentValues(4);
        long current = System.currentTimeMillis();
        values.put(MediaStore.Audio.Media.TITLE, "audio" + audiofile.getName());
        values.put(MediaStore.Audio.Media.DATE_ADDED, (int) (current / 1000));
        values.put(MediaStore.Audio.Media.MIME_TYPE, "audio/mp4");
        values.put(MediaStore.Audio.Media.DATA, audiofile.getAbsolutePath());
        ContentResolver contentResolver = context.getContentResolver();

        Uri base = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Uri newUri = contentResolver.insert(base, values);

        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, newUri));
        Toast.makeText(context, "Added File " + newUri, Toast.LENGTH_LONG).show();
    }

    public boolean getRecordStatus() {
        return recordStatus;
    }
}
