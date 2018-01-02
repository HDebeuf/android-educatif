package be.henallux.masi.pedagogique.activities.historyActivity;

import android.support.v7.widget.RecyclerView;

/**
 * Created by Le Roi Arthur on 02-01-18.
 */

public interface OnStartDragListener {

    /**
     * Called when a view is requesting a start of a drag.
     *
     * @param viewHolder The holder of the view to drag.
     */
    void onStartDrag(RecyclerView.ViewHolder viewHolder);
}