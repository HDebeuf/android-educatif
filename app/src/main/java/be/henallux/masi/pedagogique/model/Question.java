package be.henallux.masi.pedagogique.model;

import java.util.ArrayList;

/**
 * Created by Le Roi Arthur on 17-12-17.
 *
 */

public class Question {
    private Integer id;
    private String statement;
    private Questionnaire questionnaire;
    private ArrayList<Answer> answers;

    public Question(Integer id, String statement, Questionnaire questionnaire, ArrayList<Answer> answers) {
        this.id = id;
        this.statement = statement;
        this.questionnaire = questionnaire;
        this.answers = answers;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public Questionnaire getQuestionnaire() {
        return questionnaire;
    }

    public void setQuestionnaire(Questionnaire questionnaire) {
        this.questionnaire = questionnaire;
    }

    public ArrayList<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<Answer> answers) {
        this.answers = answers;
    }
}
