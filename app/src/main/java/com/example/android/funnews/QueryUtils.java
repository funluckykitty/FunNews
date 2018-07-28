package com.example.android.funnews;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

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
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
//7/22/18 - LSB - Some Utilities to get the data...
public final class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private QueryUtils() {
    }
    public static List<News> fetchNewsData(String requestUrl) {
        //7/17/18 - LSB for testing slowness  Keep this
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "There was a problem making the HTTP request.", e);
        }

        // 7/17/18 - Extract data from JSON and create a list.
        List<News> article = extractFeatureFromJson(jsonResponse);

        return article;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            //7/18/18 - LSB - If Success (200)...
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    //7/22/18 - read all the data from the stream of JSON.
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static List<News> extractFeatureFromJson(String articleJSON) {
        Log.i("http utils", "fetchItemData");

        // 7/25/18 Return Now if the JSON String is empty or null.
        if (TextUtils.isEmpty(articleJSON)) {
            return null;
        }



        // 7/24/18 - Create an Empty Array
        List<News> articles = new ArrayList<>();


        try {

            // 7/24/18 = LSB Create a JSONObject from the JSON response string
            JSONObject baseJson = new JSONObject(articleJSON);
            JSONObject baseJsonResponse = null;
            try {
                baseJsonResponse = baseJson.getJSONObject("response");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JSONArray baseJsonResponseResults = null;
            try {
                baseJsonResponseResults = baseJsonResponse.getJSONArray("results");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < baseJsonResponseResults.length(); i++) {
                //7/24/18 - LSB - Loop thru the array
                JSONObject currentItem = baseJsonResponseResults.getJSONObject(i);
                JSONArray tagsArray = currentItem.optJSONArray("tags");

                JSONObject tagsObject = tagsArray.optJSONObject(0);
                String author;
                Bitmap appImage;
                if (tagsObject!= null){
                    author = "Article by: " + tagsObject.optString("webTitle");
                  //  Log.e("export", "extractFeatureFromJson:" + author);

                    String webImage = tagsObject.optString("bylineImageUrl");
                    appImage =  ((BitmapDrawable) LoadImageFromWebOperations(webImage)).getBitmap();
                }
                else{
                    author = "Author is not credited";
                   // Log.e("export", "extractFeatureFromJson:" + author);
                    appImage = null;
                }

                String sectionName = currentItem.optString("sectionName");
               // Log.e("sectionName", "extractFeatureFromJson:" + sectionName);

                String webTitle = currentItem.optString("webTitle");
                //Log.e("webTitle", "extractFeatureFromJson:" + webTitle);

                String webUrl = currentItem.optString("webUrl");
                //Log.e("webUrl", "extractFeatureFromJson:" + webUrl);

                String webPublicationDate = currentItem.optString("webPublicationDate");
                //Log.e("webPublicationDate", "extractFeatureFromJson:" + webPublicationDate);

                News article = new News(webTitle, sectionName, author, webPublicationDate, webUrl, appImage);
                articles.add(article);
            }

        } catch (JSONException e) {
            Log.e("http utils", "Problem parsing the item JSON results", e);
        }
        return articles;
    }
    //7/27/18 - LSB - Article about how to add an image   https://stackoverflow.com/questions/3118691/android-make-an-image-at-a-url-equal-to-imageviews-image
    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "");
            return d;
        } catch (Exception e) {
            Log.e(LOG_TAG, "Image failed", e);
            return null;
        }

    }

}
