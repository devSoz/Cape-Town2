package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.transition.Fade;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Database.interfaceDb;
import com.example.myapplication.Models.ModelHero;
import com.example.myapplication.Models.favHero;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterHeroes extends RecyclerView.Adapter<AdapterHeroes.HeroViewHolder> implements Filterable {

    private static List<ModelHero> heroList = new ArrayList<>();
    private Integer rowLayout;
    private CreateDb createDb;
    private interfaceDb dao;
    private String filterPattern="";
    List<favHero> favHeroList;
    List<ModelHero> heroListFull= new ArrayList<>();
    Context context;
    private List<Integer> heroIdList = new ArrayList<>();
    String selected;

    //List<ModelHero> heroList,
    public AdapterHeroes(Integer rowLayout, Context context, String selected)
    {
        this.rowLayout = rowLayout;
        this.context = context;
        this.selected = selected;

        createDb = CreateDb.getInstance(context);
        dao = createDb.Dao();
        favHeroList =  dao.getFavHero();

        for (favHero i : favHeroList) {
            heroIdList.add(i.getHeroId());
        }
    }

    public static class HeroViewHolder extends RecyclerView.ViewHolder
    {
        LinearLayout layoutHero;
        TextView bredFor;
        TextView name, slug, weight, fullname, birthplace, color,firstappearance, work, connection1, connection2;

        ImageView imageView;
        ImageButton ibtnFavHero;
        CardView cardView;
        LinearLayout linearLayoutBack, linearLayoutFront ;

        public HeroViewHolder(View view)
        {
            super(view);

            imageView = (ImageView) view.findViewById(R.id.imageView);
            layoutHero = (LinearLayout) view.findViewById(R.id.layoutHero);
            name = (TextView) view.findViewById(R.id.Name);

            linearLayoutFront = (LinearLayout) view.findViewById(R.id.layoutFront);
            ibtnFavHero = (ImageButton) view.findViewById(R.id.btnFavHero);
          }
    }

    @Override
    public AdapterHeroes.HeroViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new HeroViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HeroViewHolder holder, final int position) {

        try {
            Picasso.get()
                    .load(heroList.get(position).getImages().getSm())
                    .placeholder(R.color.white)
                    .into(holder.imageView);

            holder.imageView.setTransitionName("transition" + position);
            holder.name.setText(heroList.get(position).getName());

            if (heroIdList.contains(heroList.get(position).getId())) {
                holder.ibtnFavHero.setImageResource(R.drawable.fav);
            } else {
                holder.ibtnFavHero.setImageResource(R.drawable.un_fav);
            }

            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Fade fade = new Fade();
                    View decor = ((Activity)context).getWindow().getDecorView();
                    fade.excludeTarget(decor.findViewById(R.id.action_bar_container), true);
                    fade.excludeTarget(android.R.id.statusBarBackground, true);
                    fade.excludeTarget(android.R.id.navigationBarBackground, true);
                    ((Activity)context).getWindow().setEnterTransition(fade);
                    ((Activity)context).getWindow().setExitTransition(fade);

                    Intent intent = new Intent(context, HeroDetail.class);
                    Integer pos = holder.getAdapterPosition();
                    String imageUrl = heroList.get(pos).getImages().getMd();
                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(((Activity)context), holder.imageView , "hero_transition");
                    intent.putExtra("slug","Slug : " + emptyifNull(heroList.get(position).getSlug()));
                    intent.putExtra("name" , "Name : " + heroList.get(pos).getName());
                    intent.putExtra("image" , imageUrl);
                    intent.putExtra("fullName" , "Full Name : " + emptyifNull(heroList.get(position).getBiography().getFullName())
                            + "\nRace : " + emptyifNull(heroList.get(position).getAppearance().getRace())
                            + "(" + emptyifNull(heroList.get(position).getAppearance().getGender()) + ")");

                    intent.putExtra("birthPlace" ,"Birth Place : "+ heroList.get(pos).getBiography().getPlaceOfBirth());
                    intent.putExtra("color" , "Hair Color : " + emptyifNull(heroList.get(position).getAppearance().getHairColor())
                            + "  Eye Color : " + emptyifNull(heroList.get(position).getAppearance().getEyeColor()));
                    intent.putExtra("firstAppearance" , "First Appearance : " +heroList.get(pos).getBiography().getFirstAppearance());
                    intent.putExtra("weight" , "Weight : " + heroList.get(position).getAppearance().getWeight().get(0)
                            + ", Height: " + heroList.get(position).getAppearance().getHeight().get(0));
                    intent.putExtra("occ" , "Occupation : " +heroList.get(pos).getWork().getOccupation());
                    intent.putExtra("aff" , "Group Affiliation : " + emptyifNull(heroList.get(position).getConnections().getGroupAffiliation()));
                    intent.putExtra("relatives" , "Relatives : " + heroList.get(pos).getConnections().getRelatives());
                    context.startActivity(intent, options.toBundle());
                }
            });

            holder.ibtnFavHero.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Integer pos = holder.getAdapterPosition();
                    String imageUrl = heroList.get(pos).getImages().getSm();
                    Integer heroId = heroList.get(pos).getId();
                    String heroname = heroList.get(pos).getName();

                    if (heroIdList.contains(heroId)) {
                        holder.ibtnFavHero.setImageResource(R.drawable.un_fav);
                        deleteData(heroId, imageUrl, heroname);
                        heroIdList.remove(heroId);
                    } else {
                        holder.ibtnFavHero.setImageResource(R.drawable.fav);
                        heroIdList.add(heroId);
                        insertData(heroId, imageUrl, heroname);
                    }
                }
            });
        } catch (Exception e) {
            Log.d("Mmkayy", e.getMessage());
        }
    }


    public void insertData(Integer heroId, String imageUrl, String heroName)
    {
        favHero myDataList = new favHero(heroId, imageUrl, heroName);
        createDb = CreateDb.getInstance(context);
        dao = createDb.Dao();
        dao.insert(myDataList);

        Toast.makeText(context,"Added to favourites!",Toast.LENGTH_LONG).show();
    }

    public void deleteData(Integer heroId, String imageUrl, String heroName)
    {
        createDb = CreateDb.getInstance(context);
        dao = createDb.Dao();
        dao.deleteById(heroId);

        Toast.makeText(context,"Removed from favourites!",Toast.LENGTH_LONG).show();
    }

    public String emptyifNull(String s)
    {
        if (s==null)
            return "";
        else
            return s;
    }

    @Override
    public Filter getFilter() {
        return searchHeroFilter;
    }
    private Filter searchHeroFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ModelHero> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(heroListFull);
            } else {
                filterPattern = constraint.toString().toLowerCase().trim();
                for (ModelHero item : heroList) {
                    String gender = item.getAppearance().getGender();
                   // if ((selected == "a") || ((selected == "f") && (gender == "Female")) || ((selected == "m") && (gender == "Male")))
                    {
                        if (item.getName().toLowerCase().contains(filterPattern)) {
                            filteredList.add(item);
                        }
                        if (String.valueOf(item.getId()).contains(filterPattern)) {
                            filteredList.add(item);
                        }
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            heroList.clear();
            heroList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public void addAll(List<ModelHero> tempResults, String filterStr) {
        heroList.clear();
        heroListFull.clear();

        if (filterStr=="f") filterStr="female"; else filterStr="male";
        for (ModelHero result : tempResults) {

            if ((selected == "a") || ((selected != "a") && (result.getAppearance().getGender().toLowerCase().contains(filterStr))))

            {
                heroListFull.add(result);
                heroList.add(result);
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return heroList.size();
    }
}
