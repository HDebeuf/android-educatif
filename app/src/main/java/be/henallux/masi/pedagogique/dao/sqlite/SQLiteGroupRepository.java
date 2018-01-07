package be.henallux.masi.pedagogique.dao.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLInput;
import java.util.ArrayList;

import be.henallux.masi.pedagogique.dao.interfaces.IGroupRepository;
import be.henallux.masi.pedagogique.dao.sqlite.entities.CategoryToActivityEntity;
import be.henallux.masi.pedagogique.dao.sqlite.entities.GroupEntity;
import be.henallux.masi.pedagogique.dao.sqlite.entities.UserToGroupEntity;
import be.henallux.masi.pedagogique.model.Group;
import be.henallux.masi.pedagogique.model.User;

/**
 * Created by Le Roi Arthur on 28-12-17.
 */

public class SQLiteGroupRepository implements IGroupRepository {

    private Context context;
    private static SQLiteGroupRepository instance;

    private SQLiteGroupRepository(Context context) {
        this.context = context;
    }

    public static SQLiteGroupRepository getInstance(Context ctx){
        if(instance == null){
            instance = new SQLiteGroupRepository(ctx);
        }
        return instance;
    }

    @Override
    public Group createGroup(ArrayList<User> users) {
        ContentValues values = new ContentValues();
        SQLiteDatabase db = SQLiteHelper.getDatabaseInstance(context);


        //TODO : find a better way to do this, dammit ..
        db.execSQL("INSERT INTO " + GroupEntity.TABLE + " DEFAULT VALUES"); //Insert a new group
        Cursor cursor = db.rawQuery("SELECT last_insert_rowid()",null);
        cursor.moveToFirst();
        int groupId = cursor.getInt(0);

        for(User user : users){
            values.put(UserToGroupEntity.COLUMN_FK_GROUP,groupId);
            values.put(UserToGroupEntity.COLUMN_FK_USER,user.getId());
            db.insert(UserToGroupEntity.TABLE,null,values);
            values.clear();
        }
        Group usersGroup = new Group(groupId,users);
        return usersGroup;
    }
}
