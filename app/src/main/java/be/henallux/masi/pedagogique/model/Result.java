package be.henallux.masi.pedagogique.model;

import java.util.ArrayList;

/**
 * Created by hendrikdebeuf2 on 29/12/17.
 */

public class Result {

    private Questionnaire questionnaire;
    private ArrayList<AnswerToQuestion> givenAnswers;
    private Integer correctAnswers;
    private Integer wrongAnswers;

    public Questionnaire getQuestionnaire() {
        return questionnaire;
    }

    public void setQuestionnaire(Questionnaire questionnaire) {
        this.questionnaire = questionnaire;
    }

    public ArrayList<AnswerToQuestion> getGivenAnswers() {
        return givenAnswers;
    }

    public void setGivenAnswers(ArrayList<AnswerToQuestion> givenAnswers) {
        this.givenAnswers = givenAnswers;
    }

    public Integer getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(Integer correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public Integer getWrongAnswers() {
        return wrongAnswers;
    }

    public void setWrongAnswers(Integer wrongAnswers) {
        this.wrongAnswers = wrongAnswers;
    }

}
