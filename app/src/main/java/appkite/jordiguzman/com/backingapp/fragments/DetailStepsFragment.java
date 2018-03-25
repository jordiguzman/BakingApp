package appkite.jordiguzman.com.backingapp.fragments;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import appkite.jordiguzman.com.backingapp.R;
import appkite.jordiguzman.com.backingapp.adapters.AdapterDetail;
import appkite.jordiguzman.com.backingapp.model.Ingredients;
import appkite.jordiguzman.com.backingapp.model.Recipe;
import appkite.jordiguzman.com.backingapp.model.Step;
import appkite.jordiguzman.com.backingapp.ui.DetailRecipeActivity;
import appkite.jordiguzman.com.backingapp.ui.MainActivity;

public class DetailStepsFragment extends Fragment implements AdapterDetail.StepsClickListener {


    public static ArrayList<Step> steps;
    public static ArrayList<Ingredients> ingredients;
    private String name;

    private int position;


    public DetailStepsFragment(){

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.activity_detail_steps, container, false);

        if (getArguments() != null){
             position= getArguments().getInt("position");
            Recipe recipe = getArguments().getParcelable("recipe");


            if (recipe != null){
                steps= recipe.steps;
                name = recipe.name;
                ingredients = recipe.ingredients;
            }else {
                steps = MainActivity.mRecipes.get(position).steps;
                name = MainActivity.mRecipes.get(position).name;
                ingredients = MainActivity.mRecipes.get(position).ingredients;
            }
        }
        assert getActivity() != null;
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);



        RecyclerView mRecyclerView = rootView.findViewById(R.id.rv_detail);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setHasFixedSize(true);


        AdapterDetail mAdapterDetail = new AdapterDetail(steps, getContext(), DetailStepsFragment.this);
        mRecyclerView.setAdapter(mAdapterDetail);

        return rootView;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClickStep(int position) {
        Intent intent = new Intent(getContext(), DetailRecipeActivity.class);
        intent.putParcelableArrayListExtra("steps", steps);
        intent.putParcelableArrayListExtra("ingredients", ingredients);
        intent.putExtra("name", MainActivity.mRecipes.get(position).name);
        intent.putExtra("position", position);
        startActivity(intent);
    }

}
