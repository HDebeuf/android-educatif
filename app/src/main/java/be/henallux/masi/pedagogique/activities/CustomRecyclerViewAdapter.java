package be.henallux.masi.pedagogique.activities;

/**
 * Created by Angele on 06/01/2018.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;
public class CustomRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = CustomRecyclerViewAdapter.class.getSimpleName();
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private List<ItemObject> itemObjects;
    public CustomRecyclerViewAdapter(List<ItemObject> itemObjects) {
        this.itemObjects = itemObjects;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_layout, parent, false);
            return new HeaderViewHolder(layoutView);
        } else if (viewType == TYPE_ITEM) {
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
            return new ItemViewHolder(layoutView);
        }
        throw new RuntimeException("No match for " + viewType + ".");
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemObject mObject = itemObjects.get(position);
        if(holder instanceof HeaderViewHolder){
            ((HeaderViewHolder) holder).headerTitle.setText(mObject.getContents());
        }else if(holder instanceof ItemViewHolder){
            ((ItemViewHolder) holder).itemContent.setText(mObject.getContents());
        }
    }
    private ItemObject getItem(int position) {
        return itemObjects.get(position);
    }
    @Override
    public int getItemCount() {
        return itemObjects.size();
    }
    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;
        return TYPE_ITEM;
    }
    private boolean isPositionHeader(int position) {
        return position == 0;
    }
}