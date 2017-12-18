package be.henallux.masi.pedagogique.dao.interfaces;

import java.util.ArrayList;

import be.henallux.masi.pedagogique.model.Activity;
import be.henallux.masi.pedagogique.model.Category;

/**
 * Created by Le Roi Arthur on 17-12-17.
 */

public interface IActivitiesRepository {
    ArrayList<Activity> getAllActivities();
    ArrayList<Activity> getAllActivitiesOfCategory(Category c);
}
