package be.henallux.masi.pedagogique.dao.interfaces;

import java.util.ArrayList;

import be.henallux.masi.pedagogique.activities.historyActivity.synthesis.Synthesis;
import be.henallux.masi.pedagogique.activities.mapActivity.Location;

/**
 * Created by Le Roi Arthur on 29-12-17.
 */

public interface ISynthesisRepository {
    ArrayList<Synthesis> getAllSynthesisOfLocation(Location l);
}
