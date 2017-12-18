package be.henallux.masi.pedagogique.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Le Roi Arthur on 17-12-17.
 */

public class Category implements Parcelable {

    private Integer id;
    private String descritption;
    private int ageMin;
    private int ageMax;

    public Category(Integer id, String descritption, int ageMin, int ageMax) {
        this.id = id;
        this.descritption = descritption;
        this.ageMin = ageMin;
        this.ageMax = ageMax;
    }

    public String getDescritption() {
        return descritption;
    }

    public void setDescritption(String descritption) {
        this.descritption = descritption;
    }

    public int getAgeMin() {
        return ageMin;
    }

    public void setAgeMin(int ageMin) {
        this.ageMin = ageMin;
    }

    public int getAgeMax() {
        return ageMax;
    }

    public void setAgeMax(int ageMax) {
        this.ageMax = ageMax;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    protected Category(Parcel in) {
        id = in.readByte() == 0x00 ? null : in.readInt();
        descritption = in.readString();
        ageMin = in.readInt();
        ageMax = in.readInt();
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
        dest.writeString(descritption);
        dest.writeInt(ageMin);
        dest.writeInt(ageMax);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Category> CREATOR = new Parcelable.Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };
}