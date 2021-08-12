package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SearchView;

import com.example.myapplication.API.APIHeroes;
import com.example.myapplication.API.interfaceHeroes;
import com.example.myapplication.Models.ModelHero;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.os.SystemClock.sleep;

public class MainActivity extends Fragment {

    public AdapterHeroes adapterHeroes;
    private ProgressDialog progress;
    View view;
    String selected;
    @Override
        public View onCreateView(@NonNull LayoutInflater inflater,
                ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_main, container, false);

        //progressShow();
        selected = getArguments().getString("option");
        adapterHeroes = new AdapterHeroes(R.layout.list_hero, view.getContext(), selected);
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_hero);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapterHeroes);

        interfaceHeroes heroService = APIHeroes.getClient().create(interfaceHeroes.class);
        Call<List<ModelHero>> call = heroService.getHero();

        call.enqueue(new Callback<List<ModelHero>>() {
            @Override
            public void onResponse(Call<List<ModelHero>> call, Response<List<ModelHero>> response) {
                List<ModelHero> heroList = response.body();
                adapterHeroes.addAll(heroList,selected);
               // progress.hide();
            }

            @Override
            public void onFailure(Call<List<ModelHero>> call, Throwable t) {
                try {
                    throw t;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });

        btnClick();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    }

    public void btnClick()
    {
        SearchView searchView = (SearchView) view.findViewById(R.id.searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapterHeroes.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapterHeroes.getFilter().filter(newText);
                return false;
            }

        });
    }

    public void progressShow()
    {
        progress=new ProgressDialog(view.getContext());
        progress.setMessage("Downloading Music");
        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progress.setIndeterminate(true);
        progress.setProgress(0);
        progress.show();
    }

}
