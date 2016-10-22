package ge.gita.restexample.retrofit;

import android.telecom.CallScreeningService;

import java.util.List;

import ge.gita.restexample.dataprovider.model.Movie;
import ge.gita.restexample.dataprovider.model.MovieResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by irakli.managadze on 8/8/2016
 */
public interface ApiInterface {

    @GET("search/movie")
    Call<MovieResponse> getSearchedMovies(@Query("api_key") String apiKey, @Query("query") String search);

    @GET("movie/{movie_id}")
    Call<Movie> getSearchedDetails(@Path("movie_id") int id, @Query("api_key") String apiKey);
    @GET("movie/top_rated")
    Call<MovieResponse> getTopMovies(@Query("api_key") String apiKey);
}