package be.henallux.masi.pedagogique.activities.loginActivity;

import android.content.DialogInterface;
import android.os.Build;
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

import java.nio.charset.StandardCharsets;
import java.util.List;

import be.henallux.masi.pedagogique.R;
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
    EditText usernameEditText;

    @BindView(R.id.passwordEditText)
    EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_prompt);
        ButterKnife.bind(this);
        validator = new Validator(this);
        validator.setValidationListener(this);

        final SQLiteLoginActivityRepository repository = new SQLiteLoginActivityRepository(getApplicationContext());
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String loginId,pwd;
               loginId = usernameEditText.getText().toString();
               pwd = Hashing.sha256().hashString(passwordEditText.getText().toString(), StandardCharsets.UTF_8).toString();
               if(repository.getPwdHash(repository.getID(loginId)).equals(pwd)){
                   Toast.makeText(getApplicationContext(), R.string.working, Toast.LENGTH_SHORT).show();
               }



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
                alertDialog.setTitle("Informations");
                alertDialog.setMessage(this.getString(R.string.ask_help));
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

    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {

    }
}
