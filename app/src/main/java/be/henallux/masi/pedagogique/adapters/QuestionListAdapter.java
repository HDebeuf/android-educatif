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
import be.henallux.masi.pedagogique.model.AnswerGiven;
import be.henallux.masi.pedagogique.model.Question;
import be.henallux.masi.pedagogique.model.Answer;
import be.henallux.masi.pedagogique.model.Questionnaire;

/**
 * Created by Angele on 06/01/2018.
 */

public class QuestionListAdapter extends RecyclerView.Adapter<QuestionListAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Question> questionsArrayList;
    private ArrayList<ItemBindingModel> allItemsBindingModel = new ArrayList<>();

    public QuestionListAdapter(Context context, ArrayList<Question> questionArrayList) {
        this.context = context;
        this.questionsArrayList = questionArrayList;
        for(Question q : questionArrayList){
            AnswerListAdapter answerListAdapter = new AnswerListAdapter(context, q.getAnswers());
            ItemBindingModel bm = new ItemBindingModel(q,answerListAdapter);
            allItemsBindingModel.add(bm);
        }
    }

    @Override
    public QuestionListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.recyclerview_question,parent,false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final QuestionListAdapter.ViewHolder holder, final int position) {

        ItemBindingModel model = allItemsBindingModel.get(position);

        holder.question.setText(model.question.getStatement());

        final RecyclerView answerRecyclerView = holder.answersRecyclerView;
        answerRecyclerView.setHasFixedSize(true);
        final RecyclerView.LayoutManager answerLayoutManager = new GridLayoutManager(context, 2);
        answerRecyclerView.setLayoutManager(answerLayoutManager);

        holder.answersRecyclerView.setAdapter(model.adapter);
    }

    @Override
    public int getItemCount() {
        return allItemsBindingModel.size();
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


    public class ItemBindingModel{
        public Question question;
        public AnswerListAdapter adapter;

        public ItemBindingModel(Question question, AnswerListAdapter adapter) {
            this.question = question;
            this.adapter = adapter;
        }
    }

    /**
     * Returns all the result for the current questionnaire
     * @return an arraylist of results
     */
    public ArrayList<AnswerGiven> getAllResult(){
        ArrayList<AnswerGiven> results = new ArrayList<>();

        for(ItemBindingModel bm : allItemsBindingModel){
            AnswerGiven answerGiven = new AnswerGiven(bm.question,bm.adapter.getAnswersBindingModel());
            results.add(answerGiven);
        }
        return results;
    }
}
