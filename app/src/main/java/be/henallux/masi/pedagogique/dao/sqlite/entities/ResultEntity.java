package be.henallux.masi.pedagogique.dao.sqlite.entities;

/**
 * Created by hendrikdebeuf2 on 29/12/17.
 */

public class ResultEntity {
    public static final String TABLE = "Result";
    public static final String COLUMN_ID = "idResult";
    public static final String COLUMN_NB_CORRECT = "nbCorrectAnswers";
    public static final String COLUMN_NB_WRONG = "nbWrongAnswers";
    public static final String COLUMN_FK_GROUP = "Group_idGroup";
}
