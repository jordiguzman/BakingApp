package appkite.jordiguzman.com.backingapp.utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class ApiClient {

    private static final String BASE_URL=
            "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/";
    private static Retrofit retrofit = null;


    public static Retrofit getApiClient(){
        if (retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return retrofit;
    }
}
