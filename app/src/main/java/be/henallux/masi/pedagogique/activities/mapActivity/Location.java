package be.henallux.masi.pedagogique.activities.mapActivity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

import java.lang.*;
import java.lang.Class;

/**
 * Created by Le Roi Arthur on 17-12-17.
 * A point of interest on a map
 */

public class Location implements Parcelable {

    private int id;
    private String title;
    private LatLng location;
    private java.lang.Class classToThrow; //The class that will be thrown in intent when the location is touched

    public Location(int id, String title, LatLng location, Class classToThrow) {
        this.id = id;
        this.title = title;
        this.location = location;
        this.classToThrow = classToThrow;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public java.lang.Class getClassToThrow() {
        return classToThrow;
    }

    public void setClassToThrow(Class classToThrow) {
        this.classToThrow = classToThrow;
    }

    public int getId() {
        return id;
    }

    protected Location(Parcel in) {
        title = in.readString();
        location = (LatLng) in.readValue(LatLng.class.getClassLoader());
        classToThrow = (java.lang.Class) in.readValue(java.lang.Class.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeValue(location);
        dest.writeValue(classToThrow);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Location> CREATOR = new Parcelable.Creator<Location>() {
        @Override
        public Location createFromParcel(Parcel in) {
            return new Location(in);
        }

        @Override
        public Location[] newArray(int size) {
            return new Location[size];
        }
    };
}