package be.henallux.masi.pedagogique.dao.interfaces;

import java.util.ArrayList;

import be.henallux.masi.pedagogique.model.Location;

/**
 * Created by Le Roi Arthur on 17-12-17.
 */

public interface IHistoryMapRepository {
    String getStyle();
    ArrayList<Location> getAllPointsOfInterest();
}
