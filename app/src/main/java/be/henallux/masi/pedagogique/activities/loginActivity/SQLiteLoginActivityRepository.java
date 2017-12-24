package be.henallux.masi.pedagogique.activities.loginActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;

import be.henallux.masi.pedagogique.activities.mapActivity.LocationEntity;
import be.henallux.masi.pedagogique.dao.sqlite.SQLiteHelper;
import be.henallux.masi.pedagogique.dao.sqlite.entities.UserEntity;
import be.henallux.masi.pedagogique.model.User;
import be.henallux.masi.pedagogique.utils.ICryptographyService;
import be.henallux.masi.pedagogique.utils.SHA256CryptographyService;

/**
 * Created by haubo on 12/22/2017.
 */

public class SQLiteLoginActivityRepository implements ILoginActivityRepository{
    private Context context;
    private SQLiteDatabase db;
    private ICryptographyService cryptographyService;


    public SQLiteLoginActivityRepository(Context context) {
        this.context = context;
        this.cryptographyService = new SHA256CryptographyService();
        getDB();
    }

    public void getDB(){
        this.db = SQLiteHelper.getDatabaseInstance(this.context);
    }

    /**
     * Used to probe if the user table contains data or not, if not, adding Exemples users
     * @return
     */
    @Override
    public int getCount() {
        //https://stackoverflow.com/questions/18097748/how-to-get-row-count-in-sqlite-using-android
        int count;
        String query = "SELECT * FROM " + UserEntity.TABLE;
        Cursor cursor = db.rawQuery(query,null);
        count = cursor.getCount();
        return count;
    }

    @Override
    public User getUserByUsername(String username) {
        SQLiteDatabase db = SQLiteHelper.getDatabaseInstance(context);
        Cursor cursor = db.query(UserEntity.TABLE,
                new String[]{UserEntity.COLUMN_ID, UserEntity.COLUMN_USERNAME, UserEntity.COLUMN_FIRSTNAME,UserEntity.COLUMN_LASTNAME,UserEntity.COLUMN_PASSWORDHASH,UserEntity.COLUMN_GENDER,UserEntity.COLUMN_URI_AVATAR},
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

        cursor.close();
        return new User(userId,userName,firstName,lastName,passwordHash,gender,uri,null,null);
    }

    @Override
    public boolean accessGranted(String username, String password) {
        User foundUser = getUserByUsername(username);
        if(foundUser == null) return false;
        return (cryptographyService.hashPassword(password).equals(foundUser.getPasswordHash()));
    }

}
