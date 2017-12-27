package be.henallux.masi.pedagogique.model;

import android.net.Uri;

import java.util.ArrayList;

/**
 * Created by Le Roi Arthur on 17-12-17.
 */

public class User {

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

    public Integer getId() {return id;}

    public void setId(Integer id) {this.id = id;}

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
}
