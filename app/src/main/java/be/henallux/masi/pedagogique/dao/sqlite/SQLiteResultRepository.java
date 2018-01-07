package be.henallux.masi.pedagogique.dao.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import be.henallux.masi.pedagogique.dao.interfaces.IResultRepository;
import be.henallux.masi.pedagogique.dao.sqlite.entities.AnswerToQuestionEntity;
import be.henallux.masi.pedagogique.dao.sqlite.entities.ResultEntity;
import be.henallux.masi.pedagogique.model.Answer;
import be.henallux.masi.pedagogique.model.Group;
import be.henallux.masi.pedagogique.utils.IMailComposer;
import be.henallux.masi.pedagogique.utils.IMailSender;
import be.henallux.masi.pedagogique.utils.MailComposer;
import be.henallux.masi.pedagogique.utils.MailSender;

/**
 * Created by hendrikdebeuf2 on 7/01/18.
 */

public class SQLiteResultRepository implements IResultRepository {

    private Context context;
    private SQLiteDatabase db;
    private IMailComposer mailComposer;
    private IMailSender mailSender;

    public SQLiteResultRepository(Context context) {
        this.context = context;
        db = SQLiteHelper.getDatabaseInstance(this.context);
        mailComposer = new MailComposer(context);
        mailSender = new MailSender(context);
    }

    public void sendResult (ArrayList<Answer> givenAnswerArrayList, int groupId){


        StringBuilder body = mailComposer.composeResultsGrid(givenAnswerArrayList);
        mailSender.sendMail("Groupe " + groupId , "Questionnaire", body.toString());


        int nbCorrect = 0;
        int nbWrong = 0;
        for (Answer givenAnswer : givenAnswerArrayList){
            if(givenAnswer.isCorrect()){
                nbCorrect++;
            } else {
                nbWrong++;
            }
        }
        ContentValues values = new ContentValues();
        values.put(ResultEntity.COLUMN_NB_CORRECT,nbCorrect);
        values.put(ResultEntity.COLUMN_NB_WRONG,nbWrong);
        values.put(ResultEntity.COLUMN_FK_GROUP,groupId);
        db.insert(ResultEntity.TABLE, null, values);


    }

}
