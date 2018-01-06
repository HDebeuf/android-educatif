package be.henallux.masi.pedagogique.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.creativityapps.gmailbackgroundlibrary.BackgroundMail;
import com.facebook.stetho.Stetho;
import be.henallux.masi.pedagogique.activities.loginActivity.LoginPromptActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Stetho.initializeWithDefaults(this);//Allow to visualize DB from chrome://inspect

        Intent intent = new Intent(this, LoginPromptActivity.class);
        startActivity(intent);
    }
}
