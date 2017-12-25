package be.henallux.masi.pedagogique.activities.mapActivity;

import be.henallux.masi.pedagogique.dao.sqlite.entities.ActivityEntity;
import be.henallux.masi.pedagogique.dao.sqlite.entities.CategoryEntity;
import be.henallux.masi.pedagogique.dao.sqlite.entities.CategoryToActivityEntity;

/**
 * Created by Le Roi Arthur on 17-12-17.
 */

public class ActivityMapBaseEntity {
    public static final String TABLE = "ActivityMapBase";
    public static final String COLUMN_ID = "idActivityMapBase";
    public static final String COLUMN_STYLE = "URIJsonFile";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_LATITUDE_CENTER = "latitudeCenter";
    public static final String COLUMN_LONGITUDE_CENTER = "longitudeCenter";
    public static final String COLUMN_ZOOM = "zoomFactor";
    public static final String COLUMN_FK_ACTIVITY = "Activity_idActivity";

    public static final String CREATE_TABLE =
            "create table "
                    + ActivityMapBaseEntity.TABLE + "("
                    + ActivityMapBaseEntity.COLUMN_ID + " integer primary key autoincrement, "
                    + ActivityMapBaseEntity.COLUMN_STYLE + " varchar(500),"
                    + ActivityMapBaseEntity.COLUMN_NAME + " varchar(20) not null,"
                    + ActivityMapBaseEntity.COLUMN_LATITUDE_CENTER + " decimal(8,5) not null,"
                    + ActivityMapBaseEntity.COLUMN_LONGITUDE_CENTER + " decimal(8,5) not null,"
                    + ActivityMapBaseEntity.COLUMN_ZOOM + " decimal(8,5) not null,"
                    + ActivityMapBaseEntity.COLUMN_FK_ACTIVITY + " integer not null, foreign key (" + ActivityMapBaseEntity.COLUMN_FK_ACTIVITY + ") references " + ActivityEntity.TABLE + "(" + ActivityEntity.COLUMN_ID + "))";



    public static final String SELECT_REQUEST_WHERE_CATEGORY = "select " + COLUMN_ID + "," +
            TABLE + "." + COLUMN_NAME + "," +
            ActivityEntity.COLUMN_CLASS_CANONICAL_CLASS_NAME + "," +
            ActivityEntity.COLUMN_ACTIVITY_CANONICAL_CLASS_NAME + "," +
            ActivityEntity.COLUMN_URI_ICON + "," +
            COLUMN_STYLE  + "," +
            COLUMN_LATITUDE_CENTER  + "," +
            COLUMN_LONGITUDE_CENTER + "," +
            COLUMN_ZOOM +
            " from " + TABLE +
            " inner join " + ActivityEntity.TABLE +
            " inner join " + CategoryToActivityEntity.TABLE +
            " inner join " + CategoryEntity.TABLE +
            " where " + ActivityMapBaseEntity.COLUMN_FK_ACTIVITY + " = " + ActivityEntity.COLUMN_ID +
            " and " + CategoryToActivityEntity.COLUMN_FK_ACTIVITY + " = " + ActivityEntity.COLUMN_ID +
            " and " + CategoryToActivityEntity.COLUMN_FK_CATEGORY + " = " + CategoryEntity.COLUMN_ID +
            " and " + CategoryEntity.COLUMN_ID + "=?";


    public static final String SELECT_REQUEST_WHERE_ID = "select " + COLUMN_ID + "," +
            TABLE + "." + COLUMN_NAME + "," +
            ActivityEntity.COLUMN_CLASS_CANONICAL_CLASS_NAME + "," +
            ActivityEntity.COLUMN_ACTIVITY_CANONICAL_CLASS_NAME + "," +
            ActivityEntity.COLUMN_URI_ICON + "," +
            COLUMN_STYLE  + "," +
            COLUMN_LATITUDE_CENTER  + "," +
            COLUMN_LONGITUDE_CENTER + "," +
            COLUMN_ZOOM +
            " from " + TABLE +
            " inner join " + ActivityEntity.TABLE +
            " inner join " + CategoryToActivityEntity.TABLE +
            " inner join " + CategoryEntity.TABLE +
            " where " + COLUMN_FK_ACTIVITY + " = " + ActivityEntity.COLUMN_ID +
            " and " + CategoryToActivityEntity.COLUMN_FK_ACTIVITY + " = " + ActivityEntity.COLUMN_ID +
            " and " + CategoryToActivityEntity.COLUMN_FK_CATEGORY + " = " + CategoryEntity.COLUMN_ID +
            " and " + COLUMN_ID + "=?";
}
