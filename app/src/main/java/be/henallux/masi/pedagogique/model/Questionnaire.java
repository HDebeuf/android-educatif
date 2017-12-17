package be.henallux.masi.pedagogique.model;

import java.util.ArrayList;

/**
 * Created by Le Roi Arthur on 17-12-17.
 */

public class Questionnaire {
    private Integer id;
    private String statement;
    private ArrayList<Question> questions;

    public Questionnaire(Integer id, String statement, ArrayList<Question> questions) {
        this.id = id;
        this.statement = statement;
        this.questions = questions;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }
}
