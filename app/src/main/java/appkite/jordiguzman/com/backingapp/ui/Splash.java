package appkite.jordiguzman.com.backingapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import appkite.jordiguzman.com.backingapp.R;
import appkite.jordiguzman.com.backingapp.interfaces.ApiInterface;
import appkite.jordiguzman.com.backingapp.model.Recipe;
import appkite.jordiguzman.com.backingapp.utils.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static appkite.jordiguzman.com.backingapp.ui.MainActivity.mRecipes;

public class Splash extends AppCompatActivity {

    public static ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        apiInterface = ApiClient.getApiClient()
                .create(ApiInterface.class);
        delay();
        loadData();

    }


    private void delay() {
        CountDownTimer mCt = new CountDownTimer(2500, 200) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                toMain();
                finish();
            }
        };
        mCt.start();
    }


    private void toMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void loadData() {
        Call<List<Recipe>> call = apiInterface.getReciples();

        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(@NonNull Call<List<Recipe>> call, @NonNull Response<List<Recipe>> response) {
                mRecipes = (ArrayList<Recipe>) response.body();
            }

            @Override
            public void onFailure(@NonNull Call<List<Recipe>> call, @NonNull Throwable t) {

            }
        });
    }


    @Override
    public void onBackPressed() {

    }
}
