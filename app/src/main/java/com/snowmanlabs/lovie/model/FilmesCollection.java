package com.snowmanlabs.lovie.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by denisvcosta on 09/04/2017.
 */

public class FilmesCollection {

    @SerializedName("page")
    private Integer page;

    @SerializedName("results")
    public List<Filme> filmes;

    @SerializedName("total_results")
    private Integer totalResults;

    @SerializedName("total_pages")
    private Integer totalPages;

    public FilmesCollection(){}

    public FilmesCollection(List<Filme> filmes){
        this.filmes = filmes;
    }

    public List<Filme> getFilmes() {
        return filmes;
    }

    public void setResults(List<Filme> filmes) {
        this.filmes = filmes;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

}
