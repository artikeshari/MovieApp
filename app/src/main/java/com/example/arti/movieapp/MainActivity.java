 package com.example.arti.movieapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

 public class MainActivity extends AppCompatActivity {

     EditText editText;
     String UrlRequest,phrase;
     Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText=(EditText)findViewById(R.id.phrase);

        UrlRequest= "https://api.themoviedb.org/3/search/movie?api_key=9363df7332d29b2abad23b2a208335b6&query=";
        button=(Button)findViewById(R.id.submitButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phrase=editText.getText().toString();
                String[] keywords=phrase.split(" ");
                for(int i=0;i<keywords.length;i++)
                {
                    UrlRequest=UrlRequest+keywords[i]+"+";
                }
                Log.v("HTTP  :  ",UrlRequest);
                Intent intent=new Intent(getApplicationContext(),MovieGrid.class);
                intent.putExtra("url",UrlRequest);
                startActivity(intent);
            }
        });
    }
}
