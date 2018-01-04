package be.henallux.masi.pedagogique.dao.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;

import java.util.ArrayList;

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
                        InstrumentEntity.COLUMN_SAMPLE_FILE_NAME,
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


            String sampleFileString = cursor.getString(3);

            boolean isUnlocked = cursor.getInt(4) != 0;

            int locationId = cursor.getInt(5);

            instruments.add(new Instrument(id, locationId, name, uriImage, sampleFileString, isUnlocked));
            cursor.moveToNext();
        }

        cursor.close();
        return instruments;

    }

    @Override
    public Instrument getOneInstrument(int id) {
        return null;
    }


}
