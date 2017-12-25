package be.henallux.masi.pedagogique.dao.sqlite.entities;

/**
 * Created by Le Roi Arthur on 17-12-17.
 */

public class ActivityEntity {
    public static final String TABLE = "EducativeActivity";
    public static final String COLUMN_ID = "idActivity";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_ACTIVITY_CANONICAL_CLASS_NAME = "ActivityCanonicalClassname"; //The Android native activity that will be thrown when the user chooses this educative activity
    public static final String COLUMN_CLASS_CANONICAL_CLASS_NAME = "ClassCanonicalClassname"; //The name of the class (in the model) that will represent this activity (not used now, but maybe later)
    public static final String COLUMN_URI_ICON = "uriIcon";
    public static final String COLUMN_FK_QUESTIONNAIRE = "Questionnaire_idQuestionnaire";
}
