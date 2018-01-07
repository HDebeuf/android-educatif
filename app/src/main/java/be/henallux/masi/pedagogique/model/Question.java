package be.henallux.masi.pedagogique.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Le Roi Arthur on 17-12-17.
 *
 */

public class Question implements Parcelable {
    private Integer id;
    private String statement;
    //private Questionnaire questionnaire;
    private ArrayList<Answer> answers;
    private int type;

    public Question(Integer id, String statement, int type, ArrayList<Answer> answers) {
        this.id = id;
        this.statement = statement;
        this.type = type;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    /*
    public Questionnaire getQuestionnaire() {
        return questionnaire;
    }

    public void setQuestionnaire(Questionnaire questionnaire) {
        this.questionnaire = questionnaire;
    }
    */

    public ArrayList<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<Answer> answers) {
        this.answers = answers;
    }

    protected Question(Parcel in) {
        id = in.readByte() == 0x00 ? null : in.readInt();
        statement = in.readString();
        if (in.readByte() == 0x01) {
            answers = new ArrayList<Answer>();
            in.readList(answers, Answer.class.getClassLoader());
        } else {
            answers = null;
        }
        type = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(id);
        }
        dest.writeString(statement);
        if (answers == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(answers);
        }
        dest.writeInt(type);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Question> CREATOR = new Parcelable.Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };
}