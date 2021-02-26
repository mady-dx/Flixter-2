package com.example.flixter.adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Movie;
import android.os.Parcelable;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.target.Target;
import com.example.flixter.DetailActivity;
import com.example.flixter.MainActivity;
import com.example.flixter.R;
import com.example.flixter.models.Movies;
import com.google.android.material.internal.ParcelableSparseArray;

import org.parceler.Parcels;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    Context context;
    List<Movies> movies;

    public MovieAdapter (Context context, List<Movies> movies){
        this.context = context;
        this.movies = movies;
    }

    //involves inflating a layout XML and returning the holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter", "onCreateViewHolder");
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(movieView);
    }

    //involves populating data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("MovieAdapter", "onBindViewHolder" + position);
        //get movie at passed position
        Movies movie = movies.get(position);
        //bind the movie to the data into VH
        holder.bind(movie);
    }

    //return total count of items in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        RelativeLayout igMovie;
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            igMovie = itemView.findViewById(R.id.igMovie);
        }

        public void bind(Movies movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            String imageURL;
            //if phone in landscape load backdrop
            if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                imageURL = movie.getBackdropPath();
            }
            else{
                imageURL = movie.getPosterPath();
            }
            //else poster image time
            int radius = 30;
            Glide.with(context).load(imageURL).transform(new FitCenter(), new RoundedCorners(radius))
                    .into(ivPoster);
            //register click on whole container
            //action on click to navigate to new activity
            igMovie.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    //Toast.makeText(context, movie.getTitle(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("movie", Parcels.wrap(movie));

                    Intent i = new Intent(context, DetailActivity.class);
                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation
                            ((Activity) context, tvTitle, ViewCompat.getTransitionName(tvTitle));

                    context.startActivity(intent,options.toBundle());
                }
            });

        }
    }

}
