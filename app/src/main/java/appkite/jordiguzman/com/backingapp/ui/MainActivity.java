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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import appkite.jordiguzman.com.backingapp.R;
import appkite.jordiguzman.com.backingapp.adapters.AdapterMain;
import appkite.jordiguzman.com.backingapp.model.Recipe;

public class MainActivity extends AppCompatActivity implements AdapterMain.ListItemClickListener{


    private final String LOG_TAG = MainActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    public static ArrayList<Recipe> mRecipes = new ArrayList<>();
    private CoordinatorLayout coordinatorLayout;
    public static boolean tablet, landscape;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(LOG_TAG, "onCreate");
        coordinatorLayout = findViewById(R.id.coordinator_layout);

        tablet = getResources().getBoolean(R.bool.isTablet);
        landscape = getResources().getBoolean(R.bool.landscape);


        recyclerView = findViewById(R.id.rv_main);
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
    public void snackBar(){
        Snackbar snackbar = Snackbar
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
        snackbar.setActionTextColor(Color.RED);
        View sbView = snackbar.getView();
        TextView textView = sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        snackbar.show();

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

    }

    @Override
    public void onListItemClicked(int clickedItemIndex) {

        Recipe recipe = mRecipes.get(clickedItemIndex);

        Intent intent = new Intent(MainActivity.this, DetailStepsActivity.class);
        intent.putExtra("recipe", recipe);
        intent.putParcelableArrayListExtra("recipeList", mRecipes);
        intent.putExtra("position", clickedItemIndex);

        startActivity(intent);

    }
}
