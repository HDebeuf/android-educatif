package be.henallux.masi.pedagogique.dao.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import be.henallux.masi.pedagogique.dao.interfaces.IResultRepository;
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
    private static SQLiteResultRepository instance;

    private SQLiteResultRepository(Context context) {
        this.context = context;
        db = SQLiteHelper.getDatabaseInstance(this.context);
        mailComposer = new MailComposer(context);
        mailSender = new MailSender(context);
    }

    public static SQLiteResultRepository getInstance(Context context){
        if(instance == null){
            instance = new SQLiteResultRepository(context);
        }
        return instance;
    }


    public boolean sendResult (ArrayList<Answer> givenAnswerArrayList, Group group){
        StringBuilder body = mailComposer.composeResultGridPlaintext(group,givenAnswerArrayList);
        mailSender.sendMail("Groupe " + group.getId() , "Questionnaire", body.toString());

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
        values.put(ResultEntity.COLUMN_FK_GROUP,group.getId());
        db.insert(ResultEntity.TABLE, null, values);

        boolean passed = false;

        if (nbCorrect>=nbWrong){
            passed = true;
        }

        return passed;
    }

}
