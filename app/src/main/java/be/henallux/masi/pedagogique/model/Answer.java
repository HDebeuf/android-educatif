package be.henallux.masi.pedagogique.model;

/**
 * Created by Le Roi Arthur on 17-12-17.
 */

public class Answer {

    private Integer id;
    private String statement;
<<<<<<< HEAD
    private Question question;
    private boolean isCorrect;

    public Answer(Integer id, String statement, Question question, boolean isCorrect) {
        this.id = id;
        this.statement = statement;
        this.question = question;
=======
    private boolean isCorrect;

    public Answer(Integer id, String statement, boolean isCorrect) {
        this.id = id;
        this.statement = statement;
>>>>>>> 9632e73b89e2e94b3ad032d8939b599119300f66
        this.isCorrect = isCorrect;

    }
    public Answer(Integer id, String statement) {
        this.id = id;
        this.statement = statement;
    }
<<<<<<< HEAD

    public Question getQuestion() {
        return question;
    }
=======
>>>>>>> 9632e73b89e2e94b3ad032d8939b599119300f66



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
