package be.henallux.masi.pedagogique.activities.musicalActivity.makeMusic;

import java.io.File;
import java.io.Serializable;

/**
 * Created by hendrikdebeuf2 on 31/12/17.
 */

public class RecordAudio implements Serializable {

    private File audioFile;
    private String fileName;
    private String filePath;
    private String actualTime;
    private int actualTimeMs;
    private String reverseActualTime;
    private int maxDuration;

    public RecordAudio(){

    }

    public File getAudioFile() {
        return audioFile;
    }

    public void setAudioFile(File audioFile) {
        this.audioFile = audioFile;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getActualTime() {
        return actualTime;
    }

    public void setActualTime(String actualTime) {
        this.actualTime = actualTime;
    }

    public int getActualTimeMs() {
        return actualTimeMs;
    }

    public void setActualTimeMs(int actualTimeMs) {
        this.actualTimeMs = actualTimeMs;
    }

    public String getReverseActualTime() {
        return reverseActualTime;
    }

    public void setReverseActualTime(String reverseActualTime) {
        this.reverseActualTime = reverseActualTime;
    }

    public int getMaxDuration() {
        return maxDuration;
    }

    public void setMaxDuration(int maxDuration) {
        this.maxDuration = maxDuration;
    }
}