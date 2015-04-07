package com.example.michael.the_one;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import network.VolleySingleton;


/**
 * Created by Michael on 01/04/2015.
 */
public class NewsTab extends Fragment {

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout =inflater.inflate(R.layout.history_tab,container,false);

        //Text view for the fragment
        final TextView textView = (TextView) getActivity().findViewById(R.id.textView);

        // Instantiate the RequestQueue which will obtain the request for the API from the volleysingleton already running
        RequestQueue requestQueue = VolleySingleton.getsInstance().getmRequestQueue();

        // Request a string response from the provided URL
        final StringRequest request = new StringRequest(Request.Method.GET, "http://php.net", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getActivity(), "RESPONSE "+response, Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {


            @Override
            public void onErrorResponse(VolleyError error) {
                textView.setText("That didn't work!!");
            }
        });

        requestQueue.add(request); //Add the string request to the request queue
        return layout; //Return the layout for the fragment
    }
}
