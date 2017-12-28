package be.henallux.masi.pedagogique.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

import be.henallux.masi.pedagogique.R;
import be.henallux.masi.pedagogique.model.User;

/**
 * Created by haubo on 12/27/2017.
 */

public class GroupCreationUsernameAdapter extends RecyclerView.Adapter<GroupCreationUsernameAdapter.MyViewHolder>{

    Context context;
    ArrayList<ItemBindingModel> userArrayList = new ArrayList<>();
    private User currentUser;


    public GroupCreationUsernameAdapter(Context context, ArrayList<User> userArrayList, User currentUser) {
        this.context = context;
        for(User u : userArrayList){
            ItemBindingModel bm = new ItemBindingModel(u,false);
            this.userArrayList.add(bm);
        }
        this.currentUser = currentUser;
    }

    @Override
    public GroupCreationUsernameAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_username_item,parent,false);
        final MyViewHolder holder=new MyViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        if(position % 2 == 0){
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context,R.color.recyclerview_background_1));
        }
        else
        {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context,R.color.recyclerview_background_2));
        }
        holder.textviewUser.setText(userArrayList.get(position).user.getFirstName() + " " + userArrayList.get(position).user.getLastName());

        holder.checkboxChoiceGroup.setOnCheckedChangeListener(null); //remove previous listener
        holder.checkboxChoiceGroup.setOnCheckedChangeListener(new CustomCheckedListener(holder.getAdapterPosition()));
        holder.checkboxChoiceGroup.setChecked(userArrayList.get(holder.getAdapterPosition()).checked);
    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    /**
     * Used to retrieve only selected users
     * @return participating users
     */
    public ArrayList<User> getParticipatingUsers(){
        ArrayList<User> participatingUsers = new ArrayList<>();
        for(ItemBindingModel bm : userArrayList){
            if(bm.checked)
                participatingUsers.add(bm.user);
        }
        participatingUsers.add(currentUser);
        return participatingUsers;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        CheckBox checkboxChoiceGroup;
        TextView textviewUser;

        public MyViewHolder(View itemView) {
            super(itemView);
            checkboxChoiceGroup = itemView.findViewById(R.id.checkbox_username_group_choice);
            textviewUser = itemView.findViewById(R.id.textview_username_group);
        }
    }

    private class ItemBindingModel{
        public User user;
        public boolean checked;

        public ItemBindingModel(User user, boolean checked) {
            this.user = user;
            this.checked = checked;
        }

        public void toggleChecked(){
            checked = !checked;
        }
    }

    private class CustomCheckedListener implements CompoundButton.OnCheckedChangeListener {
        private int position;
        public CustomCheckedListener(int position) {
            this.position = position;
        }

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            userArrayList.get(position).checked = b;
        }
    }
}
