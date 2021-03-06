package be.henallux.masi.pedagogique.dao.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;

import java.util.ArrayList;

import be.henallux.masi.pedagogique.dao.interfaces.IClassRepository;
import be.henallux.masi.pedagogique.dao.sqlite.entities.CategoryEntity;
import be.henallux.masi.pedagogique.dao.sqlite.entities.ClassEntity;
import be.henallux.masi.pedagogique.dao.sqlite.entities.UserEntity;
import be.henallux.masi.pedagogique.model.Category;
import be.henallux.masi.pedagogique.model.Class;
import be.henallux.masi.pedagogique.model.User;

/**
 * Created by Le Roi Arthur on 29-12-17.
 */

public class SQLiteClassRepository implements IClassRepository {

    private Context context;
    private static SQLiteClassRepository instance;

    private SQLiteClassRepository(Context applicationContext) {
        this.context = applicationContext;
    }

    public static SQLiteClassRepository getInstance(Context ctx){
        if(instance == null){
            instance = new SQLiteClassRepository(ctx);
        }
        return instance;
    }

    @Override
    public Class getClassById(int id) {
        SQLiteDatabase database = SQLiteHelper.getDatabaseInstance(context);
        Cursor cursor = database.query(ClassEntity.TABLE,
                new String[]{ClassEntity.COLUMN_ID,ClassEntity.COLUMN_DESCRIPTION
                },
                ClassEntity.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null);

        if(cursor.getCount() == 0) return null;
        cursor.moveToFirst();

        int idClass = cursor.getInt(0);
        String description = cursor.getString(1);
        Class foundClass = new Class(idClass,description);
        cursor.close();

        return foundClass;
    }

    @Override
    public ArrayList<Class> getAllClasses() {
        SQLiteDatabase database = SQLiteHelper.getDatabaseInstance(context);
        ArrayList<Class> classes = new ArrayList<>();
        Cursor cursor = database.query(ClassEntity.TABLE,
                new String[]{ClassEntity.COLUMN_ID,
                        ClassEntity.COLUMN_DESCRIPTION},
                null, null, null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(0);
            String description = cursor.getString(1);
            classes.add(new Class(id,description));
            cursor.moveToNext();
        }

        cursor.close();
        return classes;
    }
}
