package appkite.jordiguzman.com.backingapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import appkite.jordiguzman.com.backingapp.R;
import appkite.jordiguzman.com.backingapp.adapters.AdapterMain;
import appkite.jordiguzman.com.backingapp.model.Recipe;

public class MainActivity extends AppCompatActivity implements AdapterMain.ListItemClickListener{


    private RecyclerView recyclerView;
    public static ArrayList<Recipe> mRecipes = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rv);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        loadData();

    }
    public void loadData(){
        AdapterMain mAdapterMain = new AdapterMain(mRecipes, MainActivity.this, this);
        recyclerView.setAdapter(mAdapterMain);
    }

    @Override
    public void onListItemClicked(int clickedItemIndex) {

        Recipe recipe = mRecipes.get(clickedItemIndex);

        Intent intent = new Intent(MainActivity.this, DetailRecipes.class);
        intent.putExtra("recipe", recipe);
        intent.putParcelableArrayListExtra("recipeList", mRecipes);
        intent.putExtra("position", clickedItemIndex);

        startActivity(intent);

    }
}
