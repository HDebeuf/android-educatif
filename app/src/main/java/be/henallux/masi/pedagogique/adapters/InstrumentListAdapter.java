package be.henallux.masi.pedagogique.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import be.henallux.masi.pedagogique.R;
import be.henallux.masi.pedagogique.activities.musicalActivity.MakeMusicActivity;
import be.henallux.masi.pedagogique.activities.musicalActivity.MusicalActivity;
import be.henallux.masi.pedagogique.activities.musicalActivity.makeMusic.Instrument;
import be.henallux.masi.pedagogique.utils.Constants;

/**
 * Created by hendrikdebeuf2 on 30/12/17.
 */

public class InstrumentListAdapter extends RecyclerView.Adapter<InstrumentListAdapter.ViewHolder>{

    private Context context;
    private ArrayList<Instrument> instrumentArrayList;

    public InstrumentListAdapter(Context context, ArrayList<Instrument> instrumentArrayList) {
        this.context = context;
        this.instrumentArrayList = instrumentArrayList;
    }

    @Override
    public InstrumentListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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

        holder.locked.setImageResource(R.drawable.ic_locked);
        if (instrumentArrayList.get(position).isUnlocked()){
            holder.locked.setEnabled(false);
            holder.instrumentImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // TO DO manage samples
                }
            });
        } else {
            holder.instrumentInfoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openContinentQuizz(instrumentArrayList.get(position).getLocationId());
                }
            });
        }

        holder.instrumentInfoButton.setImageResource(R.drawable.ic_info);
        holder.instrumentInfoButton.setId(instrumentArrayList.get(position).getLocationId());
        holder.instrumentInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openContinentQuizz(view.getId());
            }
        });

    }

    @Override
    public int getItemCount() {
        return instrumentArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView instrumentName;
        ImageView instrumentImage;
        ImageView locked;
        ImageButton instrumentInfoButton;

        public ViewHolder (View itemView){
            super(itemView);
            instrumentName = (TextView) itemView.findViewById(R.id.instrumentName);
            instrumentImage = itemView.findViewById(R.id.instrumentImage);
            instrumentInfoButton = (ImageButton) itemView.findViewById(R.id.informationButton);
            locked = itemView.findViewById(R.id.locked);
        }
    }

    private void openContinentQuizz (int locationId){
        Intent intent = new Intent(context, MusicalActivity.class);
        intent.putExtra(Constants.KEY_LOCATION_CLICKED,locationId);
        context.startActivity(intent);
    }

}
