package be.henallux.masi.pedagogique.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

import be.henallux.masi.pedagogique.R;
import be.henallux.masi.pedagogique.dao.interfaces.IActivitiesRepository;
import be.henallux.masi.pedagogique.dao.sqlite.SQLiteActivitiesRepository;
import be.henallux.masi.pedagogique.model.Activity;
import be.henallux.masi.pedagogique.model.Category;
import be.henallux.masi.pedagogique.utils.Constants;
import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Main activity on which users are prompted for an activity.
 * Steps to add your own activity :
 * 1) Create a new package containing everything related to your new activity
 * 2) Create your new activity inside that package
 * 3) Go to "SQLiteHelper.insert" method and add your activity as indicated
 */
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

        Category categoryOfUser = getIntent().getExtras().getParcelable(Constants.KEY_CATEGORY_USER);

        recyclerViewActivities.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewActivities.setAdapter(new ActivitiesAdapter(activitiesRepository.getAllActivitiesOfCategory(categoryOfUser)));
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
        public void onBindViewHolder(final ActivityViewHolder holder, int position) {
            final Activity act = activities.get(position);
            holder.buttonStart.setText(act.getName());
            holder.buttonStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainMenuActivity.this,act.getAssociatedClass());
                    intent.putExtra(Constants.ACTIVITY_KEY,act);
                    startActivity(intent);
                }
            });

            Target target = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    Drawable drawable = new BitmapDrawable(MainMenuActivity.this.getResources(), bitmap);
                    holder.buttonStart.setCompoundDrawablesWithIntrinsicBounds(drawable,null,null,null);
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {}

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {}
            };
            Picasso.with(MainMenuActivity.this)
                    .load(act.getUriIcon())
                    .resize(90, 90)
                    .onlyScaleDown()
                    .into(target);
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
            buttonStart = itemView.findViewById(R.id.buttonStart);
        }
    }
}
