package be.henallux.masi.pedagogique.dao.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import be.henallux.masi.pedagogique.activities.musicalActivity.makeMusic.entities.InstrumentEntity;
import be.henallux.masi.pedagogique.dao.interfaces.IAnswerRepository;
import be.henallux.masi.pedagogique.dao.interfaces.IQuestionRepository;
import be.henallux.masi.pedagogique.dao.sqlite.SQLiteHelper;
import be.henallux.masi.pedagogique.dao.sqlite.entities.AnswerEntity;
import be.henallux.masi.pedagogique.model.Answer;
import be.henallux.masi.pedagogique.model.Question;

/**
 * Created by Le Roi Arthur on 06-01-18.
 */

public class SQLiteAnswerRepository implements IAnswerRepository {

    private Context context;
    private static SQLiteAnswerRepository instance;

    private SQLiteAnswerRepository(Context context) {
        this.context = context;
    }


    public static SQLiteAnswerRepository getInstance(Context ctx){
        if(instance == null){
            instance = new SQLiteAnswerRepository(ctx);
        }
        return instance;
    }


    @Override
    public ArrayList<Answer> getAnswersOfQuestion(int questionId) {
        SQLiteDatabase db = SQLiteHelper.getDatabaseInstance(context);

        ArrayList<Answer> answers = new ArrayList<>();

        Cursor cursor = db.query(AnswerEntity.TABLE,
                new String[]{AnswerEntity.COLUMN_ID,
                        AnswerEntity.COLUMN_STATEMENT,
                        AnswerEntity.COLUMN_IS_TRUE,
                        AnswerEntity.COLUMN_FK_QUESTION},
                AnswerEntity.COLUMN_FK_QUESTION + "=?", new String[]{String.valueOf(questionId)},
                null, null, null);

        if (cursor.getCount() == 0) return null;
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int idAnswer = cursor.getInt(0);
            String statement = cursor.getString(1);
            boolean isTrue = cursor.getInt(2) == 1;
            Question question = SQLiteQuestionRepository.getInstance(context).getQuestionById(questionId,false);

            answers.add(new Answer(idAnswer, statement, isTrue, question));
            cursor.moveToNext();
        }

        cursor.close();
        return answers;
    }

}
