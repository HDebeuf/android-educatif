package be.henallux.masi.pedagogique.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import be.henallux.masi.pedagogique.R;
import be.henallux.masi.pedagogique.model.User;

/**
 * Created by haubo on 12/27/2017.
 */

public class groupCreationUsernameAdapter extends RecyclerView.Adapter<groupCreationUsernameAdapter.MyViewHolder>{

    Context context;
    ArrayList<User> userArrayList;


    public groupCreationUsernameAdapter(Context context, ArrayList<User> userArrayList) {
        this.context = context;
        this.userArrayList = userArrayList;
    }

    @Override
    public groupCreationUsernameAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_username_item,parent,false);
        MyViewHolder myViewHolder=new MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(groupCreationUsernameAdapter.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private User currentUser;

        public MyViewHolder(View itemView) {
            super(itemView);
        }
        public void display(User user) { //We're using this method to access easyly current data selected in the others methods
            this.currentUser = user;
        /*itemName.setText(item.getItemName());
        itemQuantity.setText("" + item.getItemQuantity()); //little trick to cast in string
        itemMeasure.setText(item.getItemMeasure());
        itemCheck.setText("OK");*/
        }
    }



}
