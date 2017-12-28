package be.henallux.masi.pedagogique.activities.groupCreation;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Color;
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
import be.henallux.masi.pedagogique.model.User;
import be.henallux.masi.pedagogique.utils.Constants;

/**
 * Created by Le Roi Arthur on 28-12-17.
 */

public class ConfirmGroupChosenDialogFragment extends DialogFragment {

    private static ConfirmGroupChosenCallback callback;

    public static ConfirmGroupChosenDialogFragment newInstance(ConfirmGroupChosenCallback callbackConfirm, ArrayList<User> users) {

        callback = callbackConfirm;

        ConfirmGroupChosenDialogFragment f = new ConfirmGroupChosenDialogFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(Constants.KEY_USERS_CHOSEN,users);
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
        View view = inflater.inflate(R.layout.choose_group_dialog_confirm, null);
        builder.setView(view)
            .setPositiveButton(R.string.map_activity_dialog_finish_confirm, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    callback.onConfirm();
                }
            })
            .setNegativeButton(R.string.map_activity_dialog_finish_cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    callback.onCancel();
                }
            });

        ArrayList<User> users = getArguments().getParcelableArrayList(Constants.KEY_USERS_CHOSEN);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewChosenLocations);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.setAdapter(new ConfirmGroupChosenDialogFragment.UserAdapter(users));
        return builder.create();
    }
    private class UserAdapter extends RecyclerView.Adapter<UserViewHolder>{

        private ArrayList<User> users;
        private UserViewHolder viewHolder;

        private UserAdapter(ArrayList<User> users) {
            this.users = users;
        }

        @Override
        public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ConstraintLayout c = (ConstraintLayout) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.confirm_user_item_layout,parent,false);
            viewHolder = new UserViewHolder(c);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final UserViewHolder holder, int position) {
            final User user = users.get(position);
            holder.textViewName.setText(user.getFirstName() + " " + user.getLastName());
        }

        @Override
        public int getItemCount() {
            return users.size();
        }
    }

    private class UserViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewName;
        public ImageView imageViewIcon;

        public UserViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            imageViewIcon = itemView.findViewById(R.id.imageViewIcon);
        }
    }

    public interface ConfirmGroupChosenCallback{
        void onConfirm();
        void onCancel();
    }
}
