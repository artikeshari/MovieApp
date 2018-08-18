package com.example.arti.movieapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by bandriya on 06-Oct-17.
 */
public class QueryUtils {

    public static final String LOG_TAG = "MainActivity";


    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    public QueryUtils() {
    }

    /**
     * Query the USGS dataset and return an object to represent a single Movie.
     */

    public static ArrayList<Movie> fetchMovieData(String requestUrl){
        Log.v(LOG_TAG,"fetchMovieData");
        URL url=createUrl(requestUrl);
        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse=null;
        try{
            Log.v("jsonrequest",requestUrl);
            jsonResponse=makeHttpRequest(url);
        }
        catch (IOException e)
        {
            Log.e(LOG_TAG,"Error closing input stream",e);
        }
        ArrayList<Movie> Movie = extractMoviesFromJson(jsonResponse);
        return Movie;
    }
    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException{
        String jsonResponse="";
        if(url==null)
        {
            return jsonResponse;
        }
        HttpURLConnection urlConnection=null;
        InputStream inputStream=null;
        try
        {
            Log.v(LOG_TAG,"Exception in http request");
            urlConnection = (HttpURLConnection) url.openConnection();
            Log.v(LOG_TAG,"Exception in http request");
            urlConnection.setRequestMethod("GET");
            Log.v(LOG_TAG,"Exception in http request");
            urlConnection.setReadTimeout(10000);
            Log.v(LOG_TAG,"Exception in http request");
            urlConnection.setConnectTimeout(15000);
            Log.v(LOG_TAG,"Exception in http request");
            urlConnection.connect();
            Log.v(LOG_TAG,"Exception in http request");
            if (urlConnection.getResponseCode() == 200)
            {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
            Log.v(LOG_TAG,"Exception in http request");
        }
        catch(IOException e)
        {
            Log.e(LOG_TAG,"Exception in http request",e);
        }
        finally
        {
            if (urlConnection != null) {

                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }

        return jsonResponse;
    }
    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException
    {
        StringBuilder output=new StringBuilder();
        if(inputStream!=null)
        {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader reader=new BufferedReader(inputStreamReader);
            String line=reader.readLine();
            while (line!=null){
                output.append(line);
                line=reader.readLine();
            }

        }
        return output.toString();
    }
    /**
     * Returns new URL object from the given string URL.
     */
    public static URL createUrl(String stringUrl){
        URL url=null;
        try
        {
            url=new URL(stringUrl);
        }
        catch (MalformedURLException exception)
        {
            Log.e(LOG_TAG, "Error with creating URL", exception);
        }
        return url;
    }
    /**
     * Return a list of {@link Movie} objects that has been built up from
     * parsing a JSON response.
     */

    public static ArrayList<Movie> extractMoviesFromJson(String json_response) {

        // Create an empty ArrayList that we can start adding Movies to
        ArrayList<Movie> Movies = new ArrayList<>();
        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
            // build up a list of Movie objects with the corresponding data.
            JSONObject jsonObject=new JSONObject(json_response);

            JSONArray results=jsonObject.getJSONArray("results");
            for(int i=0;i<results.length();i++) {
                JSONObject arrayObject = results.getJSONObject(i);
                String title = arrayObject.getString("title");
                String posterPath = arrayObject.getString("poster_path");
                String overview = arrayObject.getString("overview");
                String releaseDate = arrayObject.getString("release_date");
                Movies.add(new Movie(title, releaseDate, overview, posterPath));
            }
        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the Movie JSON results", e);
        }
        // Return the list of Movies
        return Movies;
    }
}
