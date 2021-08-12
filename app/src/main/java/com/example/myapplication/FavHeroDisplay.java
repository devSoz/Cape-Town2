package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SearchView;

import com.example.myapplication.Database.interfaceDb;
import com.example.myapplication.Models.favHero;

import java.util.List;

public class FavHeroDisplay extends Fragment {

    private CreateDb createDb;
    private interfaceDb dao;
    List<favHero> favHeroList;
    RecyclerView recyclerView;
    Context context;
    AdapterFavHero adapterFavHero;
    View view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.fav_main, container, false);
        //setContentView(R.layout.fav_main);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_fav);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        context = view.getContext();

        adapterFavHero = new AdapterFavHero(R.layout.fav_hero, view.getContext());
        recyclerView.setAdapter(adapterFavHero);

        favDisplay();
        return view;
    }

    public void favDisplay()
    {
        //favDoge myDataList=new favDoge(imageId, imageUrl);
        createDb = CreateDb.getInstance(context);
        dao = createDb.Dao();
        favHeroList =  dao.getFavHero();
        adapterFavHero.addAll(favHeroList);
        SearchView searchView = (SearchView) view.findViewById(R.id.searchFavView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                adapterFavHero.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapterFavHero.getFilter().filter(newText);
                return false;
            }

        });
    }



}