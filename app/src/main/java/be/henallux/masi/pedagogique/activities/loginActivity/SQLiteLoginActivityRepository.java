package be.henallux.masi.pedagogique.activities.loginActivity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import be.henallux.masi.pedagogique.activities.mapActivity.Location;
import be.henallux.masi.pedagogique.dao.sqlite.SQLiteHelper;

/**
 * Created by haubo on 12/22/2017.
 */

public class SQLiteLoginActivityRepository implements ILoginActivityRepository{
    private Context context;
    private SQLiteDatabase db;
    public void getDB(){
        this.db = SQLiteHelper.getDatabaseInstance(this.context);
    }
    public void createAllUsers() {
        getDB();
        db.close();
        }

    public SQLiteLoginActivityRepository(Context context) {
        this.context = context;
    }
}
