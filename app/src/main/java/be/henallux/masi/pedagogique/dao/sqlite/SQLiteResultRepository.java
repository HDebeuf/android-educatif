package be.henallux.masi.pedagogique.dao.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;

import java.util.ArrayList;

import be.henallux.masi.pedagogique.dao.interfaces.IResultRepository;
import be.henallux.masi.pedagogique.dao.sqlite.entities.UserEntity;
import be.henallux.masi.pedagogique.model.Category;
import be.henallux.masi.pedagogique.model.Result;
import be.henallux.masi.pedagogique.model.User;

/**
 * Created by hendrikdebeuf2 on 29/12/17.
 */

public class SQLiteResultRepository implements IResultRepository {

    private Context context;
    private SQLiteDatabase db;

    private SQLiteResultRepository(Context applicationContext) {
        this.context = applicationContext;
    }

    private ArrayList<Result> getAllResults(int groupId){
        // TO DO get the results of each quizz
        return null;
    }

    //TO DO set result ? --> if add to db at end of quizz
    // OR AnswerRepository to add each answer

}
