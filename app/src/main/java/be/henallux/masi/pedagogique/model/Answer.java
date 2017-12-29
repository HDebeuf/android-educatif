package be.henallux.masi.pedagogique.model;

/**
 * Created by Le Roi Arthur on 17-12-17.
 */

public class Answer {

    private Integer id;
    private String statement;
    private Question question;
    private boolean isTrue;

    public Answer(Integer id, String statement, Question question) {
        this.id = id;
        this.statement = statement;
        this.question = question;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
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

    public boolean getIsTrue() {
        return isTrue;
    }

    public void setTrue(boolean aTrue) {
        isTrue = aTrue;
    }
}
