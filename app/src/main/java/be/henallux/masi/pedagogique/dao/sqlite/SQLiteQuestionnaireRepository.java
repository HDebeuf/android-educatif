package be.henallux.masi.pedagogique.dao.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;

import java.sql.Statement;
import java.util.ArrayList;

import be.henallux.masi.pedagogique.activities.mapActivity.LocationEntity;
import be.henallux.masi.pedagogique.activities.musicalActivity.makeMusic.Instrument;
import be.henallux.masi.pedagogique.activities.musicalActivity.makeMusic.entities.InstrumentEntity;
import be.henallux.masi.pedagogique.dao.interfaces.IInstrumentRepository;
import be.henallux.masi.pedagogique.dao.interfaces.IQuestionnaireRepository;
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

    public SQLiteQuestionnaireRepository(Context context) {
        this.context = context;
    }

    @Override
    public ArrayList<Question> getAllQuestion(Questionnaire questionnaire) {

        int idQuestionnaire = questionnaire.getId();
        SQLiteDatabase db = SQLiteHelper.getDatabaseInstance(context);
        ArrayList<Question> questions = new ArrayList<>();
        Cursor cursor = db.rawQuery("Select * from "+ QuestionEntity.TABLE+" where "+QuestionEntity.COLUMN_FK_QUESTIONNAIRE+" = '"+idQuestionnaire+"'",null);

        cursor.moveToFirst();

         while (!cursor.isAfterLast()) {
             int id = cursor.getInt(0);
             String statement = cursor.getString(1);
             // récupéré les reponses
             ArrayList<Answer> answers = new ArrayList<>();
             Cursor c = db.rawQuery("Select * from "+ AnswerEntity.TABLE+" where "+AnswerEntity.COLUMN_FK_QUESTION+" = '"+id+"'",null);

             c.moveToFirst();

             while (!c.isAfterLast()) {
                 int a_id= c.getInt(0);
                 String a_statement = c.getString(1);
                 answers.add(new Answer(a_id,a_statement));
                 c.moveToNext();
             }
             c.close();

             questions.add(new Question(id,statement,questionnaire,answers));

             cursor.moveToNext();
         }

        cursor.close();
        return questions;
    }

    /**
     * Récupération du questionnaire
     * @param idlocation
     * @return Questionnaire
     */
    @Override
    public Questionnaire getQuestionnaqire(int idlocation) {

        SQLiteDatabase db = SQLiteHelper.getDatabaseInstance(context);
        StringBuilder queryString = new  StringBuilder();
        Questionnaire questionnaire = new Questionnaire();

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


    }




}
