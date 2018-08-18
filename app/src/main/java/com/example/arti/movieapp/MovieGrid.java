package com.example.arti.movieapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MovieGrid extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Movie>> {

    private static final int EARTHQUAKE_LOADER_ID = 1;
    private String URL_request;
    ImageAdapter imageAdapter;
    GridView gridView;
    DisplayMetrics metrics;
    TextView mEmptyStateTextView;
    private ProgressBar progressBar;
    private boolean isConnected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_grid);

        ConnectivityManager connectivityManager=(ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        isConnected=networkInfo!=null&&networkInfo.isConnected();
        mEmptyStateTextView=(TextView)findViewById(R.id.emptyView);
        progressBar=(ProgressBar)findViewById(R.id.circularProgressBar);
        if(isConnected) {
            metrics = this.getResources().getDisplayMetrics();
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                URL_request = extras.getString("url");
            }
            Log.v("BookListActivity", URL_request + "&");
            getSupportLoaderManager().initLoader(EARTHQUAKE_LOADER_ID, null, this);
        }
        else
        {
            progressBar.setVisibility(View.INVISIBLE);
            mEmptyStateTextView.setText(R.string.noInternet);
        }
    }

    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int id, Bundle args) {
        return new MovieLoader(this,URL_request);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Movie>> loader, final ArrayList<Movie> data) {
        progressBar.setVisibility(View.INVISIBLE);
        if(isConnected&&data.size()==0) {
            // Set empty state text to display "No earthquakes found."
            mEmptyStateTextView.setText(R.string.noMovie);
        }
        else if(!isConnected)
        {
            mEmptyStateTextView.setText(R.string.noInternet);
        }
        if(!data.isEmpty()&&data!=null) {
            Log.v("MovieGrid", "onLoadFinish" + data.size());
            imageAdapter = new ImageAdapter(this, data, metrics);
            Log.v("MovieGrid", "onLoadFinish" + data.size());
            gridView = (GridView) findViewById(R.id.gridView);
            gridView.setAdapter(imageAdapter);

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(getApplicationContext(), MovieDetail.class);
                    intent.putExtra("title", data.get(i).getMovieTitle());
                    intent.putExtra("releaseDate", data.get(i).getReleaseDate());
                    intent.putExtra("path", data.get(i).getPosterPath());
                    intent.putExtra("overview", data.get(i).getOverView());
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Movie>> loader)
    {
        gridView.setAdapter(null);
    }
}
