package be.henallux.masi.pedagogique.model;

import java.util.ArrayList;

/**
 * Created by Le Roi Arthur on 17-12-17.
 */

public class ActivityMap extends Activity {

    private Integer id;
    private String style; //A json description for the map style, set as an option with the setup of the map
    private ArrayList<Location> pointsOfInterest;

    public ActivityMap(Integer id, String name, Category category, String style, ArrayList<Location> pointsOfInterest) {
        super(id,name,category);
        this.id = id;
        this.style = style;
        this.pointsOfInterest = pointsOfInterest;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }
}
