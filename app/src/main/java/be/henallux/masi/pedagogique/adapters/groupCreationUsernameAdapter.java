package be.henallux.masi.pedagogique.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import be.henallux.masi.pedagogique.R;
import be.henallux.masi.pedagogique.model.User;

/**
 * Created by haubo on 12/27/2017.
 */

public class GroupCreationUsernameAdapter extends RecyclerView.Adapter<GroupCreationUsernameAdapter.MyViewHolder>{

    Context context;
    ArrayList<User> userArrayList = new ArrayList<>();
    private User aloneUser;


    public GroupCreationUsernameAdapter(Context context, ArrayList<User> userArrayList, User aloneUser) {
        this.context = context;
        this.userArrayList = userArrayList;
        this.aloneUser = aloneUser;
    }

    @Override
    public GroupCreationUsernameAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_username_item,parent,false);
        MyViewHolder myViewHolder=new MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.checkboxChoiceGroup.setChecked(userArrayList.get(position).getIsSelected());
        holder.checkboxChoiceGroup.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                userArrayList.get(holder.getAdapterPosition()).setIsSelected(isChecked); //used to memorize who is selected with recyclerview
            }
        });
        holder.textviewUser.setText(userArrayList.get(position).getFirstName() + " " + userArrayList.get(position).getLastName());
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
        int i;
        ArrayList<User> participatingUsers = new ArrayList<>();
        participatingUsers.add(aloneUser);
        for(i=0;i<userArrayList.size();i++){
            if(userArrayList.get(i).getIsSelected()){
                participatingUsers.add(userArrayList.get(i));
            }
        }
        return participatingUsers;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        CheckBox checkboxChoiceGroup;
        TextView textviewUser;

        public MyViewHolder(View itemView) {
            super(itemView);
            checkboxChoiceGroup = (CheckBox) itemView.findViewById(R.id.checkbox_username_group_choice);
            textviewUser = (TextView) itemView.findViewById(R.id.textview_username_group);

        }

       /* public void display(User user) { //We're using this method to access easyly current data selected in the others methods
            this.currentUser = user;
        itemName.setText(item.getItemName());
        itemQuantity.setText("" + item.getItemQuantity()); //little trick to cast in string
        itemMeasure.setText(item.getItemMeasure());
        itemCheck.setText("OK");
        }*/
    }



}
