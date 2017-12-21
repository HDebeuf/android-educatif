package be.henallux.masi.pedagogique.dao.sqlite.entities;

/**
 * Created by Le Roi Arthur on 17-12-17.
 */

public class LocationEntity {
    public static final String TABLE = "Location";
    public static final String COLUMN_ID = "idLocation";
    public static final String COLUMN_TITLE = "Title";
    public static final String COLUMN_LATITUDE = "Latitude";
    public static final String COLUMN_LONGITUDE = "Longitude";
    public static final String COLUMN_FK_ACTIVITYMAPBASE = "ActivityMapBase_idActivityMapBase";

    public static final String CREATE_TABLE_LOCATION =
            "create table "
                    + LocationEntity.TABLE + "("
                    + LocationEntity.COLUMN_ID + " integer primary key autoincrement, "
                    + LocationEntity.COLUMN_TITLE + " varchar(50) not null,"
                    + LocationEntity.COLUMN_LATITUDE + " decimal(8,5) not null,"
                    + LocationEntity.COLUMN_LONGITUDE + " decimal(8,5) not null,"
                    + LocationEntity.COLUMN_FK_ACTIVITYMAPBASE + " integer not null, foreign key (" + LocationEntity.COLUMN_FK_ACTIVITYMAPBASE + ") references " + QuestionEntity.TABLE + "(" + QuestionEntity.COLUMN_ID + "))";


}
