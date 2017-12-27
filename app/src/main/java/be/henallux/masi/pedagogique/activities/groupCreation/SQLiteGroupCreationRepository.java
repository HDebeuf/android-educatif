package be.henallux.masi.pedagogique.activities.groupCreation;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.Toast;

import java.util.ArrayList;

import be.henallux.masi.pedagogique.R;
import be.henallux.masi.pedagogique.dao.sqlite.SQLiteHelper;
import be.henallux.masi.pedagogique.dao.sqlite.entities.CategoryEntity;
import be.henallux.masi.pedagogique.dao.sqlite.entities.UserEntity;
import be.henallux.masi.pedagogique.model.Category;
import be.henallux.masi.pedagogique.model.User;

/**
 * Created by haubo on 12/27/2017.
 */

public class SQLiteGroupCreationRepository implements IGroupCreationRepository {
    private Context context;
    private SQLiteDatabase db;

    public SQLiteGroupCreationRepository(Context context) {
        this.context = context;
        getDB();
    }

    public void getDB(){
        this.db = SQLiteHelper.getDatabaseInstance(this.context);
    }

    public Category getCategoryOfUser(int categoryId) {
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
    @Override
    public ArrayList<User> GetUsersByCaterogy(int categoryId,int userHimselfId) {
        ArrayList<User> usersList = null;
        SQLiteDatabase db = SQLiteHelper.getDatabaseInstance(context);
        Cursor cursor = db.query(UserEntity.TABLE,
                new String[]{UserEntity.COLUMN_ID, UserEntity.COLUMN_USERNAME, UserEntity.COLUMN_FIRSTNAME,UserEntity.COLUMN_LASTNAME,UserEntity.COLUMN_PASSWORDHASH,UserEntity.COLUMN_GENDER,UserEntity.COLUMN_URI_AVATAR, UserEntity.COLUMN_FK_CATEGORY},
                UserEntity.COLUMN_USERNAME + "=?" + " AND " + UserEntity.COLUMN_ID + "!=?",
                new String[]{String.valueOf(categoryId),String.valueOf(userHimselfId)},
                null, null, null);

        if(cursor.getCount() == 0) Toast.makeText(context, context.getResources().getString(R.string.group_no_camarade), Toast.LENGTH_SHORT).show();

        cursor.moveToFirst();
while(cursor.moveToNext()) {
    int userId = cursor.getInt(0);
    String userName = cursor.getString(1);
    String firstName = cursor.getString(2);
    String lastName = cursor.getString(3);
    String passwordHash = cursor.getString(4);
    int gender = cursor.getInt(5);
    String avatarURI = cursor.getString(6);
    Uri uri = null;
    if (!TextUtils.isEmpty(avatarURI)) {
        uri = Uri.parse(avatarURI);
    }
    Category cat = getCategoryOfUser(cursor.getInt(7));
    User toInsert = new User(userId,userName,firstName,lastName,passwordHash,gender,uri,cat,null,null);
    usersList.add(toInsert);
}
        cursor.close();
return usersList;
    }
}
