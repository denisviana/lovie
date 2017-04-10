package com.snowmanlabs.lovie.mvp_interface;

import android.content.Context;

import com.snowmanlabs.lovie.model.Filme;
import com.snowmanlabs.lovie.model.FilmesCollection;

import java.util.List;

/**
 * Created by denisvcosta on 08/04/2017.
 *
 * A interface MVP fornece um contrato entre as Views, o Presenter e os Models
 *
 */

public interface MVP {


    interface IPresenterFilmes{
        void carregarFilmes(int filtro);
        void carregarFavoritos(int filtro);
        void loadMore();
        void setView(MVP.IViewFilmes view);
        void setViewDetalhes(MVP.IDetalhesFilme view_detalhes);
        void proximaPagina();
        boolean isLastPage();
        boolean isLoading();
        int totalPages();
        void abrirDetalhesFilme(int id);
        void addOrRemoveFavorite(Filme filme);
    }

    interface IViewFilmes{
        void initViews();
        void addAll(List<Filme> filmes);
        void removeLoadingFooter();
        void addLoadingFooter();
        Context getContext();
        void carregando(int status);
        void exibirFilmes(FilmesCollection filmes);
    }



    interface IAdapterRecyclerView{
        void add(Filme f);
        void addAll(List<Filme> filmes);
        void remove(Filme f);
        void clear();
        boolean isEmpty();
        void addLoadingFooter();
        void removeLoadingFooter();
        Filme getItem(int position);
        List<Filme> getMovies();
        void setMovies(List<Filme> movieResults);
    }


    interface IDetalhesFilme{
        Context getContext();
        void carregamentoCompleto();
        void addFavorito();
        void removeFavorito();
        void initViews();
        void exibirDetalhesFilme(Filme filme);

    }

}
