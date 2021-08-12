package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.transition.Fade;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class HeroDetail extends AppCompatActivity {
    TextView name, slug, weight, fullname, birthplace, color,firstappearance, work, connection1, connection2;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero_detail);
         name = (TextView) findViewById(R.id.hName);
         slug = (TextView) findViewById(R.id.slug);
         weight   = (TextView) findViewById(R.id.weight);
         fullname = (TextView) findViewById(R.id.fullname);
         birthplace = (TextView) findViewById(R.id.birthplace);
         color = (TextView) findViewById(R.id.color);
         firstappearance = (TextView) findViewById(R.id.firstappearance);
         work = (TextView) findViewById(R.id.work);
         connection1 = (TextView) findViewById(R.id.connection1);
         connection2 = (TextView) findViewById(R.id.connection2);
         imageView = (ImageView) findViewById(R.id.himageView);

        Intent intent = getIntent();
        String x = intent.getStringExtra("name");
        String imageUrl = intent.getStringExtra("image");




        Picasso.get()
                .load(imageUrl)
                .placeholder(R.color.white)
                .into(imageView);

        Fade fade = new Fade();
        View decor = getWindow().getDecorView();

        fade.excludeTarget(decor.findViewById(R.id.action_bar_container), true);
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);

        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);

        name.setText(intent.getStringExtra("name"));
        slug.setText(intent.getStringExtra("slug"));
        weight.setText(intent.getStringExtra("weight"));
        fullname.setText(intent.getStringExtra("fullName"));
        color.setText(intent.getStringExtra("color"));
        firstappearance.setText(intent.getStringExtra("firstAppearance"));
        work.setText(intent.getStringExtra("occ"));
        connection1.setText(intent.getStringExtra("aff"));
        connection2.setText(intent.getStringExtra("relatives"));
        //work.setText(intent.getStringExtra("image"));
        birthplace.setText(intent.getStringExtra("birthPlace"));
    }
}