package appkite.jordiguzman.com.backingapp.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import appkite.jordiguzman.com.backingapp.R;
import appkite.jordiguzman.com.backingapp.fragments.DetailRecipeFragment;
import appkite.jordiguzman.com.backingapp.model.Ingredients;
import appkite.jordiguzman.com.backingapp.model.Step;

public class DetailRecipeActivity extends AppCompatActivity {


    public static ArrayList<Ingredients> mIngredients;
    public static ArrayList<Step> mStep;
    private String name;
    private int position;

    private CoordinatorLayout coordinatorLayout;
    private Snackbar mSnackbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        coordinatorLayout = findViewById(R.id.coordinator_layout);
        if (isOnline()){
            snackBar();
            return;
        }else {
            if (mSnackbar != null){
                mSnackbar.dismiss();
            }
        }


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
                bundleToFragment.putParcelableArrayList("steps", mStep);

                fragment.setArguments(bundleToFragment);
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container_detail_recipe_fragment, fragment)
                        .commit();


        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                releaseExoPlayer();
                DetailRecipeActivity.this.onBackPressed();
                break;

        }
        return true;
    }
    public void releaseExoPlayer(){
        if (DetailRecipeFragment.mSimpleExoPlayer != null)DetailRecipeFragment.mSimpleExoPlayer.release();
    }
    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo == null || !netInfo.isConnectedOrConnecting();
    }

    public void snackBar(){
         mSnackbar= Snackbar
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

}
