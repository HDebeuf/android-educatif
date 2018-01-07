package be.henallux.masi.pedagogique.dao.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import be.henallux.masi.pedagogique.dao.interfaces.IAnswerRepository;
import be.henallux.masi.pedagogique.dao.interfaces.IAnswerToQuestionRepository;
import be.henallux.masi.pedagogique.dao.sqlite.entities.AnswerToQuestionEntity;
import be.henallux.masi.pedagogique.dao.sqlite.entities.UserEntity;
import be.henallux.masi.pedagogique.model.Answer;

/**
 * Created by hendrikdebeuf2 on 7/01/18.
 */

public class SQLiteAnswerToQuestionRepository implements IAnswerToQuestionRepository {

    private Context context;
    private SQLiteDatabase db;

    public SQLiteAnswerToQuestionRepository(Context context) {
        this.context = context;
        this.db = SQLiteHelper.getDatabaseInstance(this.context);
    }

    public void addAnswer (Answer answer) {
        ContentValues values = new ContentValues();
        values.put(AnswerToQuestionEntity.COLUMN_FK_ANSWER,answer.getId());
        values.put(AnswerToQuestionEntity.COLUMN_FK_QUESTION,answer.getQuestionId());
        values.put(AnswerToQuestionEntity.COLUMN_FK_RESULT,answer.isCorrect());
        db.insert(AnswerToQuestionEntity.TABLE, null, values);
    }

}
