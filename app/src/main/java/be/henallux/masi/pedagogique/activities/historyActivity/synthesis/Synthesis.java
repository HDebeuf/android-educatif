package be.henallux.masi.pedagogique.activities.historyActivity.synthesis;

import java.net.URL;

/**
 * Created by Le Roi Arthur on 29-12-17.
 */

public class Synthesis {

    private int id;
    private String text;
    private URL url;


    public Synthesis(int id, String text, URL url) {
        this.id = id;
        this.text = text;
        this.url = url;
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
}
