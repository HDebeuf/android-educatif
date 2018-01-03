package be.henallux.masi.pedagogique.activities.historyActivity.synthesis;

import android.os.Parcel;

import java.net.URL;

import be.henallux.masi.pedagogique.activities.mapActivity.Location;

/**
 * Created by Le Roi Arthur on 29-12-17.
 */

public class SynthesisWebView extends Synthesis {
    public SynthesisWebView(int id,String text, URL url,Location location) {
        super(id, text, url,location);
    }

    public SynthesisWebView(Parcel in) {
        super(in);
    }
}
