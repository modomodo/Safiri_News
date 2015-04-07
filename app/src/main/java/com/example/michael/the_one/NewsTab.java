package com.example.michael.the_one;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


/**
 * Created by Michael on 01/04/2015.
 */
public class NewsTab extends Fragment {

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout =inflater.inflate(R.layout.history_tab,container,false);

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        StringRequest request = new StringRequest(Request.Method.GET, "", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {


            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        return layout; //Return the layout for the fragment
    }
}
