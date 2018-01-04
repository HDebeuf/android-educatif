package be.henallux.masi.pedagogique.activities.historyActivity.synthesis;

import android.os.Parcel;

import java.net.URL;

import be.henallux.masi.pedagogique.activities.mapActivity.Location;

/**
 * Created by Le Roi Arthur on 29-12-17.
 */

public class SynthesisVideo extends Synthesis {
    public SynthesisVideo(int id,String text, URL url,Location l) {
        super(id, text, url,l);
    }

    public SynthesisVideo(Parcel in) {
        super(in);
    }
}
