package be.henallux.masi.pedagogique.dao.sqlite.entities;

/**
 * Created by hendrikdebeuf2 on 29/12/17.
 */

public class AnswerToQuestionEntity {
    //Represents a N to N table between Result, Answer and Question
    //A result contains many answers to many questions

    public static final String TABLE = "AnswersGiven_has_Question";
    public static final String COLUMN_ID = "AnswersGiven_has_QuestionID";
    public static final String COLUMN_FK_QUESTION = "Question_idQuestion";
    public static final String COLUMN_FK_RESULT = "Result_idResult";
    public static final String COLUMN_FK_ANSWER = "Answer_idAnswer";
}
