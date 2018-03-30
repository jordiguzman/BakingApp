package appkite.jordiguzman.com.backingapp.ui;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import appkite.jordiguzman.com.backingapp.R;
import appkite.jordiguzman.com.backingapp.adapters.AdapterMain;
import appkite.jordiguzman.com.backingapp.model.Recipe;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements AdapterMain.ListItemClickListener{


    public static ArrayList<Recipe> mRecipes = new ArrayList<>();
    public static boolean tablet, landscape, portrait;

    private Snackbar mSnackbar;
    @BindView(R.id.rv_main)
    RecyclerView recyclerView;
    @BindView(R.id.coordinator_layout)
    CoordinatorLayout coordinatorLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        isTablet(this);
        isLandscape(this);

        if (!tablet){
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
        }else{
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        }
        if (landscape){
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        }

        recyclerView.setHasFixedSize(true);

        if (isOnline()){
            snackBar();
            return;
        }

        loadData();

    }

    public static void isTablet(Context context){
        tablet = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE);

    }
    public static void isLandscape(Context context){
        int orientation = context.getResources().getConfiguration().orientation;

       switch (orientation){
           case 1:
               landscape = false;
               portrait = true;
               break;
           case 2:
               landscape= true;
               portrait = false;
               break;
       }

    }

    public void snackBar(){
        mSnackbar = Snackbar
                .make(coordinatorLayout, "No network connection!", Snackbar.LENGTH_INDEFINITE)
                .setAction("RETRY", new View.OnClickListener() {
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


    private boolean isOnline() {

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo == null || !netInfo.isConnectedOrConnecting();
    }


    public void loadData(){
        AdapterMain mAdapterMain = new AdapterMain(mRecipes, MainActivity.this, this);
        recyclerView.setAdapter(mAdapterMain);
        recyclerView.setHasFixedSize(true);

    }

    @Override
    public void onListItemClicked(int clickedItemIndex) {
        if (isOnline()){
            snackBar();
            return;
        }else {
            if (mSnackbar != null){
                mSnackbar.dismiss();
            }
        }
        Recipe recipe = mRecipes.get(clickedItemIndex);

        Intent intent = new Intent(MainActivity.this, DetailStepsActivity.class);
        intent.putExtra("recipe", recipe);
        intent.putParcelableArrayListExtra("recipeList", mRecipes);
        intent.putExtra("position", clickedItemIndex);

        startActivity(intent);

    }


}
