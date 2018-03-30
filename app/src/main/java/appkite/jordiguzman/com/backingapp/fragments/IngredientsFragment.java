package appkite.jordiguzman.com.backingapp.fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import appkite.jordiguzman.com.backingapp.R;
import appkite.jordiguzman.com.backingapp.adapters.AdapterIngredients;
import butterknife.BindView;
import butterknife.ButterKnife;


public class IngredientsFragment extends Fragment{


    @BindView(R.id.rv_ingredients)
    RecyclerView mRecyclerView;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.activity_ingredients, container, false);
        ButterKnife.bind(this, rootView);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setHasFixedSize(true);
        AdapterIngredients mAdapterIngredients = new AdapterIngredients(getContext());
        mRecyclerView.setAdapter(mAdapterIngredients);

        return rootView;
    }
}
