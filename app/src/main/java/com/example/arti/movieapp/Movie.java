package com.example.arti.movieapp;

import android.util.Log;

/**
 * Created by bandriya on 06-Oct-17.
 */
public class Movie
{
    String posterPath;
    String movieTitle,releaseDate,overView;
    public Movie(String movieTitle,String releaseDate,String overView,String posterPath)
    {
        this.movieTitle=movieTitle;
        this.overView=overView;
        this.posterPath=posterPath;
        this.releaseDate=releaseDate;
    }

    public String getPosterPath() {
        Log.v("Movie",posterPath);
        return "https://image.tmdb.org/t/p/original"+posterPath;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public String getOverView() {
        return overView;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
