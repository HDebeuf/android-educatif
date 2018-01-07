package be.henallux.masi.pedagogique.activities.musicalActivity.makeMusic;

import android.net.Uri;

import be.henallux.masi.pedagogique.model.Questionnaire;

/**
 * Created by hendrikdebeuf2 on 30/12/17.
 */

public class Instrument {
    private int id;
    private int locationId;
    private String name;
    private String description;
    private Uri imagePath;
    private String sampleFileName;
    private Questionnaire questionnaire;
    private int soundId;
    private boolean isUnlocked;


    public Instrument(int id, int locationId, String name, String description, Uri imagePath, String sampleFileName, Questionnaire questionnaire, int soundId) {
        this.id = id;
        this.locationId = locationId;
        this.name = name;
        this.description = description;
        this.imagePath = imagePath;
        this.sampleFileName = sampleFileName;
        this.questionnaire = questionnaire;
        this.soundId = soundId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Uri getImagePath() {
        return imagePath;
    }

    public void setImagePath(Uri imagePath) {
        this.imagePath = imagePath;
    }

    public String getSampleFileName() {
        return sampleFileName;
    }

    public void setSampleFileName(String sampleFileName) {
        this.sampleFileName = sampleFileName;
    }

    public Questionnaire getQuestionnaire() {
        return questionnaire;
    }

    public void setQuestionnaire(Questionnaire questionnaire) {
        this.questionnaire = questionnaire;
    }

    public int getSoundId() {
        return soundId;
    }

    public void setSoundId(int soundId) {
        this.soundId = soundId;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Instrument)) return false;
        return((Instrument)obj).getId() == id;
    }
}

