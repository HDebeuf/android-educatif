package be.henallux.masi.pedagogique.dao.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;

import be.henallux.masi.pedagogique.activities.musicalActivity.makeMusic.entities.InstrumentEntity;
import be.henallux.masi.pedagogique.dao.interfaces.IQuestionRepository;
import be.henallux.masi.pedagogique.dao.interfaces.IQuestionnaireRepository;
import be.henallux.masi.pedagogique.dao.sqlite.entities.QuestionnaireEntity;
import be.henallux.masi.pedagogique.model.Question;
import be.henallux.masi.pedagogique.model.Questionnaire;

/**
 * Created by Angèle Guillon on 04/01/18.
 */

public class SQLiteQuestionnaireRepository implements IQuestionnaireRepository {

    private Context context;
    private IQuestionRepository questionRepository = new SQLiteQuestionRepository(context);

    public SQLiteQuestionnaireRepository(Context context) {
        this.context = context;
    }


    @Override
    public ArrayList<Question> getAllQuestionOfQuestionnaire(Questionnaire questionnaire) {
        return null;
    }

    @Override
    public Questionnaire getQuestionnaireById(int idQuestionnaire) {
        SQLiteDatabase db = SQLiteHelper.getDatabaseInstance(context);

        Cursor cursor = db.query(QuestionnaireEntity.TABLE,
                new String[]{QuestionnaireEntity.COLUMN_ID,
                        QuestionnaireEntity.COLUMN_STATEMENT},
                QuestionnaireEntity.COLUMN_ID + "=?",new String[]{String.valueOf(idQuestionnaire)},
                null, null, null);

        if (cursor.getCount() == 0) return null;
        cursor.moveToFirst();

        int id = cursor.getInt(0);
        String statement = cursor.getString(1);

        ArrayList<Question> questions = questionRepository.getQuestionsOfQuestionnaire(idQuestionnaire);
        Questionnaire q = new Questionnaire(idQuestionnaire, statement, questions);

        cursor.close();

        return q;
    }
}
