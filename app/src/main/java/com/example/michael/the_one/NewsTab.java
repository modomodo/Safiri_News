package com.example.michael.the_one;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import Adapters.NewsRecyclerAdapter;
import Interface.Keys;
import logging.L;
import network.VolleySingleton;

/**
 * Created by Michael on 01/04/2015.
 */

public class NewsTab extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    public static final String URL_FAROO = "http://www.faroo.com/api?start=1&length=10&l=en&src=news&kwic=true&i=true&f=json";
    RequestQueue requestQueue = VolleySingleton.getsInstance().getmRequestQueue(); // Used to hold the HTTP requests for the API that are sent
    RecyclerView recyclerView; //Instance of recycler view where all the news objects will displayed in
    SwipeRefreshLayout mSwipeRefreshLayout;
    SharedPreferences sharedPreferences; //Storage area for app
    SharedPreferences.Editor editor; //editor for shared preferences
    String query;

    private ArrayList<News> listNews = new ArrayList<>(); //ArrayList to hold all news objects news handling all variables and the getter + Setters as an array list
    private NewsRecyclerAdapter newsRecyclerAdapter; //Adapter for handling inserting news items into the views

    //Utility method that returns the JSON result URL adding the API Key and country recognised
    //** Remember to add support for countryname
    public String getRequestUrl(){
        return URL_FAROO + "&key=" + MyApplication.API_KEY_FAROO + "&q=" + query ;
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout =inflater.inflate(R.layout.news_tab,container,false);

        //Retrieve sharedPref value to use in parsing
        sharedPreferences = getActivity().getSharedPreferences("Safiri", getActivity().MODE_PRIVATE);

        mSwipeRefreshLayout = (SwipeRefreshLayout) layout.findViewById(R.id.swipeNews);
        recyclerView= (RecyclerView) layout.findViewById(R.id.newsList);

        // allows for optimizations if all item views are of the same size
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        newsRecyclerAdapter = new NewsRecyclerAdapter(getActivity());
        recyclerView.setAdapter(newsRecyclerAdapter);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        // Configure the refreshing color
        mSwipeRefreshLayout.setColorSchemeColors(R.color.colorPrimary);

        sendJsonRequest();
        return layout; //Return the layout for the fragment
    }

    //Function to send the JSON Request
    public void sendJsonRequest() {
        query = sharedPreferences.getString("query", "Kenya");
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                getRequestUrl(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listNews = parseJsonResponse(response); //Have instance of ArrayList of JsonResponse
                        //Set the data from instance
                        newsRecyclerAdapter.setNews(listNews);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        L.t(getActivity(), "ERROR " + error.toString() + "\n Please swipe down to try again");
                    }
                });

        // Instantiate the RequestQueue which will obtain the request for the API from the volleysingleton already running
        requestQueue.add(request); //Add the string request to the request queue
    }

    private ArrayList <News> parseJsonResponse(JSONObject Response){
       ArrayList <News> listNews = new ArrayList<>(); //
        if(Response !=null && Response.length() > 0) {
            try {
                JSONArray arrayNews = Response.getJSONArray(Keys.KEY_RESULTS); //Array for the news data pulled

                for (int i = 0; i < arrayNews.length(); i++) {

                    JSONObject currentNews = arrayNews.getJSONObject(i);
                    String title = currentNews.getString(Keys.KEY_TITLE);    //Title of the article
                    String url = currentNews.getString(Keys.KEY_URL); //URL of the news article
                    String iurl = currentNews.getString(Keys.KEY_IURL);  //URL of the image of the article
                    String domain = currentNews.getString(Keys.KEY_DOMAIN); //Domain of the news article
                    long timeStamp = currentNews.getLong(Keys.KEY_DATE);  //Java Script timeStamp of time passed from 1970 in milliseconds

                    //conversion of UTC timestamp to actual date
                    Date date = new Date(timeStamp * 1000);

                    News news = new News(); //Populate the news object representing an news article
                    news.setTitle(title);
                    news.setUrlNews(url);
                    news.setUrlImg(iurl);
                    news.setDomain(domain);
                    news.setTimestamp(timeStamp);
                    news.setPublishedDate(date);

                    //Add the news object to the ArrayList
                    listNews.add(news);
                }
                //L.T(getActivity(), listNews.toString());
            } catch (JSONException e) {
                L.t(getActivity(), "ERROR " + e.toString());
            }
        }
        return listNews; //Return the ArrayList obtained
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                sendJsonRequest();
            }
        }, 3000);
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
