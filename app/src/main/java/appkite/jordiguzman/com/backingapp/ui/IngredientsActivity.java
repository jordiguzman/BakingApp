package appkite.jordiguzman.com.backingapp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import appkite.jordiguzman.com.backingapp.R;
import appkite.jordiguzman.com.backingapp.adapters.AdapterIngredients;

public class IngredientsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);

        RecyclerView mRecyclerView = findViewById(R.id.rv_ingredients);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setHasFixedSize(true);

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        String name = bundle.getString("name");
        assert name != null;
        setTitle(getResources().getString(R.string.ingredients).concat(" ").concat(name));
        AdapterIngredients mAdapterIngredients = new AdapterIngredients(DetailRecipes.ingredients, this);
        mRecyclerView.setAdapter(mAdapterIngredients);




    }
}
