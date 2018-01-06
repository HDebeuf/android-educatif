package be.henallux.masi.pedagogique.dao.sqlite.entities;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import be.henallux.masi.pedagogique.activities.musicalActivity.makeMusic.entities.InstrumentEntity;
import be.henallux.masi.pedagogique.dao.interfaces.IAnswerRepository;
import be.henallux.masi.pedagogique.dao.sqlite.SQLiteHelper;
import be.henallux.masi.pedagogique.model.Answer;
import be.henallux.masi.pedagogique.model.Question;

/**
 * Created by Le Roi Arthur on 06-01-18.
 */

public class SQLiteAnswerRepository implements IAnswerRepository {

    private Context context;

    public SQLiteAnswerRepository(Context context) {
        this.context = context;
    }

    @Override
    public ArrayList<Answer> getAnswersOfQuestion(int questionID) {
        SQLiteDatabase db = SQLiteHelper.getDatabaseInstance(context);

        ArrayList<Answer> answers = new ArrayList<>();

        Cursor cursor = db.query(AnswerEntity.TABLE,
                new String[]{AnswerEntity.COLUMN_ID,
                        AnswerEntity.COLUMN_STATEMENT,
                        AnswerEntity.COLUMN_IS_TRUE,
                        AnswerEntity.COLUMN_FK_QUESTION},
                AnswerEntity.COLUMN_FK_QUESTION + "=?",new String[]{String.valueOf(questionID)},
                null, null, null);

        if(cursor.getCount() == 0) return null;
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            int idAnswer = cursor.getInt(0);
            String statement = cursor.getString(1);
            boolean isTrue = cursor.getInt(2) == 1;

            answers.add(new Answer(idAnswer,statement,isTrue));
        }


        cursor.close();
        return answers;
    }
}
