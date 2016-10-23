package ge.gita.restexample.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


import ge.gita.restexample.dataprovider.Constants;
import ge.gita.restexample.R;
import ge.gita.restexample.dataprovider.model.MovieResponse;
import ge.gita.restexample.retrofit.ApiClient;
import ge.gita.restexample.retrofit.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements ItemClickListener {

    //https://developers.themoviedb.org/3/getting-started
    //https://square.github.io/retrofit/
    //https://futurestud.io/tutorials/retrofit-getting-started-and-android-client

    private TextView textView;
    private EditText searchEditText;
    private ImageView imageView;
    RecyclerView recyclerView;
    int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textview);
        searchEditText = (EditText) findViewById(R.id.et_search);
        imageView = (ImageView) findViewById(R.id.imageView);
        counter = 0;
        textView.setText(String.valueOf(counter));
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void onGetDataClick(View view) {
        imageView.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        String searh = searchEditText.getText().toString().trim();
        if (TextUtils.isEmpty(searh)) {
            searchEditText.setError("Enter Search Text");
            return;
        }
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<MovieResponse> call = apiService.getSearchedMovies(Constants.API_KEY, searh);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, final Response<MovieResponse> response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (response.body() != null) {
                            MovieResponse movie = response.body();
                            recyclerView.setAdapter(new MovieAdapter(movie.getMovieList(), MainActivity.this, MainActivity.this));
                        }
                    }
                });
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e("GITA-ANDROID", t.getMessage());
            }
        });
    }

    public void down(View view) {
        recyclerView.setVisibility(View.GONE);
        imageView.setVisibility(View.VISIBLE);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<MovieResponse> call = apiService.getTopMovies(Constants.API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, final Response<MovieResponse> response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (response.body() != null) {
                            MovieResponse movieResponse = response.body();
                            Picasso.with(MainActivity.this).load("http://image.tmdb.org/t/p/w500/" + movieResponse.getMovieList().get(--counter).getPosterPath()).into(imageView);
                            textView.setText(String.valueOf(counter));

                        }
                    }
                });
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e("GITA-ANDROID", t.getMessage());
            }
        });

    }

    public void up(View view) {
        recyclerView.setVisibility(View.GONE);
        imageView.setVisibility(View.VISIBLE);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<MovieResponse> call = apiService.getTopMovies(Constants.API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, final Response<MovieResponse> response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (response.body() != null) {
                            MovieResponse movieResponse = response.body();
                            Picasso.with(MainActivity.this).load("http://image.tmdb.org/t/p/w500/" + movieResponse.getMovieList().get(++counter).getPosterPath()).into(imageView);
                            textView.setText(String.valueOf(counter));
                        }
                    }
                });
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e("GITA-ANDROID", t.getMessage());
            }
        });
    }


    @Override
    public void onItemClicked(int position) {
        Intent i = new Intent(MainActivity.this, DetailsActivity.class);
        i.putExtra("id", position);
        startActivity(i);
    }
}