package be.henallux.masi.pedagogique.dao.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;

import be.henallux.masi.pedagogique.dao.interfaces.IUserRepository;
import be.henallux.masi.pedagogique.dao.sqlite.entities.CategoryEntity;
import be.henallux.masi.pedagogique.dao.sqlite.entities.UserEntity;
import be.henallux.masi.pedagogique.model.Category;
import be.henallux.masi.pedagogique.model.User;
import be.henallux.masi.pedagogique.utils.ICryptographyService;
import be.henallux.masi.pedagogique.utils.SHA256CryptographyService;

/**
 * Created by Le Roi Arthur on 28-12-17.
 */

public class SQLiteUserRepository implements IUserRepository{
    private Context context;
    private SQLiteDatabase db;
    private ICryptographyService cryptographyService;


    public SQLiteUserRepository(Context context) {
        this.context = context;
        this.cryptographyService = new SHA256CryptographyService();
        getDB();
    }

    public void getDB(){
        this.db = SQLiteHelper.getDatabaseInstance(this.context);
    }

    @Override
    public User getUserByUsername(String username) {
        SQLiteDatabase db = SQLiteHelper.getDatabaseInstance(context);
        Cursor cursor = db.query(UserEntity.TABLE,
                new String[]{UserEntity.COLUMN_ID, UserEntity.COLUMN_USERNAME, UserEntity.COLUMN_FIRSTNAME,UserEntity.COLUMN_LASTNAME,UserEntity.COLUMN_PASSWORDHASH,UserEntity.COLUMN_GENDER,UserEntity.COLUMN_URI_AVATAR, UserEntity.COLUMN_FK_CATEGORY},
                UserEntity.COLUMN_USERNAME + "=?",
                new String[]{username},
                null, null, null);

        if(cursor.getCount() == 0) return null;

        cursor.moveToFirst();

        int userId = cursor.getInt(0);
        String userName= cursor.getString(1);
        String firstName = cursor.getString(2);
        String lastName = cursor.getString(3);
        String passwordHash = cursor.getString(4);
        int gender = cursor.getInt(5);
        String avatarURI = cursor.getString(6);
        Uri uri = null;
        if(!TextUtils.isEmpty(avatarURI)){
            uri = Uri.parse(avatarURI);
        }
        Category cat = getCategoryOfUser(cursor.getInt(7));

        cursor.close();
        return new User(userId,userName,firstName,lastName,passwordHash,gender,uri,cat,null,null);
    }

    private Category getCategoryOfUser(int categoryId) {
        SQLiteDatabase db = SQLiteHelper.getDatabaseInstance(context);
        Cursor cursor = db.query(CategoryEntity.TABLE,
                new String[]{CategoryEntity.COLUMN_ID,CategoryEntity.COLUMN_AGE_MIN,CategoryEntity.COLUMN_AGE_MAX,CategoryEntity.COLUMN_DESCRIPTION},
                CategoryEntity.COLUMN_ID + "=?",
                new String[]{String.valueOf(categoryId)},
                null, null, null);

        if(cursor.getCount() == 0) return null;

        cursor.moveToFirst();

        int id = cursor.getInt(0);
        int ageMin = cursor.getInt(1);
        int ageMax = cursor.getInt(2);
        String description = cursor.getString(3);

        cursor.close();
        return new Category(id,description,ageMin,ageMax);
    }

    /**
     * Used to check the credentials of an account
     * @param username
     * @param password
     * @return the model User associated to these credentials if they are valid. Null if no user was found or the credentials were invalid
     */
    @Override
    public User accessGranted(String username, String password) {
        User foundUser = getUserByUsername(username);
        if(foundUser == null) return null;
        if(cryptographyService.hashPassword(password).equals(foundUser.getPasswordHash())){
            return foundUser;
        }
        else
        {
            return null;
        }
    }
}
