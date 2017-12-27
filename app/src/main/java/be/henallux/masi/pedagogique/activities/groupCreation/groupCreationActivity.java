package be.henallux.masi.pedagogique.activities.groupCreation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import be.henallux.masi.pedagogique.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class groupCreationActivity extends AppCompatActivity {

    @BindView(R.id.recyclerview_item_group_creation)
    RecyclerView groupRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_group_creation);
        ButterKnife.bind(this);
    }
}
