package be.henallux.masi.pedagogique.dao.sqlite.entities;

/**
 * Created by Le Roi Arthur on 17-12-17.
 */

public class UserToGroupEntity {
    public static final String TABLE = "User_has_Group";
    public static final String COLUMN_ID = "idUserHasGroup";
    public static final String COLUMN_FK_GROUP = "User_idUser";
    public static final String COLUMN_FK_USER = "Group_idGroup";
}
