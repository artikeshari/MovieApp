package com.example.arti.movieapp;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by bandriya on 08-Oct-17.
 */
public class MovieLoader extends AsyncTaskLoader<ArrayList<Movie>>{
   
        /** Tag for log messages */
        private static final String LOG_TAG = MovieLoader.class.getName();

        String murl;
        public MovieLoader(Context context, String url) {
            super(context);
            murl=url;
        }

        @Override
        public ArrayList<Movie> loadInBackground() {
            Log.v(LOG_TAG,"loadInBackGround");
            if(murl==null)
                return null;
            ArrayList<Movie> arrayList=QueryUtils.fetchMovieData(murl);
            return arrayList;
        }

        @Override
        protected void onStartLoading() {
            Log.v(LOG_TAG,"startLoading");
            forceLoad();
        }
    }

