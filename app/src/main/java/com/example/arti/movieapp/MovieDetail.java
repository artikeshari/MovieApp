package com.example.arti.movieapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.LruCache;
import com.squareup.picasso.Picasso;

public class MovieDetail extends AppCompatActivity {

    private String title,path;
    private String overView,releaseDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Bundle extras=getIntent().getExtras();
        if(extras!=null)
        {
            title=extras.getString("title");
            path=extras.getString("path");
            releaseDate=extras.getString("releaseDate");
            overView=extras.getString("overview");
        }

        ImageView imageView=(ImageView)findViewById(R.id.posterD);
        Picasso.with(this).load(path).placeholder(R.drawable.image_not_avail).into(imageView);
        TextView textView=(TextView)findViewById(R.id.title);
        textView.setText("Title: "+title);
        TextView textView2=(TextView)findViewById(R.id.releaseDate);
        textView2.setText("Release Date: "+releaseDate);
        TextView textView3=(TextView)findViewById(R.id.overview);
        textView3.setText("Overview:\n"+overView);
    }
}
