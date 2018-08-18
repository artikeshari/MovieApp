package com.example.arti.movieapp;
import android.content.Context;
import android.provider.ContactsContract;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.LruCache;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/*
 * Created by bandriya on 07-Oct-17.
 */
public class ImageAdapter extends BaseAdapter {

    Context mContext;

    // references to our images
    private ArrayList<Movie> arraylist;
    DisplayMetrics metrics;
    public ImageAdapter(Context context, ArrayList<Movie> arraylist,DisplayMetrics metrics) {
        mContext=context;
        this.arraylist=arraylist;
        this.metrics=metrics;
    }

    @Override
    public int getCount() {
        return arraylist.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        //Picasso picasso = new Picasso.Builder(mContext).memoryCache(new LruCache(maxSize)).build();
        // getResource() is not recognize here so i pass metrics from MovieGrid to here by constructor
        //DisplayMetrics metrics = getResources().getDisplayMetrics();
            ImageView imageView;
            if (view == null) {
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams((int)(metrics.density*180f),(int)(metrics.density*250f)));
                Log.v("height",(int)(metrics.density*250f)+"");
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setPadding(8, 8, 8, 8);
            }
            else
            {
                imageView = (ImageView) view;
            }
            String path = arraylist.get(i).getPosterPath();
            Log.v("ImageAdapter", arraylist.get(i).getPosterPath());
            Picasso.with(mContext).load(path).resize(200,200).placeholder(R.drawable.image_not_avail).into(imageView);
            return imageView;
    }

}