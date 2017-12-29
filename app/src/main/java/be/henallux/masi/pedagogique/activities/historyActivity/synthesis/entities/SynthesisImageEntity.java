package be.henallux.masi.pedagogique.activities.historyActivity.synthesis.entities;

import be.henallux.masi.pedagogique.activities.mapActivity.LocationEntity;

/**
 * Created by Le Roi Arthur on 29-12-17.
 */

public class SynthesisImageEntity {
    public static final String TABLE = "SynthesisImage";
    public static final String COLUMN_ID = "idSynthesisImage";
    public static final String COLUMN_URL_IMAGE = "urlImage";
    public static final String COLUMN_TEXT = "text";
    public static final String COLUMN_FK_LOCATION = "fk_location";

    public static final String CREATE_TABLE_SYNTHESIS_IMAGE =
            "create table " +
                    TABLE + "("
                    + COLUMN_ID + " integer primary key autoincrement, "
                    + COLUMN_URL_IMAGE + " varchar(200) not null,"
                    + COLUMN_TEXT + " varchar(200),"
                    + COLUMN_FK_LOCATION + " integer not null, foreign key (" + COLUMN_FK_LOCATION + ") references " + LocationEntity.TABLE + "(" + LocationEntity.COLUMN_ID + "))";


}
