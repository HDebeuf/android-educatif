package be.henallux.masi.pedagogique.activities.mapActivity;

import java.net.URI;
import java.util.ArrayList;

import be.henallux.masi.pedagogique.model.Location;

/**
 * Created by Le Roi Arthur on 17-12-17.
 */

public interface IHistoryMapRepository {
    ArrayList<Location> getAllPointsOfInterestById(int id);
    URI getUriJsonFileStyleOfId(int id);
}
