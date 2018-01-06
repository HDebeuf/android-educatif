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

    protected Answer(Parcel in) {
        id = in.readByte() == 0x00 ? null : in.readInt();
        statement = in.readString();
        isCorrect = in.readByte() != 0x00;
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