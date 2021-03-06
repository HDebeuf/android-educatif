package be.henallux.masi.pedagogique.activities.musicalActivity.makeMusic.entities;

import be.henallux.masi.pedagogique.activities.mapActivity.LocationEntity;
import be.henallux.masi.pedagogique.dao.sqlite.entities.QuestionnaireEntity;

/**
 * Created by hendrikdebeuf2 on 30/12/17.
 */

public class InstrumentEntity {
    public static final String TABLE = "Instruments";
    public static final String COLUMN_ID = "idInstrument";
    public static final String COLUMN_FK_LOCATION = "Location_idLocation";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_IMAGE_PATH = "imagePath";
    public static final String COLUMN_SAMPLE_FILE_NAME = "sampleFileName";
    public static final String COLUMN_FK_QUESTIONNAIRE = "Questionnaire_idQuestionnaire";

    public static final String CREATE_TABLE_INSTRUMENTS =
            "create table "
                    + InstrumentEntity.TABLE + "("
                    + InstrumentEntity.COLUMN_ID + " integer primary key autoincrement, "
                    + InstrumentEntity.COLUMN_NAME + " varchar(50) not null, "
                    + InstrumentEntity.COLUMN_DESCRIPTION + " varchar(200) not null, "
                    + InstrumentEntity.COLUMN_IMAGE_PATH + " varchar(200) not null, "
                    + InstrumentEntity.COLUMN_SAMPLE_FILE_NAME + " varchar(200) not null, "
                    + InstrumentEntity.COLUMN_FK_LOCATION + " integer not null,"
                    + InstrumentEntity.COLUMN_FK_QUESTIONNAIRE + " integer not null,"
                    + " foreign key (" + InstrumentEntity.COLUMN_FK_LOCATION + ") references " + LocationEntity.TABLE + "(" + LocationEntity.COLUMN_ID + "),"
                    + " foreign key (" + InstrumentEntity.COLUMN_FK_QUESTIONNAIRE + ") references " + QuestionnaireEntity.TABLE + "(" + QuestionnaireEntity.COLUMN_ID + "))";

}
