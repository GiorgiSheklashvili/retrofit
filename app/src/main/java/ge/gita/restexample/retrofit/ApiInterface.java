package ge.gita.restexample.retrofit;

import ge.gita.restexample.dataprovider.model.MovieResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by irakli.managadze on 8/8/2016
 */
public interface ApiInterface {

    @GET("search/movie")
    Call<MovieResponse> getSearchedMovies(@Query("api_key") String apiKey, @Query("query") String search);
}