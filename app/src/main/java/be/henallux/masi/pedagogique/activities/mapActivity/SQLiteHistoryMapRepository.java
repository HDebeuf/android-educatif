package be.henallux.masi.pedagogique.activities.mapActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.net.URI;
import java.util.ArrayList;

import be.henallux.masi.pedagogique.dao.sqlite.SQLiteHelper;
import be.henallux.masi.pedagogique.dao.sqlite.entities.ActivityEntity;
import be.henallux.masi.pedagogique.dao.sqlite.entities.LocationEntity;
import be.henallux.masi.pedagogique.model.Activity;
import be.henallux.masi.pedagogique.model.Location;

/**
 * Created by Le Roi Arthur on 17-12-17.
 */

public class SQLiteHistoryMapRepository implements IHistoryMapRepository {

    private Context ctx;

    public SQLiteHistoryMapRepository(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public ArrayList<Location> getAllPointsOfInterestById(int id) {
        SQLiteDatabase db = SQLiteHelper.getDatabaseInstance(ctx);
        ArrayList<Location> locations = new ArrayList<>();
        Cursor cursor = db.query(LocationEntity.TABLE,
                new String[]{LocationEntity.COLUMN_TITLE, LocationEntity.COLUMN_LATITUDE, LocationEntity.COLUMN_LONGITUDE},
                LocationEntity.COLUMN_FK_ACTIVITYMAPBASE + "=?",
                new String[]{String.valueOf(id)},
                null, null, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {

            LatLng lt = new LatLng(cursor.getDouble(1),cursor.getDouble(2));
            locations.add(new Location(cursor.getString(0),lt));
            cursor.moveToNext();
        }
        cursor.close();
        return locations;
    }


    @Override
    public URI getUriJsonFileStyleOfId(int id) {
        return null;
    }
}
