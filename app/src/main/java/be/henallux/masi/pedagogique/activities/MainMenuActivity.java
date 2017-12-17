package be.henallux.masi.pedagogique.activities;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

import be.henallux.masi.pedagogique.R;
import be.henallux.masi.pedagogique.dao.interfaces.IActivitiesRepository;
import be.henallux.masi.pedagogique.dao.sqlite.SQLiteActivitiesRepository;
import be.henallux.masi.pedagogique.model.Activity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainMenuActivity extends AppCompatActivity {

    @BindView(R.id.recyclerViewActivities)
    RecyclerView recyclerViewActivities;

    private IActivitiesRepository activitiesRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        ButterKnife.bind(this);

        activitiesRepository = SQLiteActivitiesRepository.getInstance(this);

        recyclerViewActivities.setLayoutManager(new GridLayoutManager(this,2));
        recyclerViewActivities.setAdapter(new ActivitiesAdapter(activitiesRepository.getAllActivities()));
    }

    private class ActivitiesAdapter extends RecyclerView.Adapter<ActivityViewHolder>{

        private ArrayList<Activity> activities;
        private ActivityViewHolder viewHolder;

        private ActivitiesAdapter(ArrayList<Activity> activities) {
            this.activities = activities;
        }

        @Override
        public ActivityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ConstraintLayout c = (ConstraintLayout) LayoutInflater.from(parent.getContext())
                                                    .inflate(R.layout.activity_item_layout,parent,false);
            viewHolder = new ActivityViewHolder(c);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ActivityViewHolder holder, int position) {
            Activity act = activities.get(position);
            holder.buttonStart.setText(act.getName());
        }

        @Override
        public int getItemCount() {
            return activities.size();
        }
    }

    private class ActivityViewHolder extends RecyclerView.ViewHolder{

        public Button buttonStart;

        public ActivityViewHolder(View itemView) {
            super(itemView);
            buttonStart = (Button)itemView.findViewById(R.id.buttonStart);
        }
    }
}
