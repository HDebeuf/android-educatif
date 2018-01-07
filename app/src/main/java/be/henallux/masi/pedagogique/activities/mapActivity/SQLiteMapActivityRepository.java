package be.henallux.masi.pedagogique.activities.mapActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import be.henallux.masi.pedagogique.activities.historyActivity.synthesis.Synthesis;
import be.henallux.masi.pedagogique.dao.interfaces.IQuestionnaireRepository;
import be.henallux.masi.pedagogique.dao.interfaces.ISynthesisRepository;
import be.henallux.masi.pedagogique.dao.sqlite.SQLiteSynthesisRepository;
import be.henallux.masi.pedagogique.dao.sqlite.SQLiteHelper;
import be.henallux.masi.pedagogique.dao.sqlite.SQLiteQuestionnaireRepository;
import be.henallux.masi.pedagogique.model.Questionnaire;

/**
 * Created by Le Roi Arthur on 17-12-17.
 */

public class SQLiteMapActivityRepository implements IMapActivityRepository {

    private Context ctx;
    private IQuestionnaireRepository questionnaireRepository;
    private ISynthesisRepository synthesisRepository;

    public SQLiteMapActivityRepository(Context ctx) {
        this.ctx = ctx;
        questionnaireRepository = SQLiteQuestionnaireRepository.getInstance(ctx);
        synthesisRepository = SQLiteSynthesisRepository.getInstance(ctx);
    }

    @Override
    public ArrayList<Location> getAllPointsOfInterestOfActivity(int id) {
        SQLiteDatabase db = SQLiteHelper.getDatabaseInstance(ctx);
        ArrayList<Location> locations = new ArrayList<>();
        Cursor cursor = db.query(LocationEntity.TABLE,
                new String[]{LocationEntity.COLUMN_ID,
                        LocationEntity.COLUMN_TITLE,
                        LocationEntity.COLUMN_LATITUDE,
                        LocationEntity.COLUMN_LONGITUDE,
                        LocationEntity.COLUMN_ACTIVITY_CANONICAL_NAME,
                        LocationEntity.COLUMN_FK_QUESTIONNAIRE},
                LocationEntity.COLUMN_FK_ACTIVITYMAPBASE + "=?",
                new String[]{String.valueOf(id)},
                null, null, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            try {
                int idLocation = cursor.getInt(0);
                String locationName = cursor.getString(1);
                LatLng lt = new LatLng(cursor.getDouble(2),cursor.getDouble(3));
                Class classToThrow = java.lang.Class.forName(cursor.getString(4));
                int idQuestionnaire = cursor.getInt(5);

                Questionnaire questionnaire = questionnaireRepository.getQuestionnaireById(idQuestionnaire);

                Location location = new Location(idLocation,locationName,lt, classToThrow, null, questionnaire);
                ArrayList<Synthesis> synthesisArrayList = synthesisRepository.getAllSynthesisOfLocation(location);
                location.setSynthesisArrayList(synthesisArrayList);
                locations.add(location);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            cursor.moveToNext();
        }
        cursor.close();
        return locations;
    }

    @Override
    public ActivityMapBase getActivityById(int idActivity) {
        SQLiteDatabase database = SQLiteHelper.getDatabaseInstance(ctx);
        String statement = ActivityMapBaseEntity.SELECT_REQUEST_WHERE_ID;

        Cursor cursor = database.rawQuery(statement,new String[]{String.valueOf(idActivity)});
        cursor.moveToFirst();
        Class activityClass;
        try {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            activityClass = Class.forName(cursor.getString(3));


            String stringIcon = cursor.getString(4);
            Uri uriIcon = null;
            if(!TextUtils.isEmpty(stringIcon))
                uriIcon = Uri.parse(stringIcon);

            String stringURI = cursor.getString(5);
            Uri uriJson = null;
            if(!TextUtils.isEmpty(stringURI))
                uriJson = Uri.parse(stringURI);

            double latitudeCenter = cursor.getDouble(6);
            double longitudeCenter = cursor.getDouble(7);
            double zoom = cursor.getDouble(8);
            LatLng defaultLocation = new LatLng(latitudeCenter,longitudeCenter);

            ArrayList<Location> locations = getAllPointsOfInterestOfActivity(id);

            return new ActivityMapBase(id,name,activityClass,uriJson, uriIcon,defaultLocation, zoom,locations);
        } catch (ClassNotFoundException e) {
            Log.e("Database","Could not get class for name " + cursor.getString(3));
            return null;
        }
        finally{
            cursor.close();
        }
    }

    @Override
    public Location getLocationById(int id) {
        SQLiteDatabase db = SQLiteHelper.getDatabaseInstance(ctx);
        Cursor cursor = db.query(LocationEntity.TABLE,
                new String[]{LocationEntity.COLUMN_ID,
                        LocationEntity.COLUMN_TITLE,
                        LocationEntity.COLUMN_LATITUDE,
                        LocationEntity.COLUMN_LONGITUDE,
                        LocationEntity.COLUMN_ACTIVITY_CANONICAL_NAME,
                        LocationEntity.COLUMN_FK_QUESTIONNAIRE},
                LocationEntity.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)},
                null, null, null);

        if(cursor.getCount() == 0) return null;
        cursor.moveToFirst();
        try {
            int idLocation = cursor.getInt(0);
            String locationName = cursor.getString(1);
            LatLng lt = new LatLng(cursor.getDouble(2),cursor.getDouble(3));
            Class classToThrow = java.lang.Class.forName(cursor.getString(4));
            int idQuestionnaire = cursor.getInt(5);

            Questionnaire questionnaire = questionnaireRepository.getQuestionnaireById(idQuestionnaire);

            Location location = new Location(idLocation,locationName,lt, classToThrow, null, questionnaire);
            ArrayList<Synthesis> synthesisArrayList = synthesisRepository.getAllSynthesisOfLocation(location);
            location.setSynthesisArrayList(synthesisArrayList);
            return location;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        finally {
            cursor.close();
        }
    }
}
