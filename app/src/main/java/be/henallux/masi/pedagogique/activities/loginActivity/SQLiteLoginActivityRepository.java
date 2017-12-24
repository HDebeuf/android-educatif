package be.henallux.masi.pedagogique.activities.loginActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import be.henallux.masi.pedagogique.activities.mapActivity.Location;
import be.henallux.masi.pedagogique.dao.sqlite.SQLiteHelper;
import be.henallux.masi.pedagogique.dao.sqlite.entities.UserEntity;
import be.henallux.masi.pedagogique.model.User;

/**
 * Created by haubo on 12/22/2017.
 */

public class SQLiteLoginActivityRepository implements ILoginActivityRepository{
    private Context context;
    private SQLiteDatabase db;
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
    public int getID(String log) {
        int idfound = 0;
        boolean found = false;
        String firstname, lastname, login;
        String query = "SELECT " + UserEntity.COLUMN_ID +  "," +  UserEntity.COLUMN_FIRSTNAME + ","
                + UserEntity.COLUMN_LASTNAME + " FROM " + UserEntity.TABLE;
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext() && idfound == 0) {
            firstname = cursor.getString(cursor.getColumnIndex(UserEntity.COLUMN_FIRSTNAME));
            lastname = cursor.getString(cursor.getColumnIndex(UserEntity.COLUMN_LASTNAME));
            login = firstname.concat(lastname);
            if(log.equals(login)){
                idfound = cursor.getInt(cursor.getColumnIndex(UserEntity.COLUMN_ID));
            }
        }
        return idfound;
    }
    @Override
    public String getPwdHash(int id) {
        String pwd;
        String query = "SELECT " + UserEntity.COLUMN_PASSWORDHASH + " FROM " + UserEntity.TABLE + " WHERE " +
                UserEntity.COLUMN_ID + " = " + id;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToNext();
        pwd = cursor.getString(cursor.getColumnIndex(UserEntity.COLUMN_PASSWORDHASH));
        return pwd;
    }


    public SQLiteLoginActivityRepository(Context context) {
        this.context = context;
        getDB();
    }
}
