package be.henallux.masi.pedagogique.model;

/**
 * Created by Le Roi Arthur on 17-12-17.
 */

public class Answer {

    private Integer id;
    private String statement;
    private boolean isCorrect;

    public Answer(Integer id, String statement, boolean isCorrect) {
        this.id = id;
        this.statement = statement;
        this.isCorrect = isCorrect;

    }
    public Answer(Integer id, String statement) {
        this.id = id;
        this.statement = statement;
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
}
