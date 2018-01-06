package be.henallux.masi.pedagogique.dao.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import be.henallux.masi.pedagogique.activities.musicalActivity.makeMusic.entities.InstrumentEntity;
import be.henallux.masi.pedagogique.dao.interfaces.IAnswerRepository;
import be.henallux.masi.pedagogique.dao.interfaces.IQuestionRepository;
import be.henallux.masi.pedagogique.dao.sqlite.entities.QuestionEntity;
import be.henallux.masi.pedagogique.model.Answer;
import be.henallux.masi.pedagogique.model.Question;

/**
 * Created by Le Roi Arthur on 06-01-18.
 */

public class SQLiteQuestionRepository implements IQuestionRepository {

    private IAnswerRepository answerRepository;
    private Context context;

    public SQLiteQuestionRepository(Context context) {
        this.context = context;
    }

    @Override
    public ArrayList<Question> getQuestionsOfQuestionnaire(int idQuestionnaire) {
        SQLiteDatabase db = SQLiteHelper.getDatabaseInstance(context);

        ArrayList<Question> questions = new ArrayList<>();

        Cursor cursor = db.query(QuestionEntity.TABLE,
                new String[]{QuestionEntity.COLUMN_ID,
                        QuestionEntity.COLUMN_STATEMENT,
                        QuestionEntity.COLUMN_TYPE,
                        QuestionEntity.COLUMN_FK_QUESTIONNAIRE},
                InstrumentEntity.COLUMN_FK_QUESTIONNAIRE + "=?",new String[]{String.valueOf(idQuestionnaire)},
                null, null, null);

        if(cursor.getCount() == 0) return null;
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            int idQuestion = cursor.getInt(0);
            String statement = cursor.getString(1);
            int type = cursor.getInt(2);

            ArrayList<Answer> answers = answerRepository.getAnswersOfQuestion(idQuestion);
            questions.add(new Question(idQuestion,statement, type, answers));
        }


        cursor.close();
        return questions;
    }
}
