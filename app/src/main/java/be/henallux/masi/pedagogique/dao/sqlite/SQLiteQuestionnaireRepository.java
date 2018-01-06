package be.henallux.masi.pedagogique.dao.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
<<<<<<< HEAD
=======
import android.net.Uri;
import android.text.TextUtils;

>>>>>>> 9632e73b89e2e94b3ad032d8939b599119300f66
import java.util.ArrayList;

import be.henallux.masi.pedagogique.activities.mapActivity.ActivityMapBaseEntity;
import be.henallux.masi.pedagogique.activities.mapActivity.LocationEntity;
import be.henallux.masi.pedagogique.activities.musicalActivity.makeMusic.entities.InstrumentEntity;
<<<<<<< HEAD
=======
import be.henallux.masi.pedagogique.dao.interfaces.IQuestionRepository;
>>>>>>> 9632e73b89e2e94b3ad032d8939b599119300f66
import be.henallux.masi.pedagogique.dao.interfaces.IQuestionnaireRepository;
import be.henallux.masi.pedagogique.dao.sqlite.entities.ActivityEntity;
import be.henallux.masi.pedagogique.dao.sqlite.entities.AnswerEntity;
import be.henallux.masi.pedagogique.dao.sqlite.entities.QuestionEntity;
import be.henallux.masi.pedagogique.dao.sqlite.entities.QuestionnaireEntity;
import be.henallux.masi.pedagogique.model.Answer;
import be.henallux.masi.pedagogique.model.Question;
import be.henallux.masi.pedagogique.model.Questionnaire;

/**
 * Created by Angèle Guillon on 04/01/18.
 */

public class SQLiteQuestionnaireRepository implements IQuestionnaireRepository {

    private Context context;
<<<<<<< HEAD
    private Questionnaire questionnaire;
    private ArrayList<Question> questions;
    private int idQuestionnaire;
=======
    private IQuestionRepository questionRepository = new SQLiteQuestionRepository(context);
>>>>>>> 9632e73b89e2e94b3ad032d8939b599119300f66

    public SQLiteQuestionnaireRepository(Context context) {
        this.context = context;
    }

<<<<<<< HEAD

    @Override
    public ArrayList<Question> getAllQuestionOfQuestionnaire(Questionnaire questionnaire) {
        return null;
    }

    @Override
<<<<<<< HEAD
    public Questionnaire getQuestionnaire(int idlocation) {

        SQLiteDatabase db = SQLiteHelper.getDatabaseInstance(context);
        StringBuilder queryString = new  StringBuilder();
        Questionnaire questionnaire = new Questionnaire();


        // a refaire avec les nom de colonne variable pour que se soit plus propre
        queryString.append("SELECT * FROM "+ QuestionnaireEntity.TABLE);
        queryString.append("INNER JOIN EducativeActivity ON EducativeActivity.Questionnaire_idQuestionnaire = Questionnaire.idQuestionnaire");
        queryString.append("INNER JOIN ActivityMapBase ON ActivityMapBase.Activity_idActivity = EducativeActivity.idActivity");
        queryString.append("INNER JOIN Location ON Location.ActivityMapBase_idActivityMapBase = ActivityMapBase.idActivityMapBase");

        // Renvoie les données de la requête
        Cursor cursor = db.rawQuery(queryString.toString(), null);
=======
    @Override
    public Questionnaire getQuestionnaireById(int idQuestionnaire) {
        SQLiteDatabase db = SQLiteHelper.getDatabaseInstance(context);

        Cursor cursor = db.query(QuestionnaireEntity.TABLE,
                new String[]{QuestionnaireEntity.COLUMN_ID,
                        QuestionnaireEntity.COLUMN_STATEMENT},
                InstrumentEntity.COLUMN_FK_LOCATION + "=?",new String[]{String.valueOf(idQuestionnaire)},
                null, null, null);
>>>>>>> 9632e73b89e2e94b3ad032d8939b599119300f66

        if(cursor.getCount() == 0) return null;
        cursor.moveToFirst();

        int id = cursor.getInt(0);
        String statement = cursor.getString(1);

        ArrayList<Question> questions = questionRepository.getQuestionsOfQuestionnaire(idQuestionnaire);
        Questionnaire q = new Questionnaire(idQuestionnaire,statement,questions);

        cursor.close();
<<<<<<< HEAD

        return questionnaire;


=======
    public Questionnaire getQuestionnaireById(int idQuestionnaire) {
        return null;
>>>>>>> e18c0f2df4e6e0ad7a0fe5aa2642c82933715529
=======
        return q;
>>>>>>> 9632e73b89e2e94b3ad032d8939b599119300f66
    }
}
