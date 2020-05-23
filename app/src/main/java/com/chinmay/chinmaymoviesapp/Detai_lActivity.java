package com.chinmay.chinmaymoviesapp;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.chinmay.chinmaymoviesapp.R;
import com.squareup.picasso.Picasso;

public class Detai_lActivity extends AppCompatActivity {
    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView title_txt = findViewById(R.id.film_name);
        TextView release_date = findViewById(R.id.release_date);
        TextView rating_txt = findViewById(R.id.rating);
        TextView overview_txt = findViewById(R.id.overview);
        ImageView poster_img = findViewById(R.id.film_poster);
        String title_str = getIntent().getStringExtra("film_name");
        String rating_str = getIntent().getStringExtra("film_popularity");
        String overview_str = getIntent().getStringExtra("film_overview");
        String img_str = getIntent().getStringExtra("film_imgid");
        String release_str = getIntent().getStringExtra("film_release_date");
        String imgurl = "http://image.tmdb.org/t/p/w185" + img_str;
        title_txt.setText(title_str);

        String rating = rating_str + "/" + 10;
        release_date.setText(release_str);
        rating_txt.setText(rating);
        overview_txt.setText(overview_str);
        Picasso.get().load(imgurl).into(poster_img);
    }
}
