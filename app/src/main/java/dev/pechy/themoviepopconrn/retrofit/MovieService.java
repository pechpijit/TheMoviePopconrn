package dev.pechy.themoviepopconrn.retrofit;

import dev.pechy.themoviepopconrn.model.MoviesResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieService {
        @GET("movie/top_rated")
        Call<MoviesResponse> getTopRatedMovies(@Query("api_key") String apiKey);

        @GET("movie/{id}")
        Call<MoviesResponse> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);
}
