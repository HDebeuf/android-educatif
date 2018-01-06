package be.henallux.masi.pedagogique.adapters;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.content.Context;
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
    }

    @Override
    public QuestionListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.recyclerview_question,parent,false);
        QuestionListAdapter.ViewHolder holder = new QuestionListAdapter.ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final QuestionListAdapter.ViewHolder holder, final int position) {
        holder.question.setText(questionsArrayList.get(position).getStatement());

        ArrayList<Answer> answersArraylist = questionsArrayList.get(position).getAnswers();

        holder.answersRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager answersLayoutManager = new GridLayoutManager(context, 2);
        holder.answersRecyclerView.setLayoutManager(answersLayoutManager);

        RecyclerView.Adapter AnswerListAdapter = new AnswerListAdapter(context, answersArraylist);
        holder.answersRecyclerView.setAdapter(AnswerListAdapter);

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView question;
        RecyclerView answersRecyclerView;

        public ViewHolder(View itemView) {
            super(itemView);
            question = (TextView) itemView.findViewById(R.id.questionTextView);
            answersRecyclerView = (RecyclerView) itemView.findViewById(R.id.recyclerViewReponces);


        }
    }
}
