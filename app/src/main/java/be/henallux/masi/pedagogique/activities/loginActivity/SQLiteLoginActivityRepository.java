package be.henallux.masi.pedagogique.activities.loginActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import be.henallux.masi.pedagogique.activities.mapActivity.Location;
import be.henallux.masi.pedagogique.dao.sqlite.SQLiteHelper;
import be.henallux.masi.pedagogique.dao.sqlite.entities.UserEntity;

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
    public void ifVoid() {
        //https://stackoverflow.com/questions/18097748/how-to-get-row-count-in-sqlite-using-android
        int count;
        String query = "SELECT * FROM " + UserEntity.TABLE;
        getDB();
        Cursor cursor = db.rawQuery(query,null);
        count = cursor.getCount();
        db.close();
        if(count==0){
            Log.i("noUser","We're going to add users");
        }
        else{
        }
    }

    public SQLiteLoginActivityRepository(Context context) {
        this.context = context;
    }
}
