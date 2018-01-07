package be.henallux.masi.pedagogique.dao.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;

import be.henallux.masi.pedagogique.activities.mapActivity.Location;
import be.henallux.masi.pedagogique.activities.musicalActivity.makeMusic.Instrument;
import be.henallux.masi.pedagogique.activities.musicalActivity.makeMusic.entities.InstrumentEntity;
import be.henallux.masi.pedagogique.dao.interfaces.IInstrumentRepository;
import be.henallux.masi.pedagogique.dao.interfaces.IQuestionnaireRepository;
import be.henallux.masi.pedagogique.dao.sqlite.entities.AnswerToQuestionEntity;
import be.henallux.masi.pedagogique.dao.sqlite.entities.CategoryEntity;
import be.henallux.masi.pedagogique.dao.sqlite.entities.InstrumentUnlockedEntity;
import be.henallux.masi.pedagogique.model.Category;
import be.henallux.masi.pedagogique.model.Group;
import be.henallux.masi.pedagogique.model.Questionnaire;

/**
 * Created by hendrikdebeuf2 on 30/12/17.
 * modified by Angèle Guillon on 04/01/18.
 */

public class SQLiteInstrumentRepository implements IInstrumentRepository {

    private Context context;
    private IQuestionnaireRepository questionnaireRepository = SQLiteQuestionnaireRepository.getInstance(context);
    private static SQLiteInstrumentRepository instance;

    private SQLiteInstrumentRepository(Context context) {
        this.context = context;
    }

    public static SQLiteInstrumentRepository getInstance(Context context){
        if(instance == null){
            instance = new SQLiteInstrumentRepository(context);
        }
        return instance;
    }

    public ArrayList<Instrument> getAllInstruments() {

        SQLiteDatabase db = SQLiteHelper.getDatabaseInstance(context);
        ArrayList<Instrument> instruments = new ArrayList<>();
        Cursor cursor = db.query(InstrumentEntity.TABLE,
                new String[]{InstrumentEntity.COLUMN_ID,
                        InstrumentEntity.COLUMN_FK_LOCATION,
                        InstrumentEntity.COLUMN_NAME,
                        InstrumentEntity.COLUMN_DESCRIPTION,
                        InstrumentEntity.COLUMN_IMAGE_PATH,
                        InstrumentEntity.COLUMN_SAMPLE_FILE_NAME,
                        InstrumentEntity.COLUMN_FK_QUESTIONNAIRE},
                null,null,
                null, null, null);

        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {
            int id = cursor.getInt(0);
            int locationId = cursor.getInt(1);
            String name = cursor.getString(2);
            String description = cursor.getString(3);
            String uriImageString = cursor.getString(4);
            String sampleFile = cursor.getString(5);
            int fkQuestionnaire = cursor.getInt(6);
            int soundID = -1;
            Uri uriImage = null;

            if(!TextUtils.isEmpty(uriImageString)){
                uriImage = Uri.parse(uriImageString);
            }

            Questionnaire questionnaire = questionnaireRepository.getQuestionnaireById(fkQuestionnaire);

            instruments.add(new Instrument(id,locationId, name,description, uriImage, sampleFile,questionnaire,soundID));
            cursor.moveToNext();
        }

        cursor.close();
        return instruments;

    }

    @Override
    public Instrument getInstrumentOfLocation(int idlocation) {
        SQLiteDatabase db = SQLiteHelper.getDatabaseInstance(context);
        ArrayList<Instrument> instruments = new ArrayList<>();
        Cursor cursor = db.query(InstrumentEntity.TABLE,
                new String[]{InstrumentEntity.COLUMN_ID,
                        InstrumentEntity.COLUMN_FK_LOCATION,
                        InstrumentEntity.COLUMN_NAME,
                        InstrumentEntity.COLUMN_DESCRIPTION,
                        InstrumentEntity.COLUMN_IMAGE_PATH,
                        InstrumentEntity.COLUMN_SAMPLE_FILE_NAME,
                        InstrumentEntity.COLUMN_FK_QUESTIONNAIRE},
                InstrumentEntity.COLUMN_FK_LOCATION + "=?",new String[]{String.valueOf(idlocation)},
                null, null, null);

        if(cursor.getCount() == 0) return null;
        cursor.moveToFirst();
        int id = cursor.getInt(0);
        int locationId = cursor.getInt(1);
        String name = cursor.getString(2);
        String description = cursor.getString(3);
        String uriImageString = cursor.getString(4);
        String sampleFile = cursor.getString(5);
        int fkQuestionnaire = cursor.getInt(6);
        int soundID = -1; //Not used yet
        Uri uriImage = null;

        if(!TextUtils.isEmpty(uriImageString)){
            uriImage = Uri.parse(uriImageString);
        }

        Questionnaire questionnaire = questionnaireRepository.getQuestionnaireById(fkQuestionnaire);

        Instrument i = new Instrument(id,locationId, name,description, uriImage, sampleFile,questionnaire,soundID);

        cursor.close();
        return i;
    }

    String makePlaceholders(int len) {
        if (len < 1) {
            // It will lead to an invalid query anyway ..
            throw new RuntimeException("No placeholders");
        } else {
            StringBuilder sb = new StringBuilder(len * 2 - 1);
            sb.append("?");
            for (int i = 1; i < len; i++) {
                sb.append(",?");
            }
            return sb.toString();
        }
    }

    @Override
    public ArrayList<Instrument> getInstrumentsOfGroup(Group g) {

        String[] idsOfUnlockedInstruments = getIdsOfUnlockedInstrumentsForGroup(g);

        if(idsOfUnlockedInstruments.length == 0) return new ArrayList<>();

        SQLiteDatabase db = SQLiteHelper.getDatabaseInstance(context);
        ArrayList<Instrument> instruments = new ArrayList<>();
        Cursor cursor = db.rawQuery("Select " +
                InstrumentEntity.COLUMN_ID + "," +
                InstrumentEntity.COLUMN_FK_LOCATION + "," +
                InstrumentEntity.COLUMN_NAME + "," +
                InstrumentEntity.COLUMN_DESCRIPTION + "," +
                InstrumentEntity.COLUMN_IMAGE_PATH + "," +
                InstrumentEntity.COLUMN_SAMPLE_FILE_NAME + "," +
                InstrumentEntity.COLUMN_FK_QUESTIONNAIRE+ "," +
                InstrumentEntity.COLUMN_FK_LOCATION + " from " + InstrumentEntity.TABLE
                + " where " + InstrumentEntity.COLUMN_ID + " in (" + makePlaceholders(idsOfUnlockedInstruments.length) ,idsOfUnlockedInstruments);

        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {
            int id = cursor.getInt(0);
            int locationId = cursor.getInt(1);
            String name = cursor.getString(2);
            String description = cursor.getString(3);
            String uriImageString = cursor.getString(4);
            String sampleFile = cursor.getString(5);
            int fkQuestionnaire = cursor.getInt(6);
            int soundID = cursor.getInt(7);
            Uri uriImage = null;

            if(!TextUtils.isEmpty(uriImageString)){
                uriImage = Uri.parse(uriImageString);
            }

            Questionnaire questionnaire = questionnaireRepository.getQuestionnaireById(fkQuestionnaire);

            instruments.add(new Instrument(id,locationId, name,description, uriImage, sampleFile,questionnaire,soundID));
            cursor.moveToNext();
        }

        cursor.close();
        return instruments;
    }

    private String[] getIdsOfUnlockedInstrumentsForGroup(Group g) {
        SQLiteDatabase db = SQLiteHelper.getDatabaseInstance(context);
        ArrayList<Integer> ids = new ArrayList<>();
        Cursor cursor = db.query(InstrumentUnlockedEntity.TABLE,
                new String[]{InstrumentUnlockedEntity.COLUMN_FK_INSTRUMENT},
                InstrumentUnlockedEntity.COLUMN_FK_GROUP + "=?",new String[]{String.valueOf(g.getId())},
                null, null, null);

        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {
            ids.add(cursor.getInt(0));
            cursor.moveToNext();
        }

        cursor.close();
        return convertIntegers(ids);
    }

    private static String[] convertIntegers(ArrayList<Integer> integers)
    {
        String[] ret = new String[integers.size()];
        for (int i=0; i < ret.length; i++)
        {
            ret[i] = String.valueOf(ret[i]);
        }
        return ret;
    }

    public void setIsUnlocked(int locationId, int groupId){

        int instrumentId = getInstrumentOfLocation(locationId).getId();

        SQLiteDatabase db = SQLiteHelper.getDatabaseInstance(context);
        ContentValues values = new ContentValues();
        values.put(InstrumentUnlockedEntity.COLUMN_FK_GROUP,groupId);
        values.put(InstrumentUnlockedEntity.COLUMN_FK_INSTRUMENT,instrumentId);
        db.insert(AnswerToQuestionEntity.TABLE, null, values);

    }

    public boolean isInstrumentActivity (int locationId){
        boolean isInstrumentActivity = false;
        int instrumentId = 0;
        try{
            instrumentId = getInstrumentOfLocation(locationId).getId();
        } catch (Exception e){
            Log.d("InstrumentChecker","No instrument linked to this location");
            isInstrumentActivity = false;
        }
        if (instrumentId!=0){
            isInstrumentActivity = true;
        }
        return isInstrumentActivity;
    }

}
