package be.henallux.masi.pedagogique.dao.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import be.henallux.masi.pedagogique.activities.historyActivity.synthesis.Synthesis;
import be.henallux.masi.pedagogique.activities.historyActivity.synthesis.SynthesisVideo;
import be.henallux.masi.pedagogique.activities.historyActivity.synthesis.entities.SynthesisVideoEntity;
import be.henallux.masi.pedagogique.activities.musicalActivity.makeMusic.Instrument;
import be.henallux.masi.pedagogique.activities.musicalActivity.makeMusic.entities.InstrumentEntity;
import be.henallux.masi.pedagogique.dao.interfaces.IInstrumentRepository;

/**
 * Created by hendrikdebeuf2 on 30/12/17.
 */

public class SQLiteInstrumentRepository implements IInstrumentRepository {

    private Context context;

    public SQLiteInstrumentRepository(Context context) {
        this.context = context;
    }

    public ArrayList<Instrument> getAllInstruments() {

        SQLiteDatabase db = SQLiteHelper.getDatabaseInstance(context);
        ArrayList<Instrument> instruments = new ArrayList<>();
        Cursor cursor = db.query(InstrumentEntity.TABLE,
                new String[]{InstrumentEntity.COLUMN_ID,
                        InstrumentEntity.COLUMN_NAME,
                        InstrumentEntity.COLUMN_IMAGE_PATH,
                        InstrumentEntity.COLUMN_SAMPLE_PATH,
                        InstrumentEntity.COLUMN_UNLOCKED,
                        InstrumentEntity.COLUMN_FK_LOCATION},
                null,null,
                null, null, null);

        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String uriImageString = cursor.getString(2);
            Uri uriImage = null;
            if(!TextUtils.isEmpty(uriImageString)){
                uriImage = Uri.parse(uriImageString);
            }

            // TO DO Manage sound track path in db
            String uriSampleString = cursor.getString(3);
            Uri uriSample = null;
            if(!TextUtils.isEmpty(uriSampleString)){
                uriSample = Uri.parse(uriSampleString);
            }

            boolean isUnlocked = cursor.getInt(4) != 0;

            int locationId = cursor.getInt(5);

            instruments.add(new Instrument(id, locationId, name, uriImage, uriSample, isUnlocked));
            cursor.moveToNext();
        }

        cursor.close();
        return instruments;

    }



}
