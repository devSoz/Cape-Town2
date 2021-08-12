package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Database.interfaceDb;
import com.example.myapplication.Models.ModelHero;
import com.example.myapplication.Models.favHero;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;

public class AdapterFavHero extends RecyclerView.Adapter<AdapterFavHero.favHeroViewHolder> implements Filterable {

    private List<favHero> faveHeroList= new ArrayList<>();
    private List<favHero> faveHeroListFull= new ArrayList<>();
    private Integer rowLayout;
    Context context;
    private CreateDb createDb;
    private interfaceDb dao;
    private String filterPattern="";

    public AdapterFavHero(Integer rowLayout, Context context)
    {
       // this.faveHeroList = faveHeroList;
        this.rowLayout = rowLayout;
        this.context = context;
    }
    public static class favHeroViewHolder extends RecyclerView.ViewHolder
    {
        LinearLayout layoutFavHero;
        TextView id;
        TextView imageId;
        ImageView imageViewFav;
        ImageButton imgBtnSend;

        public favHeroViewHolder(View view)
        {
            super(view);
            imageViewFav = (ImageView) view.findViewById(R.id.imageViewFav);
            layoutFavHero = (LinearLayout) view.findViewById(R.id.layoutFavHero);
            imgBtnSend = (ImageButton) view.findViewById(R.id.imgBtnShare);
        }
    }

    @Override
    public AdapterFavHero.favHeroViewHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        //View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        View view = inflater.inflate(R.layout.fav_hero, parent, false);
        return new favHeroViewHolder(view);
    }


    @Override
    public void onBindViewHolder(favHeroViewHolder holder, final int position) {

        Picasso.get()
                .load(faveHeroList.get(position).getUrl())
                .placeholder(R.color.white)
                .into(holder.imageViewFav);


        holder.imgBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) holder.imageViewFav.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                String heroDetails=new String("");
                Integer i = holder.getAdapterPosition();
                heroDetails="Name :" + faveHeroList.get(i).getName() ;
                shareImageandText(bitmap,heroDetails);
            }
        });

    }
    @Override
    public int getItemCount () {
        return faveHeroList.size();
    }

    @Override
    public Filter getFilter() {
        return searchHeroFilter;
    }
    private Filter searchHeroFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<favHero> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(faveHeroListFull);
            } else {
                filterPattern = constraint.toString().toLowerCase().trim();
                for (favHero item : faveHeroList) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                    if (String.valueOf(item.getId()).contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            faveHeroList.clear();
            faveHeroList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };


    public void addAll(List<favHero> tempResults) {

        for (favHero result : tempResults) {
            faveHeroListFull.add(result);
            faveHeroList.add(result);

        }
        notifyDataSetChanged();
    }


    private void shareImageandText(Bitmap bitmap, String heroName) {
        Uri uri = getmageToShare(bitmap);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.putExtra(Intent.EXTRA_TEXT, heroName);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Share");
        intent.setType("image/*");

        context.startActivity(Intent.createChooser(intent, "Share Via"));
    }

    private Uri getmageToShare(Bitmap bitmap) {
        File imagefolder = new File(context.getCacheDir(), "images");
        Uri uri = null;
        try {
            imagefolder.mkdirs();
            File file = new File(imagefolder, "shared_image.png");
            FileOutputStream outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, outputStream);
            outputStream.flush();
            outputStream.close();
            uri = FileProvider.getUriForFile(context, "com.example.myapplication", file);
        } catch (Exception e) {

            Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return uri;
    }
}
