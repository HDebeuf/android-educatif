package be.henallux.masi.pedagogique.adapters;

import android.support.v7.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.media.SoundPool;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Random;

import be.henallux.masi.pedagogique.R;
import be.henallux.masi.pedagogique.activities.musicalActivity.MusicalActivity;
import be.henallux.masi.pedagogique.activities.musicalActivity.makeMusic.handlers.IMapChangeHandler;
import be.henallux.masi.pedagogique.activities.musicalActivity.makeMusic.handlers.ISoundPoolHandler;
import be.henallux.masi.pedagogique.activities.musicalActivity.makeMusic.Instrument;
import be.henallux.masi.pedagogique.activities.musicalActivity.makeMusic.handlers.SoundPoolHandler;
import be.henallux.masi.pedagogique.model.Question;
import be.henallux.masi.pedagogique.model.Questionnaire;
import be.henallux.masi.pedagogique.utils.Constants;

/**
 * Created by Angele on 06/01/2018.
 */

public class QuestionnaireListAdapter extends RecyclerView.Adapter<QuestionnaireListAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Question> questionsArrayList;

    public QuestionnaireListAdapter(Context context, ArrayList<Question> questionArrayList) {
        this.context = context;
        this.questionsArrayList = questionArrayList;
    }

    @Override
    public QuestionnaireListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.single_instrument,parent,false);
        QuestionnaireListAdapter.ViewHolder holder = new QuestionnaireListAdapter.ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(QuestionnaireListAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

   /* @Override
    public QuestionnaireListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

    }

    @Override
    public void onBindViewHolder(final QuestionnaireListAdapter.ViewHolder holder, final int position) {
        if(position % 2 == 0){
            holder.instrumentImage.setBackgroundColor(ContextCompat.getColor(context,R.color.recyclerview_background_1));
        }
        else
        {
            holder.instrumentImage.setBackgroundColor(ContextCompat.getColor(context,R.color.recyclerview_background_2));
        }

        holder.instrumentName.setText(instrumentArrayList.get(position).getName());
        Picasso.with(context).load(instrumentArrayList.get(position).getImagePath()).into(holder.instrumentImage);

        holder.lockedImage.setImageResource(R.drawable.ic_locked);


        holder.lockedImage.setVisibility(View.GONE);


        String fileName = instrumentArrayList.get(position).getSampleFileName();
        String fileType = "raw";

        soundID = soundPoolHandler.loadSample(fileName, fileType);

        ArrayList<Integer> idData = new ArrayList<>(2);
        idData.add(soundID);
        idData.add(instrumentArrayList.get(position).getLocationId());
        */

}
