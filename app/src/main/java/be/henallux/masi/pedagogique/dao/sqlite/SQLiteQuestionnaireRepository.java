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
 * Created by Angèle Guillon on 04/01/18.
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

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(0);
            String statement = cursor.getString(1);
            questionnaire = new Questionnaire(id,statement);

            cursor.moveToNext();
        }

        cursor.close();

        return questionnaire;


=======
    public Questionnaire getQuestionnaireById(int idQuestionnaire) {
        return null;
>>>>>>> e18c0f2df4e6e0ad7a0fe5aa2642c82933715529
    }
}
