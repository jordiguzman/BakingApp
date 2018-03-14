package appkite.jordiguzman.com.backingapp.interfaces;

import java.util.List;

import appkite.jordiguzman.com.backingapp.model.Recipe;
import retrofit2.Call;
import retrofit2.http.GET;



public interface ApiInterface {

    @GET("59121517_baking/baking.json")
   Call<List<Recipe>> getReciples();
}
