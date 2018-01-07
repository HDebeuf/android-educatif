package be.henallux.masi.pedagogique.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;

import be.henallux.masi.pedagogique.R;
import be.henallux.masi.pedagogique.activities.mapActivity.IMapActivityRepository;
import be.henallux.masi.pedagogique.activities.mapActivity.Location;
import be.henallux.masi.pedagogique.activities.mapActivity.SQLiteMapActivityRepository;
import be.henallux.masi.pedagogique.adapters.AnswerListAdapter;
import be.henallux.masi.pedagogique.adapters.QuestionListAdapter;
import be.henallux.masi.pedagogique.dao.interfaces.IQuestionnaireRepository;
import be.henallux.masi.pedagogique.dao.interfaces.IResultRepository;
import be.henallux.masi.pedagogique.dao.sqlite.SQLiteQuestionnaireRepository;
import be.henallux.masi.pedagogique.dao.sqlite.SQLiteResultRepository;
import be.henallux.masi.pedagogique.model.Answer;
import be.henallux.masi.pedagogique.model.Question;
import be.henallux.masi.pedagogique.model.Questionnaire;
import be.henallux.masi.pedagogique.utils.Constants;

/**
 * Created by Angele on 04/01/2018.
 */

public class QuestionnaireActivity extends AppCompatActivity {
    private IMapActivityRepository repository = new SQLiteMapActivityRepository(this);
    private IQuestionnaireRepository questionnaireRepository;
    private Context context;
    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView addHeaderRecyclerView;
    private ArrayList<Question> finalQuestionArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musical_questionnaire);

        context = getApplicationContext();
        questionnaireRepository = new SQLiteQuestionnaireRepository(context);
        finalQuestionArrayList = new ArrayList<Question>();

        final RecyclerView questionnaireRecyclerView = (RecyclerView) findViewById(R.id.question_recycler_view);
        questionnaireRecyclerView.setHasFixedSize(true);

        ArrayList<Location> LocationList = new ArrayList<Location>();

        LocationList = getIntent().getExtras().getParcelableArrayList(Constants.KEY_LOCATIONS_CHOSEN);

        for (Location locationChose : LocationList) {

            int idQuestion = locationChose.getQuestionnaire().getId();

            Questionnaire questionnaire = questionnaireRepository.getQuestionnaireById(idQuestion);

            ArrayList<Question> questionArrayList = questionnaire.getQuestions();
            for (Question question : questionArrayList) {
                finalQuestionArrayList.add(question);
            }
        }


        final LinearLayoutManager questionnaireLayoutManager = new LinearLayoutManager(context);
        questionnaireRecyclerView.setLayoutManager(questionnaireLayoutManager);

        QuestionListAdapter questionnaireListAdapter = new QuestionListAdapter(context, finalQuestionArrayList);
        questionnaireRecyclerView.setAdapter(questionnaireListAdapter);

        Button finish = (Button) findViewById(R.id.finishQuestionnaireButton);
        finish.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                AnswerListAdapter answerListAdapter = new AnswerListAdapter();
                ArrayList<Answer> answerArrayList = answerListAdapter.getAnsweredArrayList();
                IResultRepository resultRepository = new SQLiteResultRepository(context);
                resultRepository.sendResult(answerArrayList,getIntent().getExtras().getInt(Constants.KEY_CURRENT_GROUP));
                Toast toast = Toast.makeText(context, "Envoyée",Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }
}



