package ge.gita.restexample.ui;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ge.gita.restexample.R;
import ge.gita.restexample.dataprovider.Constants;
import ge.gita.restexample.dataprovider.model.Movie;
import ge.gita.restexample.dataprovider.model.MovieResponse;
import ge.gita.restexample.retrofit.ApiClient;
import ge.gita.restexample.retrofit.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity extends AppCompatActivity {
    ImageView imageView;
    TextView textviewforname,textviewfordate,textviewforoverview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        imageView=(ImageView)findViewById(R.id.imageView2);
        textviewforname=(TextView) findViewById(R.id.textviewforname);
        textviewfordate=(TextView) findViewById(R.id.textviewfordate);
        textviewforoverview=(TextView) findViewById(R.id.textviewforoverview);
        int id = getIntent().getIntExtra("id",0);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Movie> call=apiService.getSearchedDetails(id,Constants.API_KEY);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, final Response<Movie> response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(response.body()!=null){
                            Movie movie=response.body();
                            Picasso.with(DetailsActivity.this).load("http://image.tmdb.org/t/p/w500/"+movie.getPosterPath()).into(imageView);
                            textviewforname.setText(movie.getOriginalTitle());
                            textviewfordate.setText(movie.getReleaseDate().toString());
                            textviewforoverview.setText(movie.getOverview());
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


}
