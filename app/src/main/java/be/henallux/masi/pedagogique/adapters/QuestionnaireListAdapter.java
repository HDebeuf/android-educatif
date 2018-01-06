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
import be.henallux.masi.pedagogique.utils.Constants;

/**
 * Created by Angele on 06/01/2018.
 */

public class QuestionnaireListAdapter  extends RecyclerView.Adapter<QuestionnaireListAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Question> questionsArrayList;
    private ISoundPoolHandler soundPoolHandler;
    private IMapChangeHandler mapChangeHandler;
    private SoundPool soundPool;
    private int soundID;
    private int tagId;
    private boolean loaded;

    public QuestionnaireListAdapter(Context context, ArrayList<Question> questionArrayList, IMapChangeHandler mapChangeHandler) {
        this.context = context;
        this.questionsArrayList = questionArrayList;
        this.mapChangeHandler = mapChangeHandler;
        tagId = 1;
    }

    @Override
    public QuestionnaireListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.single_instrument,parent,false);
        InstrumentListAdapter.ViewHolder holder = new InstrumentListAdapter.ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final InstrumentListAdapter.ViewHolder holder, final int position) {
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
        Log.d("mediainfo",String.valueOf(instrumentArrayList.get(position).isUnlocked()));
        Log.d("mediainfo",String.valueOf(instrumentArrayList.get(position).getSampleFileName()));
        if (instrumentArrayList.get(position).isUnlocked()){
            holder.lockedImage.setVisibility(View.GONE);

            //Manage multiple sound play in a SoundPool

            String fileName = instrumentArrayList.get(position).getSampleFileName();
            String fileType = "raw";

            soundID = soundPoolHandler.loadSample(fileName, fileType);

            ArrayList<Integer> idData = new ArrayList<>(2);
            idData.add(soundID);
            idData.add(instrumentArrayList.get(position).getLocationId());

            holder.instrumentImage.setTag(idData);
            holder.instrumentImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ArrayList<Integer> idData = (ArrayList<Integer>) holder.instrumentImage.getTag();
                    soundPoolHandler.playSample(idData.get(0));
                    Log.d("tototo",idData.get(0).toString());
                    Log.d("tototo",idData.get(1).toString());
                    mapChangeHandler.moveToPosition(idData.get(1));
                }
            });

        } else {
            holder.instrumentInfoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openContinentQuizz(instrumentArrayList.get(position).getLocationId());
                }
            });
            holder.lockedImage.setTag(instrumentArrayList.get(position).getLocationId());
            holder.lockedImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mapChangeHandler.moveToPosition(Integer.valueOf(holder.lockedImage.getTag().toString()));
                }
            });
        }

        holder.instrumentInfoButton.setImageResource(R.drawable.ic_info);
        holder.instrumentInfoButton.setId(instrumentArrayList.get(position).getLocationId());
        holder.instrumentInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openContinentQuizz(instrumentArrayList.get(position).getLocationId());
            }
        });

    }

    @Override
    public int getItemCount() {
        return instrumentArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView instrumentName;
        ImageView instrumentImage;
        ImageView lockedImage;
        ImageButton instrumentInfoButton;

        public ViewHolder (View itemView){
            super(itemView);
            instrumentName = (TextView) itemView.findViewById(R.id.instrumentName);
            instrumentImage = (ImageView) itemView.findViewById(R.id.instrumentImage);
            instrumentInfoButton = (ImageButton) itemView.findViewById(R.id.informationButton);
            lockedImage = itemView.findViewById(R.id.locked);

        }
    }

    private void openContinentQuizz (int locationId){
        Intent intent = new Intent(context, MusicalActivity.class);
        intent.putExtra(Constants.KEY_LOCATION_CLICKED,locationId);
        context.startActivity(intent);
    }

}
