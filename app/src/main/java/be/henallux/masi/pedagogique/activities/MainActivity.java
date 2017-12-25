package be.henallux.masi.pedagogique.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.facebook.stetho.Stetho;

import com.creativityapps.gmailbackgroundlibrary.BackgroundMail;

import be.henallux.masi.pedagogique.activities.loginActivity.LoginPromptActivity;
import be.henallux.masi.pedagogique.activities.loginActivity.LoginPromptActivity_ViewBinding;
import be.henallux.masi.pedagogique.model.Category;
import be.henallux.masi.pedagogique.utils.Constants;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Stetho.initializeWithDefaults(this);//Allow to visualize DB from chrome://inspect

        Intent intent = new Intent(this, LoginPromptActivity.class);
        startActivity(intent);
        //Intent i = new Intent(this, UITestActivity.class);
        //startActivity(i);
    }
}
