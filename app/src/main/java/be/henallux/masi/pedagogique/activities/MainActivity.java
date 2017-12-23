package be.henallux.masi.pedagogique.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.facebook.stetho.Stetho;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Stetho.initializeWithDefaults(this);//Allow to visualize DB from chrome://inspect
        //setContentView(R.layout.activity_main);
       // Intent intent = new Intent(this, MainMenuActivity.class);
       // intent.putExtra(Constants.KEY_CATEGORY_USER,new Category(2,"Cycle inférieur",6,8)); //TODO : replace with prompt to user
        // startActivity(intent);
        Intent i = new Intent(this, UITestActivity.class);
        startActivity(i);
    }
}
