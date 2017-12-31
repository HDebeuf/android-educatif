package be.henallux.masi.pedagogique.activities.musicalActivity.makeMusic;

import android.net.Uri;

/**
 * Created by hendrikdebeuf2 on 30/12/17.
 */

public class Instrument {
    private int id;
    private int locationId;
    private String name;
    private String description;
    private Uri imagePath;
    private Uri samplePath;
    private boolean isUnlocked;

    public Instrument(int id, int locationId, String name, Uri imagePath, Uri samplePath, boolean isUnlocked) {
        this.id = id;
        this.locationId = locationId;
        this.name = name;
        this.imagePath = imagePath;
        this.samplePath = samplePath;
        this.isUnlocked = isUnlocked;
    }

    public Instrument(int id, int locationId, String name, String description, Uri imagePath) {
        this.id = id;
        this.locationId = locationId;
        this.name = name;
        this.description = description;
        this.imagePath = imagePath;
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

    public Uri getSamplePath() {
        return samplePath;
    }

    public void setSamplePath(Uri samplePath) {
        this.samplePath = samplePath;
    }

    public boolean isUnlocked() {
        return isUnlocked;
    }

    public void setUnlocked(boolean unlocked) {
        isUnlocked = unlocked;
    }


}

