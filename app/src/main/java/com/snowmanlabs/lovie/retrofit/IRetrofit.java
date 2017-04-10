package com.snowmanlabs.lovie.retrofit;

import com.snowmanlabs.lovie.model.Filme;
import com.snowmanlabs.lovie.model.FilmesCollection;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by denisvcosta on 08/04/2017.
 */

public interface IRetrofit {

    @GET("popular")
    Call<FilmesCollection> getFilmesPopulares(@Query("api_key") String api_key, @Query("language") String lang, @Query("page") int page);

    @GET("top_rated")
    Call<FilmesCollection> getFilmesPorAvalicao(@Query("api_key") String api_key, @Query("language") String lang, @Query("page") int page);

    @GET("{id}")
    Call<Filme> getDetalhesFilme(@Path("id") int id,@Query("api_key") String api_key,@Query("language") String lang);
}
