package be.henallux.masi.pedagogique.activities.historyActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import be.henallux.masi.pedagogique.R;
import be.henallux.masi.pedagogique.activities.mapActivity.Location;
import be.henallux.masi.pedagogique.utils.Constants;

/**
 * Created by Le Roi Arthur on 26-12-17.
 */

public class ConfirmLocationChosenDialogFragment extends DialogFragment {

    public static ConfirmLocationChosenDialogFragment newInstance(ArrayList<Location> locations) {
        ConfirmLocationChosenDialogFragment f = new ConfirmLocationChosenDialogFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(Constants.KEY_LOCATIONS_CHOSEN,locations);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onStart() {
        super.onStart();
        ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(this.getActivity(),R.color.colorPrimary));
        ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(this.getActivity(),R.color.colorPrimary));
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.map_activity_dialog_confirm, null);
        final ConfirmLocationListener listener = (ConfirmLocationListener) getActivity();
        builder.setView(view)
                .setPositiveButton(R.string.map_activity_dialog_finish_confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onConfirm();
                    }
                })
                .setNegativeButton(R.string.map_activity_dialog_finish_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onDismiss();
                    }
                });

        ArrayList<Location> locations = getArguments().getParcelableArrayList(Constants.KEY_LOCATIONS_CHOSEN);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewChosenLocations);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.setAdapter(new LocationAdapter(locations));
        return builder.create();
    }
    private class LocationAdapter extends RecyclerView.Adapter<LocationViewHolder>{

        private ArrayList<Location> locations;
        private LocationViewHolder viewHolder;

        private LocationAdapter(ArrayList<Location> activities) {
            this.locations = activities;
        }

        @Override
        public LocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ConstraintLayout c = (ConstraintLayout) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.location_chosen_item_layout,parent,false);
            viewHolder = new LocationViewHolder(c);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final LocationViewHolder holder, int position) {
            final Location act = locations.get(position);
            holder.textViewName.setText(act.getTitle());
        }

        @Override
        public int getItemCount() {
            return locations.size();
        }
    }

    private class LocationViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewName;
        public ImageView imageViewIcon;

        public LocationViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            imageViewIcon = itemView.findViewById(R.id.imageViewIcon);
        }
    }

    public interface ConfirmLocationListener{
        void onConfirm();
        void onDismiss();
    }
}