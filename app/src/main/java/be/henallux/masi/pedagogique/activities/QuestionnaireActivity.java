package be.henallux.masi.pedagogique.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

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
import be.henallux.masi.pedagogique.model.AnswerGiven;
import be.henallux.masi.pedagogique.model.Group;
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
    private Group currentGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musical_questionnaire);

        context = getApplicationContext();
        questionnaireRepository = SQLiteQuestionnaireRepository.getInstance(context);
        finalQuestionArrayList = new ArrayList<>();

        final RecyclerView questionnaireRecyclerView = findViewById(R.id.question_recycler_view);
        questionnaireRecyclerView.setHasFixedSize(true);

        ArrayList<Location> LocationList;

        LocationList = getIntent().getExtras().getParcelableArrayList(Constants.KEY_LOCATIONS_CHOSEN);
        currentGroup = getIntent().getExtras().getParcelable(Constants.KEY_CURRENT_GROUP);
        String classToThrowAfterEnd = getIntent().getExtras().getString(Constants.KEY_CLASS_TO_RETURN_AFTER_END);

        for (Location locationChose : LocationList) {
            Questionnaire questionnaire = locationChose.getQuestionnaire();
            finalQuestionArrayList.addAll(questionnaire.getQuestions());
        }


        final LinearLayoutManager questionnaireLayoutManager = new LinearLayoutManager(context);
        questionnaireRecyclerView.setLayoutManager(questionnaireLayoutManager);
        final QuestionListAdapter questionnaireListAdapter = new QuestionListAdapter(context, finalQuestionArrayList);
        questionnaireRecyclerView.setAdapter(questionnaireListAdapter);

        Button finish = (Button) findViewById(R.id.finishQuestionnaireButton);
        finish.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!questionnaireListAdapter.isValid()){
                    Toast.makeText(context, R.string.error_missing_answer, Toast.LENGTH_SHORT).show();
                    return;
                }
                ArrayList<AnswerGiven> results = questionnaireListAdapter.getAllResult();
                ArrayList<Answer> answerArrayList = new ArrayList<>();
                for(AnswerGiven ag : results){
                    answerArrayList.add(ag.getGivenAnswers().get(0));
                }
                IResultRepository resultRepository = SQLiteResultRepository.getInstance(context);
                resultRepository.sendResult(answerArrayList,currentGroup);
                Toast toast = Toast.makeText(context, "Envoyée",Toast.LENGTH_LONG);
                toast.show();
                Intent intent = new Intent(QuestionnaireActivity.this,MainMenuActivity.class);
                intent.putExtra(Constants.KEY_GROUP_CREATED,currentGroup);
                intent.putExtra(Constants.KEY_CATEGORY_USER,currentGroup.getMembers().get(0).getCategory());
                startActivity(intent);
            }
        });
    }
}



