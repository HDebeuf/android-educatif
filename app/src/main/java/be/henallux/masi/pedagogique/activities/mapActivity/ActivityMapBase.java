package be.henallux.masi.pedagogique.activities.mapActivity;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

import java.lang.*;
import java.util.ArrayList;

import be.henallux.masi.pedagogique.model.Activity;

/**
 * Created by Le Roi Arthur on 17-12-17.
 */

public class ActivityMapBase extends Activity implements Parcelable {

    private Integer id;
    private Uri jsonFileStyleURI; //A json description for the map style, set as an option with the setup of the map
    private ArrayList<Location> pointsOfInterest;
    private LatLng defaultLocation; // The center of the map when the app is started
    private double zoom; // The default zoom

    public ActivityMapBase(Integer id, String name, Class associatedClass) {
        super(id, name, associatedClass);
    }


    public ActivityMapBase(Integer id, String name, Class associatedClass, Uri jsonFileStyle, LatLng defaultLocation, double zoom, ArrayList<Location> locations) {
        super(id,name,associatedClass);
        this.id = id;
        this.jsonFileStyleURI = jsonFileStyle;
        this.defaultLocation = defaultLocation;
        this.zoom = zoom;
        this.pointsOfInterest = locations;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Uri getStyle() {
        return jsonFileStyleURI;
    }

    public void setStyle(Uri style) {
        this.jsonFileStyleURI = style;
    }

    public LatLng getDefaultLocation() {
        return defaultLocation;
    }

    public void setDefaultLocation(LatLng defaultLocation) {
        this.defaultLocation = defaultLocation;
    }

    public double getZoom() {
        return zoom;
    }

    public void setZoom(double zoom) {
        this.zoom = zoom;
    }

    public ArrayList<Location> getPointsOfInterest() {
        return pointsOfInterest;
    }

    public void setPointsOfInterest(ArrayList<Location> pointsOfInterest) {
        this.pointsOfInterest = pointsOfInterest;
    }

    public Uri getJsonFileStyleURI() {
        return jsonFileStyleURI;
    }

    protected ActivityMapBase(Parcel in) {
        id = in.readByte() == 0x00 ? null : in.readInt();
        jsonFileStyleURI = (Uri) in.readValue(Uri.class.getClassLoader());
        if (in.readByte() == 0x01) {
            pointsOfInterest = new ArrayList<Location>();
            in.readList(pointsOfInterest, Location.class.getClassLoader());
        } else {
            pointsOfInterest = null;
        }
        defaultLocation = (LatLng) in.readValue(LatLng.class.getClassLoader());
        zoom = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(id);
        }
        dest.writeValue(jsonFileStyleURI);
        if (pointsOfInterest == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(pointsOfInterest);
        }
        dest.writeValue(defaultLocation);
        dest.writeDouble(zoom);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ActivityMapBase> CREATOR = new Parcelable.Creator<ActivityMapBase>() {
        @Override
        public ActivityMapBase createFromParcel(Parcel in) {
            return new ActivityMapBase(in);
        }

        @Override
        public ActivityMapBase[] newArray(int size) {
            return new ActivityMapBase[size];
        }
    };
}