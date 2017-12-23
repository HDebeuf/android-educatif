package be.henallux.masi.pedagogique.model;

import java.util.ArrayList;

/**
 * Created by Le Roi Arthur on 17-12-17.
 */

public class Group {

    private Integer id;
    private ArrayList<User> members;

    public Group(Integer id, ArrayList<User> members) {
        this.id = id;
        this.members = members;
    }

    public ArrayList<User> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<User> members) {
        this.members = members;
    }
}
