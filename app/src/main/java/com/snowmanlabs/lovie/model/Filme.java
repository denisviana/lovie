package com.snowmanlabs.lovie.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by denisvcosta on 08/04/2017.
 */

public class Filme extends RealmObject implements Serializable{

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @PrimaryKey
    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String Titulo;

    @SerializedName("release_date")
    private String DataLancamento;

    @SerializedName("poster_path")
    private String PosterPath;

    @SerializedName("backdrop_path")
    private String BackDropPath;

    @SerializedName("vote_average")
    private String Avaliacao;

    @SerializedName("overview")
    private String Overview;

    private boolean isFavorite = false;

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public Filme(){}

    public Filme(String titulo, String dataLancamento, String posterPath, String backDropPath, String avaliacao, String overview, int id) {
        this.Titulo = titulo;
        this.DataLancamento = dataLancamento;
        this.PosterPath = posterPath;
        this.BackDropPath = backDropPath;
        this.Avaliacao = avaliacao;
        this.Overview = overview;
        this.id = id;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public String getDataLancamento() {
        return DataLancamento;
    }

    public void setDataLancamento(String dataLancamento) {
        DataLancamento = dataLancamento;
    }

    public String getPosterPath() {
        return PosterPath;
    }

    public void setPosterPath(String posterPath) {
        PosterPath = posterPath;
    }

    public String getBackDropPath() {
        return BackDropPath;
    }

    public void setBackDropPath(String backDropPath) {
        BackDropPath = backDropPath;
    }

    public String getAvaliacao() {
        return Avaliacao;
    }

    public void setAvaliacao(String avaliacao) {
        Avaliacao = avaliacao;
    }

    public String getOverview() {
        return Overview;
    }

    public void setOverview(String overview) {
        Overview = overview;
    }
}
