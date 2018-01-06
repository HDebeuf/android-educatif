package be.henallux.masi.pedagogique.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import be.henallux.masi.pedagogique.R;
import be.henallux.masi.pedagogique.activities.mapActivity.IMapActivityRepository;
import be.henallux.masi.pedagogique.activities.mapActivity.SQLiteMapActivityRepository;
import be.henallux.masi.pedagogique.adapters.QuestionListAdapter;
import be.henallux.masi.pedagogique.dao.interfaces.IQuestionnaireRepository;
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
    private ArrayList<Question> finalQuestonArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        finalQuestonArrayList = new ArrayList<Question>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musical_questionnaire);

        context = getApplicationContext();

        ArrayList<Integer> idQuestionnaireList = getIntent().getExtras().getParcelable(Constants.KEY_LOCATION_CLICKED);
        for (int idQuestion: idQuestionnaireList) {
            Questionnaire questionnaire = questionnaireRepository.getQuestionnaireById(idQuestion);
            ArrayList<Question> questionArrayList = questionnaireRepository.getAllQuestionOfQuestionnaire(questionnaire);
            finalQuestonArrayList.addAll(questionArrayList);
        }


        final RecyclerView questionnaireRecyclerView = findViewById(R.id.recyclerViewQuestionnaires);
        questionnaireRecyclerView.setHasFixedSize(true);
        final RecyclerView.LayoutManager questionnaireLayoutManager = new LinearLayoutManager(this);
        questionnaireRecyclerView.setLayoutManager(questionnaireLayoutManager);

        RecyclerView.Adapter QuestionnaireListAdapter = new QuestionListAdapter(context, finalQuestonArrayList);
        questionnaireRecyclerView.setAdapter(QuestionnaireListAdapter);
    }


}
