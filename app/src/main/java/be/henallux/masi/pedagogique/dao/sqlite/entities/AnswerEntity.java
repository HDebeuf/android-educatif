package be.henallux.masi.pedagogique.dao.sqlite.entities;

/**
 * Created by Le Roi Arthur on 17-12-17.
 */

public class AnswerEntity {
    public static final String TABLE = "Answer";
    public static final String COLUMN_ID = "idAnswer";
    public static final String COLUMN_STATEMENT = "statement";
    public static final String COLUMN_FK_QUESTION = "Question_idQuestion";
}
