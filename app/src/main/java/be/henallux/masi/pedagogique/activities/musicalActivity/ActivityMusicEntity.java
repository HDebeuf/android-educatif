package be.henallux.masi.pedagogique.activities.musicalActivity;

import be.henallux.masi.pedagogique.activities.mapActivity.ActivityMapBaseEntity;
import be.henallux.masi.pedagogique.dao.sqlite.entities.ActivityEntity;
import be.henallux.masi.pedagogique.dao.sqlite.entities.CategoryEntity;
import be.henallux.masi.pedagogique.dao.sqlite.entities.CategoryToActivityEntity;

/**
 * Created by Le Roi Arthur on 18-12-17.
 */

public class ActivityMusicEntity {
    public static final String TABLE = "ActivityMusic";
    public static final String COLUMN_ID = "idActivitMusic";
    public static final String COLUMN_STYLE = "URIJsonFile";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_FK_ACTIVITY = "Activity_idActivity";

    public static final String CREATE_TABLE =
            "create table "
                    + TABLE + "("
                    + COLUMN_ID + " integer primary key autoincrement, "
                    + COLUMN_STYLE + " varchar(500) not null,"
                    + COLUMN_NAME + " varchar(20) not null,"
                    + COLUMN_FK_ACTIVITY + " integer not null, foreign key (" + COLUMN_FK_ACTIVITY + ") references " + ActivityEntity.TABLE + "(" + ActivityEntity.COLUMN_ID + "))";



    public static final String SELECT_REQUEST = "select " + COLUMN_ID + "," +
            TABLE + "." + COLUMN_NAME + "," +
            ActivityEntity.COLUMN_CLASS_CANONICAL_CLASS_NAME + "," +
            ActivityEntity.COLUMN_ACTIVITY_CANONICAL_CLASS_NAME + "," +
            COLUMN_STYLE  +
            " from " + TABLE +
            " inner join " + ActivityEntity.TABLE +
            " inner join " + CategoryToActivityEntity.TABLE +
            " inner join " + CategoryEntity.TABLE +
            " where " + COLUMN_FK_ACTIVITY + " = " + ActivityEntity.COLUMN_ID +
            " and " + CategoryToActivityEntity.COLUMN_FK_ACTIVITY + " = " + ActivityEntity.COLUMN_ID +
            " and " + CategoryToActivityEntity.COLUMN_FK_CATEGORY + " = " + CategoryEntity.COLUMN_ID +
            " and " + CategoryEntity.COLUMN_ID + "=?";
}
