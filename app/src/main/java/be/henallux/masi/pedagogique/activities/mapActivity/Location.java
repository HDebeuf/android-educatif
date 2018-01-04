package be.henallux.masi.pedagogique.activities.mapActivity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

import java.lang.*;
import java.lang.Class;
import java.util.ArrayList;

import be.henallux.masi.pedagogique.activities.historyActivity.synthesis.Synthesis;

/**
 * Created by Le Roi Arthur on 17-12-17.
 * A point of interest on a map
 */
public class Location implements Parcelable {

    private int id;
    private String title;
    private LatLng location;
    private java.lang.Class classToThrow; //The class that will be thrown in intent when the location is touched
    private ArrayList<Synthesis> synthesisArrayList;

    public Location(int id, String title, LatLng location, Class classToThrow, ArrayList<Synthesis> synthesisArrayList) {
        this.id = id;
        this.title = title;
        this.location = location;
        this.classToThrow = classToThrow;
        this.synthesisArrayList = synthesisArrayList;
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

    public ArrayList<Synthesis> getSynthesisArrayList() {
        return synthesisArrayList;
    }

    public void setSynthesisArrayList(ArrayList<Synthesis> synthesisArrayList) {
        this.synthesisArrayList = synthesisArrayList;
    }


    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof  Location)) return false;
        return ((Location) obj).getId() == id;
    }

    protected Location(Parcel in) {
        id = in.readInt();
        title = in.readString();
        location = (LatLng) in.readValue(LatLng.class.getClassLoader());
        String classToThrowString = in.readString();
        try {
            classToThrow = Class.forName(classToThrowString);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (in.readByte() == 0x01) {
            synthesisArrayList = new ArrayList<Synthesis>();
            in.readTypedList(synthesisArrayList, Synthesis.CREATOR);
        } else {
            synthesisArrayList = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeValue(location);
        dest.writeString(classToThrow.getName());
        if (synthesisArrayList == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeTypedList(synthesisArrayList);
        }
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