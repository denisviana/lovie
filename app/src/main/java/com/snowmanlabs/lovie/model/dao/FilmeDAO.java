package com.snowmanlabs.lovie.model.dao;

import android.content.Context;

import com.snowmanlabs.lovie.model.Filme;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.exceptions.RealmException;

/**
 * Created by denisvcosta on 10/04/2017.
 */

public class FilmeDAO  {

    private Context context;
    private Realm realm;

    public FilmeDAO(Context context){
        this.context = context;
        Realm.init(context);
        realm = Realm.getDefaultInstance();
    }

    public List<Filme> obterFavoritos(){

        RealmResults<Filme> results = realm.where(Filme.class).findAll();

        List<Filme> filmes = realm.copyFromRealm(results);

        return filmes;
    }

    public Filme buscaFilme(int id){
        return realm.where(Filme.class).equalTo("id",id).findFirst();
    }

    public boolean addFavorito(Filme filme){

        try{
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(filme);
            realm.commitTransaction();
            realm.close();
            return true;
        } catch (RealmException e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean jaExiste(int id){
        try{
            RealmResults<Filme> filmes = realm.where(Filme.class)
                    .equalTo("id",id)
                    .findAll();
            if(filmes.size() > 0) {
                return true;
            }else
                return false;
        } catch (RealmException e){
            e.printStackTrace();
            return false;
        }
    }

    public void removeFavorito(int id){

        final Filme filme = realm.where(Filme.class).equalTo("id",id).findFirstAsync();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                filme.deleteFromRealm();
            }
        });

    }

    public boolean isFavorite(int id){

        try{
            Filme filme = realm.where(Filme.class).equalTo("id",id).findFirst();

            if(filme==null){
                return false;
            }

            if(filme.isFavorite()){
                return true;
            }else
                return false;
        }catch (RealmException e){
            e.printStackTrace();
            return false;
        }
    }
}
