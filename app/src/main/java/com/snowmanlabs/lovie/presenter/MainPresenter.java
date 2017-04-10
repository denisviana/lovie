package com.snowmanlabs.lovie.presenter;

import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.snowmanlabs.lovie.model.Filme;
import com.snowmanlabs.lovie.model.FilmesCollection;
import com.snowmanlabs.lovie.model.dao.FilmeDAO;
import com.snowmanlabs.lovie.mvp_interface.MVP;
import com.snowmanlabs.lovie.retrofit.IRetrofit;
import com.snowmanlabs.lovie.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by denisvcosta on 08/04/2017.
 *
 * O Presenter faz a comunição entre os models e a View e processa todas as requisições
 *
 */

public class MainPresenter implements MVP.IPresenterFilmes {

    private static final String API_KEY = "b5127d1016c968d30de8d0d6cc725a73";
    private static final String LANGUAGE = "pt-BR";
    private static int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    public static final int POPULARES = 910;
    public static final int AVALIACAO = 928;
    public static final int FAVORITOS = 999;
    private static int FILTRO = 0;

    private int TOTAL_PAGES = 0;
    private int currentPage = PAGE_START;

    private IRetrofit retrofitEndpoint;
    private FilmeDAO filmeDAO;

    private MVP.IViewFilmes view;
    private MVP.IDetalhesFilme view_detalhes;

    public MainPresenter(){

    }

    //Faz a requisição a API solicitando os filmes filtrados por Popularidade
    @Override
    public void carregarFilmes(int filtro) {

        view.carregando(View.VISIBLE);

        retrofitEndpoint = RetrofitClient.getRetrofitClient().create(IRetrofit.class);

        FILTRO = filtro;
        currentPage = PAGE_START;

        callFilmesPopulares(FILTRO).enqueue(new Callback<FilmesCollection>() {

            @Override
            public void onResponse(Call<FilmesCollection> call, Response<FilmesCollection> response) {
                Log.i("Codigo",String.valueOf(response.code()));
                if(response.code() == 200){
                    view.carregando(View.GONE);

                    view.exibirFilmes(response.body());
                    TOTAL_PAGES = response.body().getTotalPages();
                    if(currentPage <= TOTAL_PAGES)
                        view.addLoadingFooter();
                    else
                        isLastPage = true;
                }

            }

            @Override
            public void onFailure(Call<FilmesCollection> call, Throwable t) {
                Log.i("Codigo Erro: ",t.getMessage());
            }
        });


    }

    //Faz a requisição ao Banco de Dados local solicitando os filmes Favoritos
    @Override
    public void carregarFavoritos(int filtro) {

        filmeDAO = new FilmeDAO(view.getContext());

        FILTRO = filtro;

        FilmesCollection filmes = new FilmesCollection();

        filmes.setResults(filmeDAO.obterFavoritos());

        view.exibirFilmes(filmes);
    }

    //Faz a paginação
    @Override
    public void loadMore() {

        if(FILTRO != FAVORITOS){

            isLoading = true;
            currentPage += 1;

            // mocking network delay for API call
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    proximaPagina();
                }
            }, 1000);
        }

    }


    @Override
    public void setView(MVP.IViewFilmes view) {
        this.view = view;
    }

    @Override
    public void setViewDetalhes(MVP.IDetalhesFilme view_detalhes) {
        this.view_detalhes = view_detalhes;
    }

    //Proxima requisição de filmes, fazendo a paginação
    @Override
    public void proximaPagina() {

            callFilmesPopulares(FILTRO).enqueue(new Callback<FilmesCollection>() {

            @Override
            public void onResponse(Call<FilmesCollection> call, Response<FilmesCollection> response) {

                view.removeLoadingFooter();

                isLoading = false;

                view.addAll(response.body().getFilmes());

                if(currentPage <= TOTAL_PAGES)
                    view.addLoadingFooter();
                else
                    isLastPage = true;


            }

            @Override
            public void onFailure(Call<FilmesCollection> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean isLastPage() {
        return isLastPage;
    }

    @Override
    public boolean isLoading() {
        return isLoading;
    }

    @Override
    public int totalPages() {
        return TOTAL_PAGES;
    }


    //Carrega os detalhes de um filme
    @Override
    public void abrirDetalhesFilme(int id) {

        filmeDAO = new FilmeDAO(view_detalhes.getContext());

        if(filmeDAO.jaExiste(id)){
            Filme filme = filmeDAO.buscaFilme(id);
            view_detalhes.carregamentoCompleto();
            view_detalhes.exibirDetalhesFilme(filme);
            return;
        }

        retrofitEndpoint = RetrofitClient.getRetrofitClient().create(IRetrofit.class);

        callDetalhesFilme(id).enqueue(new Callback<Filme>() {
            @Override
            public void onResponse(Call<Filme> call, Response<Filme> response) {
                if(response.code()==200){
                    view_detalhes.carregamentoCompleto();
                    view_detalhes.exibirDetalhesFilme(response.body());
                }
            }

            @Override
            public void onFailure(Call<Filme> call, Throwable t) {

            }
        });
    }

    //Adiciona ou Remove um filme dos Favoritos
    @Override
    public void addOrRemoveFavorite(final Filme filme) {

        filmeDAO = new FilmeDAO(view_detalhes.getContext());

            if(filmeDAO.jaExiste(filme.getId())){
                filmeDAO.removeFavorito(filme.getId());
                view_detalhes.removeFavorito();
            } else {
                filme.setFavorite(true);
                filmeDAO.addFavorito(filme);
                view_detalhes.addFavorito();
            }
    }

    private Call<FilmesCollection> callFilmesPopulares(int Filtro){


        switch(Filtro){
            case POPULARES:

                return retrofitEndpoint.getFilmesPopulares(API_KEY,LANGUAGE,currentPage);

            case AVALIACAO:

                return retrofitEndpoint.getFilmesPorAvalicao(API_KEY,LANGUAGE,currentPage);

            case FAVORITOS:

        }
        return null;

    }

    private Call<Filme> callDetalhesFilme(int id){

        return retrofitEndpoint.getDetalhesFilme(id,API_KEY,LANGUAGE);
    }

}
