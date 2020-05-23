package com.chinmay.chinmaymoviesapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

class FilmAdapter extends ArrayAdapter<Films> {

    public FilmAdapter(@NonNull Context context, ArrayList<Films> movies) {
        super(context, 0, movies);
    }

    @SuppressWarnings("deprecation")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitemView = convertView;
        if (listitemView == null) {
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.films_item, parent, false);

        }


        final Films films = getItem(position);
        ImageView img = listitemView.findViewById(R.id.image_view);
        TextView title = listitemView.findViewById(R.id.text_view);
        assert films != null;
        title.setText(films.getFilm_name());
        String imgurl = "http://image.tmdb.org/t/p/w185"+films.getFilm_imgid();
        Picasso.get().load(imgurl).into(img);
        listitemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),Detai_lActivity.class);
                intent.putExtra("film_name", films.getFilm_name());
                intent.putExtra("film_imgid", films.getFilm_imgid());
                intent.putExtra("film_overview", films.getFilm_Overview());
                intent.putExtra("film_popularity", films.getFilm_Popularity());
                intent.putExtra("film_release_date", films.getFilm_publishDate());
                getContext().startActivity(intent);
            }
        });


        return listitemView;
    }
}
