package com.snowmanlabs.lovie.view;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.snowmanlabs.lovie.R;
import com.snowmanlabs.lovie.model.Filme;
import com.snowmanlabs.lovie.mvp_interface.MVP;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by denisvcosta on 08/04/2017.
 */

public class AdapterGridFilmes extends RecyclerView.Adapter<RecyclerView.ViewHolder>
    implements MVP.IAdapterRecyclerView{


    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private boolean isLoadingAdded = false;
    private MVP.IPresenterFilmes presenter;
    private MVP.IViewFilmes view;

    private final String PATH_POSTER = "https://image.tmdb.org/t/p/w500";
    private List<Filme> filmes = new ArrayList<>();
    private LayoutInflater inflater;

    public AdapterGridFilmes(MVP.IViewFilmes view, MVP.IPresenterFilmes presenter){
        this.presenter = presenter;
        this.view = view;
        this.inflater = LayoutInflater.from(view.getContext());
    }

    @Override
    public List<Filme> getMovies() {
        return filmes;
    }

    @Override
    public void setMovies(List<Filme> movieResults) {
        this.filmes = movieResults;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) { //Verifica se deve exibir no RecyclerView os dados dos filmes ou o Progress
            case ITEM:
                viewHolder = getViewHolder(parent, inflater);
                break;
            case LOADING:
                View viewLoading = inflater.inflate(R.layout.item_filme_progress, parent, false);
                viewHolder = new LoadingViewHolder(viewLoading);
                break;
        }

        return viewHolder;
    }

    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater){
        RecyclerView.ViewHolder holder;

        View viewFilmes = this.inflater.inflate(R.layout.layout_item_filme,parent,false);
        holder = new MyViewHolder(viewFilmes);

        return holder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Filme filme = filmes.get(position);

        switch (getItemViewType(position)){
            case ITEM:
                MyViewHolder holderFilme = (MyViewHolder) holder;
                holderFilme.avaliacao.setText(filme.getAvaliacao()+"/10");
                Picasso.with(this.inflater.getContext())
                        .load(PATH_POSTER+filme.getPosterPath())
                        .into(holderFilme.poster_filme);
                break;
            case LOADING:
                break;
        }

    }


    @Override
    public int getItemViewType(int position) {
        return (position == filmes.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    @Override
    public int getItemCount() {
        return filmes == null ? 0 : filmes.size();
    }


    @Override
    public void add(Filme f) {
        filmes.add(f);
        notifyItemInserted(filmes.size() - 1);
    }

    @Override
    public void addAll(List<Filme> filmes) {
        for (Filme filme : filmes) {
            add(filme);
        }
    }

    @Override
    public void remove(Filme f) {
        int position = filmes.indexOf(f);
        if (position > -1) {
            filmes.remove(position);
            notifyItemRemoved(position);
        }
    }

    @Override
    public void clear() {
        isLoadingAdded = false;
       filmes.clear();
        notifyDataSetChanged();
    }

    @Override
    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    @Override
    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new Filme());
    }

    @Override
    public void removeLoadingFooter() {

        isLoadingAdded = false;

        int position = filmes.size() - 1;
        Filme filme = getItem(position);

        if (filme != null) {
            filmes.remove(position);
            notifyItemRemoved(position);
        }
    }

    @Override
    public Filme getItem(int position) {
        return filmes.get(position);
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView avaliacao;
        ImageView poster_filme;
        FrameLayout item_filme;

        public MyViewHolder(View itemView) {
            super(itemView);
            poster_filme = (ImageView) itemView.findViewById(R.id.poster_filme);
            avaliacao = (TextView) itemView.findViewById(R.id.txt_avalicao);
            item_filme = (FrameLayout) itemView.findViewById(R.id.item_filme);

            item_filme.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(view.getContext(), DetalhesActivity.class);
                    intent.putExtra("id",filmes.get(getLayoutPosition()).getId());
                    view.getContext().startActivity(intent);

                }
            });


        }
    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder{

        public LoadingViewHolder(View itemView) {
            super(itemView);
        }
    }
}
