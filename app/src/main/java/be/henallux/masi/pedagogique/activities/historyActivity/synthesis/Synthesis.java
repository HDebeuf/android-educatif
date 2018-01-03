package be.henallux.masi.pedagogique.activities.historyActivity.synthesis;

import android.os.Parcel;
import android.os.Parcelable;

import java.net.URL;

import be.henallux.masi.pedagogique.activities.mapActivity.Location;

/**
 * Created by Le Roi Arthur on 29-12-17.
 */

public class Synthesis implements Parcelable {

    private int id;
    private String text;
    private URL url;
    private Location location;


    public Synthesis(int id, String text, URL url) {
        this.id = id;
        this.text = text;
        this.url = url;
    }

    public Synthesis(int id, String text, URL url, Location location) {
        this.id = id;
        this.text = text;
        this.url = url;
        this.location = location;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    protected Synthesis(Parcel in) {
        id = in.readInt();
        text = in.readString();
        url = (URL) in.readValue(URL.class.getClassLoader());
        location = (Location) in.readValue(Location.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if(this instanceof SynthesisImage)
            dest.writeInt(1);
        if(this instanceof SynthesisVideo)
            dest.writeInt(2);
        if(this instanceof SynthesisWebView)
            dest.writeInt(3);

        dest.writeInt(id);
        dest.writeString(text);
        dest.writeValue(url);
        location.setSynthesisArrayList(null);
        dest.writeValue(location);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Synthesis> CREATOR = new Parcelable.Creator<Synthesis>() {
        @Override
        public Synthesis createFromParcel(Parcel in) {
            int type = in.readInt();
            switch(type){
                case 1:
                return new SynthesisImage(in);

                case 2:
                return new SynthesisVideo(in);

                case 3:
                return new SynthesisWebView(in);
            }
            return new Synthesis(in);
        }

        @Override
        public Synthesis[] newArray(int size) {
            return new Synthesis[size];
        }
    };
}