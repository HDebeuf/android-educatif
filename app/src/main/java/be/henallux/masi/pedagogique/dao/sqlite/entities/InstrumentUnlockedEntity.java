package be.henallux.masi.pedagogique.dao.sqlite.entities;

import be.henallux.masi.pedagogique.activities.mapActivity.LocationEntity;
import be.henallux.masi.pedagogique.activities.musicalActivity.makeMusic.entities.InstrumentEntity;

/**
 * Created by Le Roi Arthur on 06-01-18.
 */

public class InstrumentUnlockedEntity {
    public static final String TABLE = "idInstrumentUnlock";
    public static final String COLUMN_ID = "isUnlocked";
    public static final String COLUMN_FK_GROUP = "Group_idGroup";
    public static final String COLUMN_FK_INSTRUMENT = "Instrument_idInstrument";

    public static final String CREATE_TABLE_INSTRUMENT_UNLOCKED =
            "create table "
                    + InstrumentUnlockedEntity.TABLE + "("
                    + InstrumentUnlockedEntity.COLUMN_ID + " integer primary key autoincrement, "
                    + InstrumentUnlockedEntity.COLUMN_FK_GROUP + " varchar(50) not null, "
                    + InstrumentUnlockedEntity.COLUMN_FK_INSTRUMENT + " varchar(200) not null, "
                    + " foreign key (" + InstrumentUnlockedEntity.COLUMN_FK_GROUP + ") references " + GroupEntity.TABLE + "(" + GroupEntity.COLUMN_ID + "),"
                    + " foreign key (" + InstrumentUnlockedEntity.COLUMN_FK_INSTRUMENT + ") references " + InstrumentEntity.TABLE + "(" + InstrumentEntity.COLUMN_ID + "))";
}
