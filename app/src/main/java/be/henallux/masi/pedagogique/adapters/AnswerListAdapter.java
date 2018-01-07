package be.henallux.masi.pedagogique.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

import be.henallux.masi.pedagogique.R;
import be.henallux.masi.pedagogique.model.Answer;

/**
 * Created by Angele on 06/01/2018.
 */

public class AnswerListAdapter extends  RecyclerView.Adapter<AnswerListAdapter.ViewHolder>{

    private Context context;
    private ArrayList<Answer> answerArrayList = new ArrayList<>();
    private ArrayList<ItemBindingModel> answersBindingModel = new ArrayList<>();

    public AnswerListAdapter(Context context, ArrayList<Answer> answerArrayList) {
        this.context= context;
        for(Answer a : answerArrayList){
            AnswerListAdapter.ItemBindingModel bm = new AnswerListAdapter.ItemBindingModel(a,false);
            this.answersBindingModel.add(bm);
        }
        this.answerArrayList = answerArrayList;
    }

    @Override
    public AnswerListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.recyclerview_checkbox_answer,parent,false);
        final ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.answerTextView.setText(answersBindingModel.get(position).answer.getStatement());
        holder.check.setOnCheckedChangeListener(null); //remove previous listener
        holder.check.setOnCheckedChangeListener(new AnswerListAdapter.CustomCheckedListener(holder.getAdapterPosition()));
        holder.check.setChecked(answersBindingModel.get(holder.getAdapterPosition()).checked);
    }

    @Override
    public int getItemCount() {
        return answersBindingModel.size();
    }


    public ArrayList<Answer> getAnswersBindingModel(){

        ArrayList<Answer> answersGiven = new ArrayList<Answer>();

        for(ItemBindingModel aq : answersBindingModel) {
            if(aq.checked)
                answersGiven.add(aq.answer);
        }
        return answersGiven;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView answerTextView;
        CheckBox check;
        public ViewHolder(View itemView) {
            super(itemView);
            answerTextView = (TextView) itemView.findViewById(R.id.answerTextView);
            check = (CheckBox) itemView.findViewById(R.id.checkBoxAnswer);
        }
    }

    private class ItemBindingModel{
        public Answer answer;
        public boolean checked;

        public ItemBindingModel(Answer answer, boolean checked) {
            this.answer = answer;
            this.checked = checked;
        }

        public void toggleChecked(){
            checked = !checked;
        }
    }

    private class CustomCheckedListener implements CompoundButton.OnCheckedChangeListener {
        private int position;
        public CustomCheckedListener(int position) {
            this.position = position;
        }

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            answersBindingModel.get(position).checked = b;
        }
    }
}
