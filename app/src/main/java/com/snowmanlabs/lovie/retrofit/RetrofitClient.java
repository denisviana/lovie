package com.snowmanlabs.lovie.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by denisvcosta on 08/04/2017.
 */

public class RetrofitClient {

    public static final String BASE_URL = "http://api.themoviedb.org/3/movie/";
    private static Retrofit retrofit = null;

    private static Gson gson = new GsonBuilder().create();

    public static Retrofit getRetrofitClient(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }

        return retrofit;
    }
}
