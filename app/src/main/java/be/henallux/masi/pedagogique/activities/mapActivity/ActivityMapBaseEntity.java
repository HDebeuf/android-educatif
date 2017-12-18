package be.henallux.masi.pedagogique.activities.mapActivity;

/**
 * Created by Le Roi Arthur on 17-12-17.
 */

public class ActivityMapBaseEntity {
    public static final String TABLE = "ActivityMapBase";
    public static final String COLUMN_ID = "idActivityMapBase";
    public static final String COLUMN_STYLE = "URIJsonFile";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_FK_ACTIVITY = "Activity_idActivity";
}
