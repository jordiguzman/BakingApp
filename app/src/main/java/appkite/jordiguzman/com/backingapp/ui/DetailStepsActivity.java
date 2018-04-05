package appkite.jordiguzman.com.backingapp.ui;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import appkite.jordiguzman.com.backingapp.R;
import appkite.jordiguzman.com.backingapp.adapters.AdapterDetail;
import appkite.jordiguzman.com.backingapp.fragments.DetailRecipeFragment;
import appkite.jordiguzman.com.backingapp.fragments.IngredientsFragment;
import appkite.jordiguzman.com.backingapp.model.Ingredients;
import appkite.jordiguzman.com.backingapp.model.Recipe;
import appkite.jordiguzman.com.backingapp.model.Step;
import butterknife.BindView;
import butterknife.ButterKnife;

import static appkite.jordiguzman.com.backingapp.ui.MainActivity.tablet;

public class DetailStepsActivity extends AppCompatActivity implements AdapterDetail.StepsClickListener {



    private int position;
    private Recipe recipe;
    private Integer[] imageValues = {R.drawable.nutella_pie, R.drawable.brownie, R.drawable.yellow_cake
            , R.drawable.cheesecake};
    public static ArrayList<Step> steps;
    public static ArrayList<Ingredients> ingredients;
    private String name;
    @BindView(R.id.iv_detail)
    ImageView iv_detail;
    private boolean rotateScreen;
    @BindView(R.id.collapsing_detail)
    CollapsingToolbarLayout collapsingToolbarLayout;
    private ConstraintLayout constraintLayoutDetail,  constraintLayoutIngredients;
    @BindView(R.id.coordinator_layout)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.rv_detail)
    RecyclerView mRecyclerView;
    private Snackbar mSnackbar;


    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_steps);
        ButterKnife.bind(this);

        if (savedInstanceState != null){
            rotateScreen = savedInstanceState.getBoolean("rotateScreen");
        }

        if (isOnline()){
            snackBar();
            return;
        }
        MainActivity.isLandscape(this);
        MainActivity.isTablet(this);
        Log.i("Valor Tablet", String.valueOf(tablet));


        constraintLayoutDetail =  findViewById(R.id.container_detail);
        constraintLayoutIngredients =  findViewById(R.id.container_ingredients);


        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);


        imageAndTextCollapsingToolbar();



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
        imageDetail();
        collapsingToolbarLayout.setTitle(name);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setHasFixedSize(true);

        setTitle(name);

        AdapterDetail mAdapterDetail = new AdapterDetail(steps, this, DetailStepsActivity.this );
        mRecyclerView.setAdapter(mAdapterDetail);
        if (tablet){
            hideDetailRecipe();
            hideIngredients();
        }
        if (rotateScreen)showDetailRecipe();


    }
    public void hideDetailRecipe(){
        if (constraintLayoutDetail != null)constraintLayoutDetail.setVisibility(View.INVISIBLE);
    }
    public void showDetailRecipe(){
        if (constraintLayoutDetail != null)constraintLayoutDetail.setVisibility(View.VISIBLE);
    }
    public void hideIngredients(){
        if (constraintLayoutDetail != null)constraintLayoutIngredients.setVisibility(View.INVISIBLE);
    }
    public void showIngredients(){
        if (constraintLayoutDetail != null)constraintLayoutIngredients.setVisibility(View.VISIBLE);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void imageDetail(){
        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),
                imageValues[position]);
        Glide.with(this)
                .load(bitmap)
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
        if (isOnline()){
            snackBar();
            return;
        }

        if (tablet){
            hideDetailRecipe();
            showIngredients();
            if (DetailRecipeFragment.mSimpleExoPlayer != null){
                DetailRecipeFragment.mSimpleExoPlayer.release();
            }

            IngredientsFragment fragment = new IngredientsFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container_ingredients, fragment)
                    .commit();
        }else {
            Intent intent = new Intent(this, IngredientsActivity.class);
            intent.putExtra("name", name);
            startActivity(intent);
        }

    }

    @Override
    public void onClickStep(int position) {
        if (isOnline()){
            snackBar();
            return;
        }else {
            if (mSnackbar != null){
                mSnackbar.dismiss();
            }
        }

        if (tablet){
            showDetailRecipe();
            hideIngredients();
            DetailRecipeFragment fragment = new DetailRecipeFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("steps", steps);
            bundle.putInt("position", position);
            bundle.putString("name", name);
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container_detail, fragment)
                    .commit();

        }else {
            Intent intent = new Intent(this, DetailRecipeActivity.class);
            intent.putParcelableArrayListExtra("steps", steps);
            intent.putParcelableArrayListExtra("ingredients", ingredients);
            intent.putExtra("name", name);
            intent.putExtra("position", position);
            startActivity(intent);
        }
        rotateScreen = true;
    }


    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo == null || !netInfo.isConnectedOrConnecting();
    }

    public void snackBar(){
        mSnackbar = Snackbar
                .make(coordinatorLayout, getResources().getString(R.string.no_network), Snackbar.LENGTH_INDEFINITE)
                .setAction(getResources().getString(R.string.retry), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isOnline()) {
                            snackBar();
                            return;
                        }
                        Intent intent = new Intent(getApplicationContext(), Splash.class);
                        startActivity(intent);
                        finish();
                    }
                });
        mSnackbar.setActionTextColor(Color.RED);
        View sbView = mSnackbar.getView();
        TextView textView = sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        mSnackbar.show();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("rotateScreen", rotateScreen);
    }

}
