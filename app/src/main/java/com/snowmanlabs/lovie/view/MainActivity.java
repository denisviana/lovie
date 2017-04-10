package com.snowmanlabs.lovie.view;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.snowmanlabs.lovie.R;
import com.snowmanlabs.lovie.model.Filme;
import com.snowmanlabs.lovie.model.FilmesCollection;
import com.snowmanlabs.lovie.mvp_interface.MVP;
import com.snowmanlabs.lovie.presenter.MainPresenter;
import com.snowmanlabs.lovie.util.PaginationScrollListener;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MVP.IViewFilmes, View.OnClickListener {

    private RecyclerView filmesRecyclerView;
    private AdapterGridFilmes adapterGridFilmes;
    private MVP.IPresenterFilmes presenter;
    private FilmesCollection filmesList;

    private Toolbar toolbar;
    private ProgressBar progressBar;
    private TextView btnFiltroPopular, btnFiltroAvalicao, btnFiltroFavoritos;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        toolbar.setTitle("");
        toolbar.setLogo(R.drawable.ic_logo);
        setSupportActionBar(toolbar);

        presenter = new MainPresenter();
        presenter.setView(this);

        adapterGridFilmes = new AdapterGridFilmes(this,presenter);

        filmesRecyclerView.setHasFixedSize(true);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        filmesRecyclerView.setLayoutManager(gridLayoutManager);
        filmesRecyclerView.setItemAnimator(new DefaultItemAnimator());

        //Captura o movimento de Scroll do RecyclerView para fazer a paginação dos dados
        filmesRecyclerView.addOnScrollListener(new PaginationScrollListener(gridLayoutManager) {
            @Override
            protected void loadMoreItems() {
                presenter.loadMore(); //Faz uma nova requisição de dados
            }

            @Override
            public int getTotalPageCount() {
                return presenter.totalPages();
            }

            @Override
            public boolean isLastPage() {
                return presenter.isLastPage();
            }

            @Override
            public boolean isLoading() {
                return presenter.isLoading();
            }

        });

        btnFiltroPopular.setOnClickListener(this);
        btnFiltroAvalicao.setOnClickListener(this);
        btnFiltroFavoritos.setOnClickListener(this);

        presenter.carregarFilmes(MainPresenter.POPULARES);

    }


    //Inicia todas as views
    @Override
    public void initViews() {
        progressBar = (ProgressBar) findViewById(R.id.main_progress);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        filmesRecyclerView = (RecyclerView) findViewById(R.id.grid_filmes);
        btnFiltroPopular = (TextView) findViewById(R.id.btn_filtro_popular);
        btnFiltroAvalicao = (TextView) findViewById(R.id.btn_filtro_avaliacao);
        btnFiltroFavoritos = (TextView) findViewById(R.id.btn_filtro_favoritos);
    }

    @Override
    public void addAll(List<Filme> filmes) {
        adapterGridFilmes.addAll(filmes);
    }

    @Override
    public void removeLoadingFooter() {
        adapterGridFilmes.removeLoadingFooter();
    }

    @Override
    public void addLoadingFooter() {
        adapterGridFilmes.addLoadingFooter();
    }

    @Override
    public Context getContext(){
        return this;
    }


    @Override
    public void carregando(int status) {
        progressBar.setVisibility(status);
    }


    //Alimenta o Grid com os filmes baixados
    @Override
    public void exibirFilmes(FilmesCollection filmes) {
        filmesList = filmes;
        adapterGridFilmes.clear();
        adapterGridFilmes.addAll(filmes.getFilmes());
        filmesRecyclerView.setAdapter(adapterGridFilmes);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_filtro_popular: //Filtra por popularidade
                presenter.carregarFilmes(MainPresenter.POPULARES);
                btnFiltroPopular.setBackgroundResource(R.drawable.shape_filtro);
                btnFiltroAvalicao.setBackgroundResource(R.drawable.shape_transparent);
                btnFiltroFavoritos.setBackgroundResource(R.drawable.shape_transparent);
                break;
            case R.id.btn_filtro_avaliacao: //Filtra por Melhor Avaliação
                presenter.carregarFilmes(MainPresenter.AVALIACAO);
                btnFiltroPopular.setBackgroundResource(R.drawable.shape_transparent);
                btnFiltroAvalicao.setBackgroundResource(R.drawable.shape_filtro);
                btnFiltroFavoritos.setBackgroundResource(R.drawable.shape_transparent);
                break;
            case R.id.btn_filtro_favoritos: //Filtra por Favoritos
                presenter.carregarFavoritos(MainPresenter.FAVORITOS);
                btnFiltroPopular.setBackgroundResource(R.drawable.shape_transparent);
                btnFiltroAvalicao.setBackgroundResource(R.drawable.shape_transparent);
                btnFiltroFavoritos.setBackgroundResource(R.drawable.shape_filtro);
                break;

        }
    }
}
