package appkite.jordiguzman.com.backingapp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import appkite.jordiguzman.com.backingapp.R;
import appkite.jordiguzman.com.backingapp.fragments.DetailRecipeFragment;
import appkite.jordiguzman.com.backingapp.model.Ingredients;
import appkite.jordiguzman.com.backingapp.model.Step;

public class DetailRecipeActivity extends AppCompatActivity {

    private final String LOG_TAG = DetailRecipeActivity.class.getSimpleName();
    public static ArrayList<Ingredients> mIngredients;
    public static ArrayList<Step> mStep;
    private String name;
    private int position;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container_detail_recipe);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mStep = bundle.getParcelableArrayList("steps");
            mIngredients = bundle.getParcelableArrayList("ingredients");
            name = bundle.getString("name");
            position = bundle.getInt("position");
        }


        setTitle(name);
        if (savedInstanceState == null) {
            DetailRecipeFragment fragment = new DetailRecipeFragment();
            Bundle bundleToFragment = new Bundle();
            bundleToFragment.putInt("position", position);

            fragment.setArguments(bundleToFragment);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container_detail_recipe_fragment, fragment)
                    .commit();
        }

    }


}
