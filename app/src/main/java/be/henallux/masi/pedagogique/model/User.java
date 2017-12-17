package be.henallux.masi.pedagogique.model;

import java.net.URI;
import java.util.ArrayList;

/**
 * Created by Le Roi Arthur on 17-12-17.
 */

public class User {

    private Integer id;
    private String firstName;
    private String lastName;
    private String passwordHash;
    private int genre;
    private URI avatarUri;
    private Class _class;
    private ArrayList<Group> groups;

    public User(Integer id, String firstName, String lastName, String passwordHash, int genre, URI avatarUri, Class _class, ArrayList<Group> groups) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.passwordHash = passwordHash;
        this.genre = genre;
        this.avatarUri = avatarUri;
        this._class = _class;
        this.groups = groups;
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

    public URI getAvatarUri() {
        return avatarUri;
    }

    public void setAvatarUri(URI avatarUri) {
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
}
