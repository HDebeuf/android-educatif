package be.henallux.masi.pedagogique.dao.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import be.henallux.masi.pedagogique.activities.mapActivity.ActivityMapBase;
import be.henallux.masi.pedagogique.activities.mapActivity.ActivityMapBaseEntity;
import be.henallux.masi.pedagogique.dao.interfaces.IActivitiesRepository;
import be.henallux.masi.pedagogique.dao.sqlite.entities.ActivityEntity;
import be.henallux.masi.pedagogique.model.Activity;
import be.henallux.masi.pedagogique.model.Category;

/**
 * Created by Le Roi Arthur on 17-12-17.
 */

public class SQLiteActivitiesRepository implements IActivitiesRepository {

    private Context context;
    private static SQLiteActivitiesRepository instance;

    private SQLiteActivitiesRepository(Context context) {
        this.context = context;
    }

    public static SQLiteActivitiesRepository getInstance(Context ctx){
        if(instance == null){
            instance = new SQLiteActivitiesRepository(ctx);
        }
        return instance;
    }


    @Override
    public ArrayList<Activity> getAllActivities() {
        SQLiteDatabase database = SQLiteHelper.getDatabaseInstance(context);
        ArrayList<Activity> activities = new ArrayList<>();
        Cursor cursor = database.query(ActivityEntity.TABLE,
                new String[]{ActivityEntity.COLUMN_ID,
                        ActivityEntity.COLUMN_NAME,
                        ActivityEntity.COLUMN_ACTIVITY_CANONICAL_CLASS_NAME,
                        ActivityEntity.COLUMN_CLASS_CANONICAL_CLASS_NAME},
                null, null, null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Class _class = null;
            try {
                _class = Class.forName(cursor.getString(2));
                activities.add(new Activity(cursor.getInt(0),cursor.getString(1), _class));
            } catch (ClassNotFoundException e) {
                Log.e("Database","Could not get class for name " + cursor.getString(2));
            }
            cursor.moveToNext();
        }

        cursor.close();
        return activities;
    }

    @Override
    public ArrayList<Activity> getAllActivitiesOfCategory(Category c) {
        SQLiteDatabase database = SQLiteHelper.getDatabaseInstance(context);
        ArrayList<Activity> activities = new ArrayList<>();

        //For now, different activities will be retrieved manually
        //For each subtype of "ActivityEntity", an instance of the subtype will be added to the list (activities)
        //All these activities will be shown at the main menu, after the category of the user is known

        //region mapsActivity
        activities.addAll(getMapsActivities(database,c));

        return activities;
    }

    private ArrayList<ActivityMapBase> getMapsActivities(SQLiteDatabase database, Category c){

        ArrayList<ActivityMapBase> activities = new ArrayList<>();

        String statement = ActivityMapBaseEntity.SELECT_REQUEST_WHERE_CATEGORY;

        Cursor cursor = database.rawQuery(statement,new String[]{String.valueOf(c.getId())});
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Class activityClass;
            try {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                activityClass = Class.forName(cursor.getString(3));

                String stringURI = cursor.getString(4);
                Uri uriJson = null;
                if(!TextUtils.isEmpty(stringURI))
                    uriJson = Uri.parse(stringURI);
                double latitudeCenter = cursor.getDouble(5);
                double longitudeCenter = cursor.getDouble(6);
                double zoom = cursor.getDouble(7);
                LatLng defaultLocation = new LatLng(latitudeCenter,longitudeCenter);

                activities.add(new ActivityMapBase(id,name,activityClass,uriJson, defaultLocation, zoom,null));
            } catch (ClassNotFoundException e) {
                Log.e("Database","Could not get class for name " + cursor.getString(3));
            }
            cursor.moveToNext();
        }
        cursor.close();
        return activities;
    }



}
