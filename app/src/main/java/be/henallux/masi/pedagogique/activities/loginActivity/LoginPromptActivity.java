package be.henallux.masi.pedagogique.activities.loginActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.common.hash.Hashing;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.squareup.picasso.Picasso;

import java.nio.charset.StandardCharsets;
import java.util.List;

import be.henallux.masi.pedagogique.R;
import be.henallux.masi.pedagogique.activities.MainMenuActivity;
import be.henallux.masi.pedagogique.activities.groupCreation.GroupCreationActivity;
import be.henallux.masi.pedagogique.dao.interfaces.IUserRepository;
import be.henallux.masi.pedagogique.dao.sqlite.SQLiteUserRepository;
import be.henallux.masi.pedagogique.model.User;
import be.henallux.masi.pedagogique.utils.Constants;
import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginPromptActivity extends AppCompatActivity implements Validator.ValidationListener {
    Validator validator;

    @BindView(R.id.login_prompt_button)
    Button buttonLogin;

    @BindView(R.id.usernameWrapper)
    TextInputLayout usernameWrapper;

    @BindView(R.id.passwordWrapper)
    TextInputLayout passwordWrapper;


    @BindView(R.id.usernameEditText)
    @NotEmpty(messageResId = R.string.error_field_required)
    EditText usernameEditText;

    @BindView(R.id.passwordEditText)
    @NotEmpty(messageResId = R.string.error_field_required)
    EditText passwordEditText;
    
    private IUserRepository repository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_prompt);
        ButterKnife.bind(this);
        repository = new SQLiteUserRepository(getApplicationContext());

        validator = new Validator(this);
        validator.setValidationListener(this);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validator.validate();
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.get_help, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_get_help:
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

    @Override
    public void onValidationSucceeded() {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        User foundUser = repository.accessGranted(username,password);
        if(foundUser != null){
            Intent intent = new Intent(this, GroupCreationActivity.class);
            intent.putExtra(Constants.KEY_CURRENT_USER,foundUser);
            intent.putExtra(Constants.KEY_ID_USER,foundUser.getId());
            startActivity(intent);
        }
        else
        {
            Toast.makeText(getApplicationContext(), R.string.login_wrong_credentials, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this.getApplication());

            // Display error messages
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            }
        }
    }
}
