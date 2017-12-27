package be.henallux.masi.pedagogique.activities.groupCreation;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import be.henallux.masi.pedagogique.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class GroupCreationActivity extends AppCompatActivity {
    private int categoryId,userId;

    @BindView(R.id.recyclerview_item_group_creation)
    RecyclerView groupRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_group_creation);
        ButterKnife.bind(this);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.group_create_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_continue:
                AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setTitle(this.getString(R.string.help_title));
                alertDialog.setMessage(this.getString(R.string.login_ask_help));
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
