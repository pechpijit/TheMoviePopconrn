package dev.pechy.themoviepopconrn;

import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import dev.pechy.themoviepopconrn.adapter.MoviesAdapter;
import dev.pechy.themoviepopconrn.model.Movie;
import dev.pechy.themoviepopconrn.model.MoviesResponse;
import dev.pechy.themoviepopconrn.retrofit.ApiClient;
import dev.pechy.themoviepopconrn.retrofit.MovieService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private final static String API_KEY = "0fdbafc2cea92cfbfd71898fed0e5499";

    RecyclerView recyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (API_KEY.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please obtain your API KEY first from themoviedb.org", Toast.LENGTH_LONG).show();
            return;
        }

        getData();
    }

    private void getData() {
        MovieService apiService = ApiClient.getClient().create(MovieService.class);
        Call<MoviesResponse> call = apiService.getTopRatedMovies(API_KEY);
        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse>call,@Nullable Response<MoviesResponse> response) {
                if (response != null) {
                    List<Movie> movies = response.body().getResults();
                    initRecycleView(movies);
                }
            }

            @Override
            public void onFailure(Call<MoviesResponse>call, Throwable t) {
                Log.e(TAG, t.toString());
                onRefreshCompleted();
            }
        });
    }

    private void initRecycleView(List<Movie> movies) {
        mSwipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        recyclerView = findViewById(R.id.scrollableview);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        MoviesAdapter adapter = new MoviesAdapter(movies, MainActivity.this);
        recyclerView.setAdapter(adapter);

        adapter.SetOnItemClickListener(new MoviesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });
        onRefreshCompleted();
    }

    private void onRefreshCompleted() {
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
