package be.henallux.masi.pedagogique.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import be.henallux.masi.pedagogique.model.Category;
import be.henallux.masi.pedagogique.utils.Constants;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, MainMenuActivity.class);
        intent.putExtra(Constants.KEY_CATEGORY_USER,new Category(2,"Cycle inférieur",6,8)); //TODO : replace with prompt to user
        startActivity(intent);
    }
}
