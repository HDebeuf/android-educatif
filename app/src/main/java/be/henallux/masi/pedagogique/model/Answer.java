package be.henallux.masi.pedagogique.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Le Roi Arthur on 17-12-17.
 */

public class Answer implements Parcelable {

    private Integer id;
    private String statement;
    private boolean isCorrect;
    private int point;
    private Question question;

    public Answer(Integer id, String statement, boolean isCorrect, Question question) {
        this.id = id;
        this.statement = statement;
        this.isCorrect = isCorrect;
        this.question = question;
    }


    public Answer(Integer id, String statement, boolean isCorrect, int point, Question question) {
        this.id = id;
        this.statement = statement;
        this.isCorrect = isCorrect;
        this.question = question;
        this.point = point;
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

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }



    protected Answer(Parcel in) {
        id = in.readByte() == 0x00 ? null : in.readInt();
        statement = in.readString();
        isCorrect = in.readByte() != 0x00;
        point = in.readInt();
        question = (Question) in.readValue(Question.class.getClassLoader());
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
        dest.writeByte((byte) (isCorrect ? 0x01 : 0x00));
        dest.writeInt(point);
        dest.writeValue(question);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Answer> CREATOR = new Parcelable.Creator<Answer>() {
        @Override
        public Answer createFromParcel(Parcel in) {
            return new Answer(in);
        }

        @Override
        public Answer[] newArray(int size) {
            return new Answer[size];
        }
    };
}