package be.henallux.masi.pedagogique.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Le Roi Arthur on 17-12-17.
 */

public class User implements Parcelable {

    private Integer id;
    private String username;
    private String firstName;
    private String lastName;
    private String passwordHash;
    private int genre;
    private Uri avatarUri;
    private Class _class;
    private Category category;
    private ArrayList<Group> groups;
    private boolean isSelected; //Used for the recycler view and checkboxes

    public User(Integer id, String username, String firstName, String lastName, String passwordHash, int genre, Uri avatarUri, Category category, Class _class, ArrayList<Group> groups) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.passwordHash = passwordHash;
        this.genre = genre;
        this.avatarUri = avatarUri;
        this._class = _class;
        this.groups = groups;
        this.category = category;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public int getGenre() {
        return genre;
    }

    public void setGenre(int genre) {
        this.genre = genre;
    }

    public Uri getAvatarUri() {
        return avatarUri;
    }

    public void setAvatarUri(Uri avatarUri) {
        this.avatarUri = avatarUri;
    }

    public Class get_class() {
        return _class;
    }

    public void set_class(Class _class) {
        this._class = _class;
    }

    public ArrayList<Group> getGroups() {
        return groups;
    }

    public void setGroups(ArrayList<Group> groups) {
        this.groups = groups;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public boolean getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    protected User(Parcel in) {
        id = in.readByte() == 0x00 ? null : in.readInt();
        username = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        passwordHash = in.readString();
        genre = in.readInt();
        avatarUri = (Uri) in.readValue(Uri.class.getClassLoader());
        _class = (Class) in.readValue(Class.class.getClassLoader());
        category = (Category) in.readValue(Category.class.getClassLoader());
        if (in.readByte() == 0x01) {
            groups = new ArrayList<Group>();
            in.readList(groups, Group.class.getClassLoader());
        } else {
            groups = null;
        }
        isSelected = in.readByte() != 0x00;
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
        dest.writeString(username);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(passwordHash);
        dest.writeInt(genre);
        dest.writeValue(avatarUri);
        dest.writeValue(_class);
        dest.writeValue(category);
        if (groups == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(groups);
        }
        dest.writeByte((byte) (isSelected ? 0x01 : 0x00));
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}