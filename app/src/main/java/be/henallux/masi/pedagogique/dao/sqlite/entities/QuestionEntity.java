package be.henallux.masi.pedagogique.dao.sqlite.entities;

/**
 * Created by Le Roi Arthur on 17-12-17.
 */

public class QuestionEntity {
    public static final String TABLE = "Question";
    public static final String COLUMN_ID = "idQuestion";
    public static final String COLUMN_STATEMENT = "statement";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_FK_QUESTIONNAIRE = "Questionnaire_idQuestionnaire";

}
