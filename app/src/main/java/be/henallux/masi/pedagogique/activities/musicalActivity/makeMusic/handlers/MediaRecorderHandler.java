package be.henallux.masi.pedagogique.activities.musicalActivity.makeMusic.handlers;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.IOException;

import be.henallux.masi.pedagogique.activities.musicalActivity.makeMusic.RecordAudio;


/**
 * Created by hendrikdebeuf2 on 1/01/18.
 *
 * source: http://www.vogella.com/tutorials/AndroidMedia/article.html
 */

public class MediaRecorderHandler implements IMediaRecorderHandler {

    private Context context;
    private MediaRecorder recorder;
    private boolean recordStatus = false;
    private int maxDuration = 30000;
    private File audiofile;
    private RecordAudio recordAudio;

    public MediaRecorderHandler(Context context, RecordAudio recordAudio) {
        this.context = context;
        this.recordAudio = recordAudio;
    }

    public void startRecording() throws IOException {

        File sampleDir = Environment.getExternalStorageDirectory();
        Log.d("mediaRecord", String.valueOf(sampleDir));

        try {
            audiofile = File.createTempFile("sound", ".mp4", sampleDir);
        } catch (IOException e) {
            Log.e("mediaRecord", "sdcard access error");
            Log.e("mediaRecord", String.valueOf(e));
        }
        String audioFilePath = audiofile.getAbsolutePath();
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        recorder.setOutputFile(audioFilePath);
        recorder.setMaxDuration(maxDuration);
        recorder.prepare();
        recorder.start();

        recordStatus = true;
        recordAudio.setAudioFile(audiofile);
        recordAudio.setFilePath(audioFilePath);
        recordAudio.setMaxDuration(maxDuration);
        recordTimer();
    }

    public void stopRecording() {
        try {
            recorder.stop();
        } catch (RuntimeException e){
            Log.e("StopRecord", "Stopped too quickly");
        }
        recorder.release();
        addRecordingToMediaLibrary();
        recordStatus = false;
    }

    private void addRecordingToMediaLibrary() {
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
    }

    public boolean getRecordStatus() {
        return recordStatus;
    }


    private void recordTimer(){

        new CountDownTimer(maxDuration, 200) {

            public void onTick(long millisUntilFinished) {
                if (recordStatus){
                    int actualTimeMs = (int) (maxDuration - millisUntilFinished);
                    recordAudio.setActualTimeMs(actualTimeMs);
                    recordAudio.setActualTime(milliSecondsToTimer(actualTimeMs));
                    recordAudio.setReverseActualTime(milliSecondsToTimer(millisUntilFinished));
                } else {
                    recordAudio.setActualTimeMs(0);
                    recordAudio.setActualTime(milliSecondsToTimer(0));
                    recordAudio.setReverseActualTime(milliSecondsToTimer(maxDuration));
                    cancel();
                }
            }

            public void onFinish() {
                recordAudio.setActualTimeMs(0);
                recordAudio.setActualTime(milliSecondsToTimer(0));
                recordAudio.setReverseActualTime(milliSecondsToTimer(maxDuration));
                stopRecording();
            }
        }.start();

    }

    private String milliSecondsToTimer(long milliseconds){
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

}
