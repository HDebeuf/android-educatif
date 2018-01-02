package be.henallux.masi.pedagogique.activities.historyActivity;

import android.graphics.Color;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import be.henallux.masi.pedagogique.R;
import be.henallux.masi.pedagogique.activities.mapActivity.Location;
import be.henallux.masi.pedagogique.utils.Constants;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FrescoActivity extends AppCompatActivity implements OnStartDragListener {

    @BindView(R.id.recyclerViewItems)
    RecyclerView recyclerView;

    private ItemsAdapter adapter;
    private ItemTouchHelper mItemTouchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fresco);

        ButterKnife.bind(this);

        ArrayList<Location> locationsChosen = getIntent().getParcelableArrayListExtra(Constants.KEY_LOCATIONS_CHOSEN);
        for(Location l : locationsChosen){
            Log.i("chosen",l.getTitle());
        }

        adapter = new ItemsAdapter(this, locationsChosen);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setAdapter(adapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);

    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

    public class ItemsAdapter extends RecyclerView.Adapter<LocationViewHolder> implements ItemTouchHelperAdapter {

        private final OnStartDragListener mDragStartListener;
        private ArrayList<Location> locations;

        public ItemsAdapter(OnStartDragListener mDragStartListener, ArrayList<Location> locationsChosen) {
            this.mDragStartListener = mDragStartListener;
            this.locations = locationsChosen;
        }

        @Override
        public LocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            CardView c = (CardView) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fresco_item_layout,parent,false);
            LocationViewHolder viewHolder = new LocationViewHolder(c);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final LocationViewHolder holder, int position) {
            final Location act = locations.get(position);
            holder.textViewTitle.setText(act.getTitle());
            holder.handleView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (MotionEventCompat.getActionMasked(event) ==
                            MotionEvent.ACTION_DOWN) {
                        mDragStartListener.onStartDrag(holder);
                    }
                    return false;
                }
            });
        }

        @Override
        public int getItemCount() {
            return locations.size();
        }

        @Override
        public void onItemDismiss(int position) {
            locations.remove(position);
            notifyItemRemoved(position);
        }

        @Override
        public void onItemMove(int fromPosition, int toPosition) {
            Location prev = locations.remove(fromPosition);
            locations.add(toPosition > fromPosition ? toPosition - 1 : toPosition, prev);
            notifyItemMoved(fromPosition, toPosition);
        }

    }

    private class LocationViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {

        public TextView textViewTitle;
        public final ImageView handleView;

        public LocationViewHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            handleView = itemView.findViewById(R.id.drag);
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(Color.WHITE);
        }
    }
}
