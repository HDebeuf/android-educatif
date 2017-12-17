package be.henallux.masi.pedagogique.dao.interfaces;

import java.util.ArrayList;

import be.henallux.masi.pedagogique.model.Activity;

/**
 * Created by Le Roi Arthur on 17-12-17.
 */

public interface IActivitiesRepository {
    ArrayList<Activity> getAllActivities();
}
