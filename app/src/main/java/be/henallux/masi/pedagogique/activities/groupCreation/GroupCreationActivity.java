package be.henallux.masi.pedagogique.activities.groupCreation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import be.henallux.masi.pedagogique.R;
import be.henallux.masi.pedagogique.activities.MainMenuActivity;
import be.henallux.masi.pedagogique.activities.historyActivity.ConfirmLocationChosenDialogFragment;
import be.henallux.masi.pedagogique.adapters.GroupCreationUsernameAdapter;
import be.henallux.masi.pedagogique.model.Category;
import be.henallux.masi.pedagogique.model.Group;
import be.henallux.masi.pedagogique.model.User;
import be.henallux.masi.pedagogique.utils.Constants;
import butterknife.BindView;
import butterknife.ButterKnife;

public class GroupCreationActivity extends AppCompatActivity implements ConfirmGroupChosenDialogFragment.ConfirmGroupChosenCallback{

    private int categoryId,userId;
    private ArrayList<User> usersList;
    private Category categoryOfUser;
    @BindView(R.id.recyclerview_item_group_creation)
    RecyclerView groupRecyclerView;

    private GroupCreationUsernameAdapter groupCreationAdapter;
    private SQLiteGroupCreationRepository repository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_group_creation);
        ButterKnife.bind(this);

        repository = new SQLiteGroupCreationRepository(getApplicationContext());
        categoryOfUser = getIntent().getExtras().getParcelable(Constants.KEY_CATEGORY_USER);
        userId = getIntent().getIntExtra(Constants.KEY_ID_USER,0);
        categoryId = categoryOfUser.getId();

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        groupRecyclerView.setLayoutManager(linearLayoutManager);
        groupCreationAdapter=new GroupCreationUsernameAdapter(this,repository.GetUsersByCaterogy(categoryId,userId),repository.getUserById(userId));
        groupRecyclerView.setAdapter(groupCreationAdapter);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.group_create_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_continue:

                this.usersList = groupCreationAdapter.getParticipatingUsers();

                ConfirmGroupChosenDialogFragment dialogFragment = ConfirmGroupChosenDialogFragment.newInstance(this,usersList);
                dialogFragment.show(getFragmentManager(), "ConfirmUserDialog");

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfirm() {
        Group groupCreated = repository.createGroup(usersList);
        Intent intent = new Intent(this, MainMenuActivity.class);
        intent.putExtra(Constants.KEY_CATEGORY_USER, categoryOfUser);
        intent.putExtra(Constants.KEY_GROUP_CREATED, groupCreated);
        startActivity(intent);
    }

    @Override
    public void onCancel() {

    }
}
