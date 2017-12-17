package be.henallux.masi.pedagogique.dao.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import be.henallux.masi.pedagogique.dao.interfaces.IActivitiesRepository;
import be.henallux.masi.pedagogique.dao.sqlite.entities.ActivityEntity;
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
        Cursor cursor = database.query(ActivityEntity.TABLE, new String[]{ActivityEntity.COLUMN_ID, ActivityEntity.COLUMN_NAME}, null, null, null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            activities.add(new Activity(cursor.getInt(0),cursor.getString(1)));
            cursor.moveToNext();
        }

        cursor.close();
        return activities;
    }
}
