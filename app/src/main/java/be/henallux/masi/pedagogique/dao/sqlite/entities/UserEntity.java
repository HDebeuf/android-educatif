package be.henallux.masi.pedagogique.dao.sqlite.entities;

/**
 * Created by Le Roi Arthur on 17-12-17.
 */

public class UserEntity {
    public static final String TABLE = "User";
    public static final String COLUMN_ID = "idUser";
    public static final String COLUMN_FIRSTNAME = "firstname";
    public static final String COLUMN_LASTNAME = "lastname";
    public static final String COLUMN_PASSWORDHASH = "passwordHash";
    public static final String COLUMN_GENRE = "genre";
    public static final String COLUMN_URI_AVATAR = "URIAvater";
    public static final String COLUMN_FK_CLASS = "Class_idClass";
}
