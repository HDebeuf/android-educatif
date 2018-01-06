package be.henallux.masi.pedagogique.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import be.henallux.masi.pedagogique.R;
import be.henallux.masi.pedagogique.activities.loginActivity.LoginPromptActivity;
import be.henallux.masi.pedagogique.activities.mapActivity.MapsActivity;
import be.henallux.masi.pedagogique.activities.musicalActivity.MusicalActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class UITestActivity extends AppCompatActivity {

    @BindView(R.id.button_login)
    Button buttonLogin;

    @BindView(R.id.button_map)
    Button buttonMap;

    @BindView(R.id.button_synthesis)
    Button buttonSynthesis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_test_menu);

        ButterKnife.bind(this);

        buttonMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UITestActivity.this, MapsActivity.class);
                startActivity(i);
            }
        });

        buttonLogin.setText("Musique");
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UITestActivity.this, MusicalActivity.class);
                startActivity(i);
            }
        });
        buttonSynthesis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),R.string.ui_test_correct_intent, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
