package be.henallux.masi.pedagogique.activities.historyActivity;

/**
 * Created by Le Roi Arthur on 02-01-18.
 */

/**
 * Interface to notify a {@link RecyclerView.Adapter} of moving and dismissal event from a {@link
 * android.support.v7.widget.helper.ItemTouchHelper.Callback}.
 *
 * @author Paul Burke (ipaulpro)
 */
public interface ItemTouchHelperAdapter {

    void onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);
}