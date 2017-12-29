package be.henallux.masi.pedagogique.model;

import java.util.ArrayList;

/**
 * Created by hendrikdebeuf2 on 29/12/17.
 */

public class AnswerToQuestion {

    private Question question;
    private ArrayList<Answer> chosenAnswers;

    public ArrayList<Answer> getChosenAnswers() {
        return chosenAnswers;
    }

    public void setChosenAnswers(ArrayList<Answer> chosenAnswers) {
        this.chosenAnswers = chosenAnswers;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }


}
