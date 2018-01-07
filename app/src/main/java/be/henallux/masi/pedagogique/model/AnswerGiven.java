package be.henallux.masi.pedagogique.model;

import java.util.ArrayList;

/**
 * Describes an answer given to a question
 * Created by Le Roi Arthur on 07-01-18.
 */

public class AnswerGiven {

    private Question question;
    private ArrayList<Answer> givenAnswers;

    public AnswerGiven(Question question, ArrayList<Answer> givenAnswers) {
        this.question = question;
        this.givenAnswers = givenAnswers;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public ArrayList<Answer> getGivenAnswers() {
        return givenAnswers;
    }

    public void setGivenAnswers(ArrayList<Answer> givenAnswers) {
        this.givenAnswers = givenAnswers;
    }
}
