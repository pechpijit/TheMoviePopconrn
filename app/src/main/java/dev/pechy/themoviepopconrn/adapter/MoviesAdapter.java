package dev.pechy.themoviepopconrn.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import dev.pechy.themoviepopconrn.R;
import dev.pechy.themoviepopconrn.model.Movie;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.VersionViewHolder> {
    private List<Movie> movies;
    private Context context;
    OnItemClickListener clickListener;

    public MoviesAdapter(List<Movie> movies,Context context) {
        this.movies = movies;
        this.context = context;
    }

    @Override
    public VersionViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_movie, viewGroup, false);
        return new VersionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final VersionViewHolder holder, final int position) {
        holder.movieTitle.setText(movies.get(position).getTitle());
        holder.data.setText(movies.get(position).getReleaseDate());
        holder.movieDescription.setText(movies.get(position).getOverview());
        holder.rating.setText(movies.get(position).getVoteAverage().toString());
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class VersionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView moviesLayout;
        TextView movieTitle;
        TextView data;
        TextView movieDescription;
        TextView rating;

        public VersionViewHolder(View itemView) {
            super(itemView);
            moviesLayout = itemView.findViewById(R.id.movies_layout);
            movieTitle = itemView.findViewById(R.id.title);
            data = itemView.findViewById(R.id.subtitle);
            movieDescription = itemView.findViewById(R.id.description);
            rating = itemView.findViewById(R.id.rating);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(v, getPosition());
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);

    }

    public void SetOnItemClickListener(final OnItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }
}