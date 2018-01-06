package be.henallux.masi.pedagogique.dao.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;

import be.henallux.masi.pedagogique.activities.mapActivity.ActivityMapBaseEntity;
import be.henallux.masi.pedagogique.activities.mapActivity.LocationEntity;
import be.henallux.masi.pedagogique.activities.musicalActivity.makeMusic.entities.InstrumentEntity;
import be.henallux.masi.pedagogique.dao.interfaces.IQuestionnaireRepository;
import be.henallux.masi.pedagogique.dao.sqlite.entities.ActivityEntity;
import be.henallux.masi.pedagogique.dao.sqlite.entities.AnswerEntity;
import be.henallux.masi.pedagogique.dao.sqlite.entities.QuestionEntity;
import be.henallux.masi.pedagogique.dao.sqlite.entities.QuestionnaireEntity;
import be.henallux.masi.pedagogique.model.Answer;
import be.henallux.masi.pedagogique.model.Question;
import be.henallux.masi.pedagogique.model.Questionnaire;

/**
 * Created by hendrikdebeuf2 on 30/12/17.
 * modified by Angèle Guillon on 04/01/18.
 */

public class SQLiteQuestionnaireRepository implements IQuestionnaireRepository {

    private Context context;
    private Questionnaire questionnaire;
    private ArrayList<Question> questions;
    private int idQuestionnaire;

    public SQLiteQuestionnaireRepository(Context context) {
        this.context = context;
    }


    @Override
    public ArrayList<Question> getAllQuestionOfQuestionnaire(Questionnaire questionnaire) {
        return null;
    }

    @Override
    public Questionnaire getQuestionnaireById(int idQuestionnaire) {
        return null;
    }
}
