package appkite.jordiguzman.com.backingapp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import appkite.jordiguzman.com.backingapp.R;
import appkite.jordiguzman.com.backingapp.fragments.DetailFragment;
import appkite.jordiguzman.com.backingapp.model.Ingredients;
import appkite.jordiguzman.com.backingapp.model.Step;

public class DetailIngredients extends AppCompatActivity {


    private String name;
    public static ArrayList<Ingredients> mIngredients;
    public static ArrayList<Step> mStep;
    private int position;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_ingredients);




        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            mStep = bundle.getParcelableArrayList("steps");
            mIngredients = bundle.getParcelableArrayList("ingredients");
            name= bundle.getString("name");
            position = bundle.getInt("position");
        }


        setTitle(name);
        if (savedInstanceState==null){
            DetailFragment fragment = new DetailFragment();
            Bundle bundleToFragment = new Bundle();
            bundleToFragment.putInt("position", position);

            fragment.setArguments(bundleToFragment);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container_detail_fragment, fragment)
                    .commit();
        }

    }


}
