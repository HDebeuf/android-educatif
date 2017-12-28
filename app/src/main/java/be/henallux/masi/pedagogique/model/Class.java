package be.henallux.masi.pedagogique.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.github.javafaker.Faker;

/**
 * Created by Le Roi Arthur on 17-12-17.
 */

public class Class implements Parcelable {
    private Integer id;
    private String descritpion;

    public Class(Integer id, String descritpion) {
        this.id = id;

        this.descritpion = descritpion;
    }

    public String getDescritpion() {
        return descritpion;
    }

    public void setDescritpion(String descritpion) {
        this.descritpion = descritpion;
    }

    protected Class(Parcel in) {
        id = in.readByte() == 0x00 ? null : in.readInt();
        descritpion = in.readString();
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
        dest.writeString(descritpion);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Class> CREATOR = new Parcelable.Creator<Class>() {
        @Override
        public Class createFromParcel(Parcel in) {
            return new Class(in);
        }

        @Override
        public Class[] newArray(int size) {
            return new Class[size];
        }
    };
}