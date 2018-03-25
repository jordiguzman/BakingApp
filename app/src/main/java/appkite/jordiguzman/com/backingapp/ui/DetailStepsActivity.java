package appkite.jordiguzman.com.backingapp.ui;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

import appkite.jordiguzman.com.backingapp.R;
import appkite.jordiguzman.com.backingapp.adapters.AdapterDetail;
import appkite.jordiguzman.com.backingapp.model.Ingredients;
import appkite.jordiguzman.com.backingapp.model.Recipe;
import appkite.jordiguzman.com.backingapp.model.Step;
import jp.wasabeef.blurry.Blurry;

public class DetailStepsActivity extends AppCompatActivity implements AdapterDetail.StepsClickListener {

    private final String LOG_TAG = DetailStepsActivity.class.getSimpleName();
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private int position;
    private Recipe recipe;
    private Integer[] imageValues = {R.drawable.nutella_pie, R.drawable.brownie, R.drawable.yellow_cake
            , R.drawable.cheesecake};
    public static ArrayList<Step> steps;
    public static ArrayList<Ingredients> ingredients;
    private String name;
    private ImageView iv_detail;
    private boolean twoPanes= true;


    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_steps);
        collapsingToolbarLayout = findViewById(R.id.collapsing_detail);
        iv_detail = findViewById(R.id.iv_detail);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        String ingredientsTilte = "Steps";
        collapsingToolbarLayout.setTitle(ingredientsTilte);

        imageAndTextCollapsingToolbar();
        imageBlurDetail();


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            position = bundle.getInt("position");
            recipe = bundle.getParcelable("recipe");

        }
        if (recipe != null){
            steps= recipe.steps;
            name= recipe.name;
            ingredients = recipe.ingredients;

        }else {
            steps = MainActivity.mRecipes.get(position).steps;
            name = MainActivity.mRecipes.get(position).name;
            ingredients = MainActivity.mRecipes.get(position).ingredients;
        }
        RecyclerView mRecyclerView = findViewById(R.id.rv_detail);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setHasFixedSize(true);


        AdapterDetail mAdapterDetail = new AdapterDetail(steps, this, DetailStepsActivity.this );
        mRecyclerView.setAdapter(mAdapterDetail);

        /*if (savedInstanceState == null){
            DetailStepsFragment fragment = new DetailStepsFragment();
            Bundle bundleToFragments= new Bundle();
            bundleToFragments.putInt("position", position);

            fragment.setArguments(bundleToFragments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.steps_detail_container, fragment)
                    .commit();

        }*/


    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void imageBlurDetail(){
        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),
                imageValues[position]);

        Blurry.with(this)
                .async()
                .from(bitmap)
                .into(iv_detail);
    }

    public void imageAndTextCollapsingToolbar() {

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                imageValues[position]);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {

            @SuppressLint("ResourceAsColor")
            @Override
            public void onGenerated(@NonNull Palette palette) {
                collapsingToolbarLayout.setContentScrimColor(R.color.colorPrimary);
                collapsingToolbarLayout.setStatusBarScrimColor(R.color.colorPrimaryDark);
            }
        });
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsedappbar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.expandedappbar);

    }
    public void clickIngredients(View view){


            Intent intent = new Intent(this, IngredientsActivity.class);
            intent.putExtra("name", name);
            startActivity(intent);




    }

    @Override
    public void onClickStep(int position) {
        Intent intent = new Intent(this, DetailRecipeActivity.class);
        intent.putParcelableArrayListExtra("steps", steps);
        intent.putParcelableArrayListExtra("ingredients", ingredients);
        intent.putExtra("name", name);
        intent.putExtra("position", position);
        startActivity(intent);
    }
}
