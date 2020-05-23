package com.chinmay.chinmaymoviesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

@SuppressWarnings("deprecation")
public class MainActivity extends AppCompatActivity {
    private GridView gridView;
    private final ArrayList<Films> films = new ArrayList<>();
    private String sort_type;
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences("popular_movies", MODE_PRIVATE);
        sort_type = sharedPreferences.getString("sort_type", "popular");
        gridView = findViewById(R.id.grid_view);

        FetchMovies fetchfilms = new FetchMovies();
        fetchfilms.execute(sort_type);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, Detai_lActivity.class);
                intent.putExtra("film_name", "pos");
                intent.putExtra("film_imgid", "imgid");
                intent.putExtra("film_overview", "data");
                intent.putExtra("film_popularity", "data");
                intent.putExtra("film_release_date", "datevdv");
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.action_sort_settings) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            final SharedPreferences.Editor editor = sharedPreferences.edit();
            int selected = 0;
            sort_type = sharedPreferences.getString("sort_type", "popular");
            if (sort_type.equals("popular"))
                //noinspection ConstantConditions
                selected = 0;
            else if (sort_type.equals("top_rated"))
                selected = 1;
            builder.setTitle("Select sorting type");
            builder.setSingleChoiceItems(R.array.sort_types, selected,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (which == 0)
                                editor.putString("sort_type", "popular");
                            else if (which == 1)
                                editor.putString("sort_type", "top_rated");
                        }
                    });
            builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    editor.apply();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                }
            });
            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @SuppressWarnings("deprecation")
                @Override
                public void onDismiss(DialogInterface dialog) {
                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                    startActivity(intent);
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("StaticFieldLeak")
    class FetchMovies extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String moviesJsonStr;
            String LOG_TAG = "Main Activity";
            try {
                String base_url = "https://api.themoviedb.org/3/movie/";
                String api_key = "6b3748adf9061715d3c0eed091dbf190";
                URL url = new URL(base_url + params[0] + "?api_key=" + api_key);
                Log.d(LOG_TAG, "URL: " + url.toString());

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuilder buffer = new StringBuilder();
                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    //noinspection StringConcatenationInsideStringBufferAppend
                    buffer.append(line + "\n");
                }
                if (buffer.length() == 0) {
                    return null;
                }
                moviesJsonStr = buffer.toString();
                Log.d(LOG_TAG, "JSON Parsed: " + moviesJsonStr);

                JSONObject main = new JSONObject(moviesJsonStr);
                JSONArray arr = main.getJSONArray("results");
                JSONObject movie;
                for (int i = 0; i < arr.length(); i++) {
                    movie = arr.getJSONObject(i);
                    films.add(new Films(movie.getString("original_title"), movie.getString("overview"), movie.getString("vote_average"), movie.getString("poster_path"), movie.getString("release_date")));

                }


            } catch (Exception e) {
                Log.e(LOG_TAG, "Error", e);

            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            FilmAdapter filmAdapter = new FilmAdapter(MainActivity.this, films);
            gridView.setAdapter(filmAdapter);

        }

    }
}
