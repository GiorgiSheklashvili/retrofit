package ge.gita.restexample.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ge.gita.restexample.dataprovider.Constants;
import ge.gita.restexample.R;
import ge.gita.restexample.dataprovider.model.Movie;
import ge.gita.restexample.dataprovider.model.MovieResponse;
import ge.gita.restexample.retrofit.ApiClient;
import ge.gita.restexample.retrofit.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    //https://developers.themoviedb.org/3/getting-started
    //https://square.github.io/retrofit/
    //https://futurestud.io/tutorials/retrofit-getting-started-and-android-client

    private TextView resultTextView,textView;
    private EditText searchEditText;
    private ImageView imageView;
    private ProgressDialog pd;
    int counter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=(TextView)findViewById(R.id.textview);
        resultTextView = (TextView) findViewById(R.id.tv_result);
        searchEditText = (EditText) findViewById(R.id.et_search);
        imageView=(ImageView)findViewById(R.id.imageView);
        counter=0;
        textView.setText(String.valueOf(counter));
    }

    public void onGetDataClick(View view) {
        int searh = Integer.parseInt(searchEditText.getText().toString().trim());
        if (TextUtils.isEmpty(searchEditText.getText().toString().trim())) {
            searchEditText.setError("Enter Search Text");
            return;
        }

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Movie> call=apiService.getSearchedDetails(searh,Constants.API_KEY);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, final Response<Movie> response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(response.body()!=null){
                            Movie movie=response.body();
                            resultTextView.setText(movie.getOriginalTitle()+"");
                            Picasso.with(MainActivity.this).load("http://image.tmdb.org/t/p/w500/"+movie.getPosterPath()).into(imageView);
                        }
                    }
                });
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Log.e("GITA-ANDROID", t.getMessage());
            }
        });
    }

    public void down(View view) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<MovieResponse> call=apiService.getTopMovies(Constants.API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, final Response<MovieResponse> response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(response.body()!=null){
                            MovieResponse movieResponse=response.body();
                            Picasso.with(MainActivity.this).load("http://image.tmdb.org/t/p/w500/"+movieResponse.getMovieList().get(--counter).getPosterPath()).into(imageView);
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

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<MovieResponse> call=apiService.getTopMovies(Constants.API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, final Response<MovieResponse> response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(response.body()!=null){
                            MovieResponse movieResponse=response.body();
                            Picasso.with(MainActivity.this).load("http://image.tmdb.org/t/p/w500/"+movieResponse.getMovieList().get(++counter).getPosterPath()).into(imageView);
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
}