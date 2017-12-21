package be.henallux.masi.pedagogique.activities.mapActivity;

import java.util.ArrayList;

/**
 * Created by Le Roi Arthur on 17-12-17.
 */

public interface IMapActivityRepository {
    ArrayList<Location> getAllPointsOfInterestById(int id);
    ActivityMapBase getActivityById(int id);
    Location getLocationById(int id);
}
