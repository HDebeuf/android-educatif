package be.henallux.masi.pedagogique.dao.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import org.apache.commons.lang3.NotImplementedException;

import java.util.ArrayList;

import be.henallux.masi.pedagogique.dao.interfaces.ICategoryRepository;
import be.henallux.masi.pedagogique.dao.interfaces.IClassRepository;
import be.henallux.masi.pedagogique.dao.sqlite.entities.ActivityEntity;
import be.henallux.masi.pedagogique.dao.sqlite.entities.CategoryEntity;
import be.henallux.masi.pedagogique.dao.sqlite.entities.UserEntity;
import be.henallux.masi.pedagogique.model.Activity;
import be.henallux.masi.pedagogique.model.Category;
import be.henallux.masi.pedagogique.model.User;
import be.henallux.masi.pedagogique.model.Class;

/**
 * Created by Le Roi Arthur on 29-12-17.
 */

public class SQLiteCategoryRepository implements ICategoryRepository {

    private Context context;
    private IClassRepository classRepository;
    private static SQLiteCategoryRepository instance;

    private SQLiteCategoryRepository(Context applicationContext) {
        this.context = applicationContext;
        this.classRepository = SQLiteClassRepository.getInstance(applicationContext);
    }

    public static SQLiteCategoryRepository getInstance(Context context){
        if(instance == null){
            instance = new SQLiteCategoryRepository(context);
        }
        return  instance;
    }

    @Override
    public Category getCategoryById(int id) {
        throw new NotImplementedException("Not implemented yet");
    }

    @Override
    public ArrayList<User> getAllUsersOfCategory(Category c) {
        SQLiteDatabase database = SQLiteHelper.getDatabaseInstance(context);
        ArrayList<User> users = new ArrayList<>();
        Cursor cursor = database.query(UserEntity.TABLE,
                new String[]{UserEntity.COLUMN_ID,
                        UserEntity.COLUMN_USERNAME,
                        UserEntity.COLUMN_FIRSTNAME,
                        UserEntity.COLUMN_LASTNAME,
                        UserEntity.COLUMN_PASSWORDHASH,
                        UserEntity.COLUMN_GENDER,
                        UserEntity.COLUMN_URI_AVATAR,
                        UserEntity.COLUMN_FK_CATEGORY,
                        UserEntity.COLUMN_FK_CLASS
                        },
                UserEntity.COLUMN_FK_CATEGORY + "=?",
                new String[]{String.valueOf(c.getId())}, null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {

            int id = cursor.getInt(0);
            String username = cursor.getString(1);
            String firstname = cursor.getString(2);
            String lastName = cursor.getString(3);
            String passwordHash = cursor.getString(4);
            int gender = cursor.getInt(5);

            String uriString = cursor.getString(6);
            Uri uriAvatar = null;
            if(!TextUtils.isEmpty(uriString)){
                uriAvatar = Uri.parse(uriString);
            }

            int fkCategory = cursor.getInt(7);
            int fkClass = cursor.getInt(8);

            Category category = c;
            Class _class = classRepository.getClassById(fkClass);

            users.add(new User(id,username,firstname,lastName,passwordHash,gender,uriAvatar,category,_class,null));
            cursor.moveToNext();
        }

        cursor.close();
        return users;
    }

    @Override
    public ArrayList<Category> getAllCategories() {
        SQLiteDatabase database = SQLiteHelper.getDatabaseInstance(context);
        ArrayList<Category> categories = new ArrayList<>();
        Cursor cursor = database.query(CategoryEntity.TABLE,
                new String[]{CategoryEntity.COLUMN_ID,
                        CategoryEntity.COLUMN_DESCRIPTION,
                        CategoryEntity.COLUMN_AGE_MAX,
                        CategoryEntity.COLUMN_AGE_MIN},
                null, null, null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(0);
            String description = cursor.getString(1);
            int ageMax = cursor.getInt(2);
            int ageMin = cursor.getInt(3);
            categories.add(new Category(id,description,ageMin,ageMax));
            cursor.moveToNext();
        }

        cursor.close();
        return categories;
    }
}
