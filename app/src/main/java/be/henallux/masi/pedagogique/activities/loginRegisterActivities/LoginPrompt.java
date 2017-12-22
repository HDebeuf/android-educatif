package be.henallux.masi.pedagogique.activities.loginRegisterActivities;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import be.henallux.masi.pedagogique.R;

public class LoginPrompt extends AppCompatActivity {
//https://code.tutsplus.com/tutorials/creating-a-login-screen-using-textinputlayout--cms-24168
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_prompt);
        final TextInputLayout usernameWrapper = (TextInputLayout) findViewById(R.id.usernameWrapper);
        final TextInputLayout passwordWrapper = (TextInputLayout) findViewById(R.id.passwordWrapper);
        usernameWrapper.setHint("Utilisateur");
        passwordWrapper.setHint("Mot de passe");
    }

}
