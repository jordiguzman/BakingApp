package appkite.jordiguzman.com.backingapp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import appkite.jordiguzman.com.backingapp.R;
import appkite.jordiguzman.com.backingapp.fragments.DetailRecipeFragment;
import appkite.jordiguzman.com.backingapp.fragments.IngredientsFragment;

public class IngredientsActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);



        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        String name = bundle.getString("name");
        assert name != null;
        setTitle(getResources().getString(R.string.ingredients).concat(" ").concat(name));

        if (savedInstanceState==null){
            IngredientsFragment fragment = new IngredientsFragment();
             getSupportFragmentManager().beginTransaction()
                     .replace(R.id.container_ingredients, fragment)
                     .commit();
        }


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                releaseExoPlayer();
                IngredientsActivity.this.onBackPressed();
                break;

        }
        return true;
    }
    public void releaseExoPlayer(){
        if (DetailRecipeFragment.mSimpleExoPlayer != null)DetailRecipeFragment.mSimpleExoPlayer.release();
    }
}
