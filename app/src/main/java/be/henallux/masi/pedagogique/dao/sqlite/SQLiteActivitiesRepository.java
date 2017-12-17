package be.henallux.masi.pedagogique.dao.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import be.henallux.masi.pedagogique.dao.interfaces.IActivitiesRepository;
import be.henallux.masi.pedagogique.dao.sqlite.entities.ActivityEntity;
import be.henallux.masi.pedagogique.mapActivity.MapsActivity;
import be.henallux.masi.pedagogique.model.Activity;

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
        Cursor cursor = database.query(ActivityEntity.TABLE, new String[]{ActivityEntity.COLUMN_ID, ActivityEntity.COLUMN_NAME, ActivityEntity.COLUMN_CANONICAL_CLASS_NAME}, null, null, null, null, null);
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
}
