package be.henallux.masi.pedagogique.model;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Le Roi Arthur on 17-12-17.
 */

public class Activity implements Parcelable {
    private Integer id;
    private String name;
    private Category category;
    private java.lang.Class associatedClass;

    public Activity(){};

    public Activity(Integer id, String name, java.lang.Class associatedClass) {
        this.id = id;
        this.name = name;
        this.associatedClass = associatedClass;
    }

    public Activity(Integer id, String name, Category category, java.lang.Class associatedClass) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.associatedClass = associatedClass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public java.lang.Class getAssociatedClass() {
        return associatedClass;
    }

    public void setAssociatedClass(java.lang.Class associatedClass) {
        this.associatedClass = associatedClass;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    protected Activity(Parcel in) {
        id = in.readByte() == 0x00 ? null : in.readInt();
        name = in.readString();
        category = (Category) in.readValue(Category.class.getClassLoader());
        associatedClass = (java.lang.Class) in.readValue(java.lang.Class.class.getClassLoader());
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
        dest.writeString(name);
        dest.writeValue(category);
        dest.writeValue(associatedClass);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Activity> CREATOR = new Parcelable.Creator<Activity>() {
        @Override
        public Activity createFromParcel(Parcel in) {
            return new Activity(in);
        }

        @Override
        public Activity[] newArray(int size) {
            return new Activity[size];
        }
    };
}