package ge.gita.restexample.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import ge.gita.restexample.dataprovider.Constants;
import ge.gita.restexample.R;
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

    private TextView resultTextView;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultTextView = (TextView) findViewById(R.id.tv_result);
    }

    public void onGetDataClick(View view) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<MovieResponse> call = apiService.getSearchedMovies(Constants.API_KEY, "war");
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, final Response<MovieResponse> response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (response.body() != null) {
                            MovieResponse movieResponse = response.body();
                            resultTextView.setText(movieResponse.getMovieList().size() + "");
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