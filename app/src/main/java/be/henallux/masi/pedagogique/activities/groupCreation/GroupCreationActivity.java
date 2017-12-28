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
import be.henallux.masi.pedagogique.adapters.GroupCreationUsernameAdapter;
import be.henallux.masi.pedagogique.dao.interfaces.ICategoryRepository;
import be.henallux.masi.pedagogique.dao.interfaces.IGroupRepository;
import be.henallux.masi.pedagogique.dao.sqlite.SQLiteCategoryRepository;
import be.henallux.masi.pedagogique.dao.sqlite.SQLiteGroupRepository;
import be.henallux.masi.pedagogique.model.Category;
import be.henallux.masi.pedagogique.model.Group;
import be.henallux.masi.pedagogique.model.User;
import be.henallux.masi.pedagogique.utils.Constants;
import butterknife.BindView;
import butterknife.ButterKnife;

public class GroupCreationActivity extends AppCompatActivity implements ConfirmGroupChosenDialogFragment.ConfirmGroupChosenCallback{

    private User currentUser;
    private ArrayList<User> usersList;
    @BindView(R.id.recyclerview_item_group_creation)
    RecyclerView groupRecyclerView;

    private GroupCreationUsernameAdapter groupCreationAdapter;
    private IGroupRepository groupRepository;
    private ICategoryRepository categoryRepository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_group_creation);
        ButterKnife.bind(this);

        groupRepository = new SQLiteGroupRepository(getApplicationContext());
        categoryRepository = new SQLiteCategoryRepository(getApplicationContext());


        currentUser = getIntent().getParcelableExtra(Constants.KEY_CURRENT_USER);
        ArrayList<User> allUsers = categoryRepository.getAllUsersOfCategory(currentUser.getCategory());
        allUsers.remove(currentUser); //Current user may not choose himself

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        groupRecyclerView.setLayoutManager(linearLayoutManager);
        groupCreationAdapter =
                new GroupCreationUsernameAdapter(this,
                        allUsers,
                        currentUser);

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

                ConfirmGroupChosenDialogFragment dialogFragment =
                        ConfirmGroupChosenDialogFragment.newInstance(this,usersList);
                dialogFragment.show(getFragmentManager(), "ConfirmUserDialog");

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfirm() {
        Group groupCreated = groupRepository.createGroup(usersList);
        Intent intent = new Intent(this, MainMenuActivity.class);
        intent.putExtra(Constants.KEY_CATEGORY_USER, currentUser.getCategory());
        intent.putExtra(Constants.KEY_GROUP_CREATED, groupCreated);
        startActivity(intent);
    }

    @Override
    public void onCancel() {

    }
}
