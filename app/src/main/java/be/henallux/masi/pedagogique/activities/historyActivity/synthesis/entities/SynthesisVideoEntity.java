package be.henallux.masi.pedagogique.activities.historyActivity.synthesis.entities;

import be.henallux.masi.pedagogique.activities.mapActivity.LocationEntity;
import be.henallux.masi.pedagogique.dao.sqlite.entities.QuestionEntity;

/**
 * Created by Le Roi Arthur on 29-12-17.
 */

public class SynthesisVideoEntity {
    public static final String TABLE = "SynthesisVideo";
    public static final String COLUMN_ID = "idSynthesisVideo";
    public static final String COLUMN_URL_VIDEO = "urlVideo";
    public static final String COLUMN_TEXT = "text";
    public static final String COLUMN_FK_LOCATION = "fk_location";

    public static final String CREATE_TABLE_SYNTHESIS_VIDEO =
            "create table " +
                    TABLE + "("
                    + COLUMN_ID + " integer primary key autoincrement, "
                    + COLUMN_URL_VIDEO + " varchar(200) not null,"
                    + COLUMN_TEXT + " varchar(200),"
                    + COLUMN_FK_LOCATION + " integer not null, foreign key (" + COLUMN_FK_LOCATION + ") references " + LocationEntity.TABLE + "(" + LocationEntity.COLUMN_ID + "))";


}
