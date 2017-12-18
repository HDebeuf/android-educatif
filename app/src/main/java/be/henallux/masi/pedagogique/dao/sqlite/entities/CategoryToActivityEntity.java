package be.henallux.masi.pedagogique.dao.sqlite.entities;

/**
 * Created by Le Roi Arthur on 18-12-17.
 */

public class CategoryToActivityEntity {
    public static final String TABLE = "Category_has_educativeActivity";
    public static final String COLUMN_ID = "idCategoryHasEducativeActivity";
    public static final String COLUMN_FK_CATEGORY = "Category_idCategory";
    public static final String COLUMN_FK_ACTIVITY = "EducativeActivity_idActivity";
}
