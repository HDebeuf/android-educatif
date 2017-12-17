package be.henallux.masi.pedagogique.model;

import java.util.ArrayList;

/**
 * Created by Le Roi Arthur on 17-12-17.
 */

public class Group {

    private Integer id;
    private ArrayList<User> members;
    private Group group;

    public Group(Integer id, ArrayList<User> members, Group group) {
        this.id = id;
        this.members = members;
        this.group = group;
    }

    public ArrayList<User> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<User> members) {
        this.members = members;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
