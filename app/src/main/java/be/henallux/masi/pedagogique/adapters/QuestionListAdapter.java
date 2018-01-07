package be.henallux.masi.pedagogique.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import be.henallux.masi.pedagogique.R;
import be.henallux.masi.pedagogique.model.Question;
import be.henallux.masi.pedagogique.model.Answer;

/**
 * Created by Angele on 06/01/2018.
 */

public class QuestionListAdapter extends RecyclerView.Adapter<QuestionListAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Question> questionsArrayList;

    public QuestionListAdapter(Context context, ArrayList<Question> questionArrayList) {
        this.context = context;
        this.questionsArrayList = questionArrayList;
        Log.d("test 1 :", questionsArrayList.get(0).getStatement());
    }

    @Override
    public QuestionListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("test 5:", "test ");

        View v= LayoutInflater.from(context).inflate(R.layout.recyclerview_question,parent,false);
        Log.d("test 5:", "test ");
        ViewHolder holder = new ViewHolder(v);
        Log.d("test 6 :", questionsArrayList.get(0).getStatement());
        return holder;

    }

    @Override
    public void onBindViewHolder(final QuestionListAdapter.ViewHolder holder, final int position) {
        holder.question.setText(questionsArrayList.get(position).getStatement());

        final RecyclerView answerRecyclerView = holder.answersRecyclerView.findViewById(R.id.recyclerViewReponces);
        answerRecyclerView.setHasFixedSize(true);
        final RecyclerView.LayoutManager answerLayoutManager = new GridLayoutManager(context, 2);
        answerRecyclerView.setLayoutManager(answerLayoutManager);

        ArrayList<Answer> answersArraylist = questionsArrayList.get(position).getAnswers();


        AnswerListAdapter answerListAdapter = new AnswerListAdapter(context, answersArraylist);
        holder.answersRecyclerView.setAdapter(answerListAdapter);

    }

    @Override
    public int getItemCount() {
        return questionsArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        TextView question;
        RecyclerView answersRecyclerView;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.singleAnswerCardView);
            question = (TextView) itemView.findViewById(R.id.questionTextView);
            answersRecyclerView = (RecyclerView) itemView.findViewById(R.id.recyclerViewReponces);


        }
    }
}
