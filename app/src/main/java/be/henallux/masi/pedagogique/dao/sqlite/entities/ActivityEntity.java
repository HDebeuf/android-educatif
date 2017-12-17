package be.henallux.masi.pedagogique.dao.sqlite.entities;

/**
 * Created by Le Roi Arthur on 17-12-17.
 */

public class ActivityEntity {
    public static final String TABLE = "EducativeActivity";
    public static final String COLUMN_ID = "idActivity";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_FK_CATEGORY = "Category_idCategory";
    public static final String COLUMN_FK_QUESTIONNAIRE = "Questionnaire_idQuestionnaire";
}
