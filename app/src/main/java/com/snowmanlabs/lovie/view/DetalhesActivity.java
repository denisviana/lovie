package com.snowmanlabs.lovie.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.snowmanlabs.lovie.R;
import com.snowmanlabs.lovie.model.Filme;
import com.snowmanlabs.lovie.mvp_interface.MVP;
import com.snowmanlabs.lovie.presenter.MainPresenter;
import com.squareup.picasso.Picasso;

/**
 * Created by denisvcosta on 09/04/2017.
 */

public class DetalhesActivity extends AppCompatActivity implements MVP.IDetalhesFilme, View.OnClickListener{

    private ImageView capaFilme;
    private TextView dataLancamento;
    private TextView avaliacao;
    private TextView overView;
    private TextView tituloFilme;
    private Toolbar toolbar;
    private ProgressBar progressBar;
    private RelativeLayout conteudo_filme;
    private ImageView btnFavorito;
    private CollapsingToolbarLayout toolbarCollapse;
    private MVP.IPresenterFilmes presenter;
    private int id;
    private Filme filmeExibido = new Filme();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);
        initViews();

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        presenter = new MainPresenter();
        presenter.setViewDetalhes(this);

        id = getIntent().getExtras().getInt("id"); //Recebe um ID passado via Intent

        btnFavorito.setOnClickListener(this);

        presenter.abrirDetalhesFilme(id); //Passa o ID do filme para poder carregar o arquivo e exibir os detalhes

    }

    @Override
    public Context getContext() {
        return this;
    }

    //Solicita ao Presenter que faça o carregamento dos dados do filme
    @Override
    public void carregamentoCompleto() {
        progressBar.setVisibility(View.GONE);
        conteudo_filme.setVisibility(View.VISIBLE);
    }

    //Animações na tela ao adicionar um filme aos favoritos
    @Override
    public void addFavorito() {
        btnFavorito.setBackgroundResource(R.drawable.heart);
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.custom_toast_container));

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    //Animações na tela ao remover um filme dos favoritos
    @Override
    public void removeFavorito() {
        btnFavorito.setBackgroundResource(R.drawable.heart_outline);
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast2,
                (ViewGroup) findViewById(R.id.custom_toast_container1));

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    @Override
    public void initViews() {
        capaFilme = (ImageView) findViewById(R.id.img_detalhe_filme);
        dataLancamento = (TextView) findViewById(R.id.data_filme);
        avaliacao = (TextView) findViewById(R.id.txt_rate);
        overView = (TextView) findViewById(R.id.txt_overview);
        tituloFilme = (TextView) findViewById(R.id.titulo_filme);
        toolbarCollapse = (CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar_detalhes);
        btnFavorito = (ImageView) findViewById(R.id.btn_favorito);
        conteudo_filme = (RelativeLayout) findViewById(R.id.conteudo_detalhes);
        progressBar = (ProgressBar) findViewById(R.id.detalhes_progressbar);
    }

    //Carrega os detalhes do filme na tela
    @Override
    public void exibirDetalhesFilme(Filme filme) {
        this.filmeExibido = new Filme(filme.getTitulo(),
                filme.getDataLancamento(),filme.getPosterPath(),
                filme.getBackDropPath(),filme.getAvaliacao(),filme.getOverview(),filme.getId());

        Picasso.with(this)
                .load("https://image.tmdb.org/t/p/w500"+filme.getBackDropPath())
                .into(capaFilme);
        tituloFilme.setText(filme.getTitulo());
        dataLancamento.setText(filme.getDataLancamento());
        avaliacao.setText(filme.getAvaliacao()+"/10");
        overView.setText(filme.getOverview());

        if(filme.isFavorite()){
            btnFavorito.setBackgroundResource(R.drawable.heart);
        }else
            btnFavorito.setBackgroundResource(R.drawable.heart_outline);
    }

    @Override
    public void onClick(View v) {
        presenter.addOrRemoveFavorite(filmeExibido);
    }
}
