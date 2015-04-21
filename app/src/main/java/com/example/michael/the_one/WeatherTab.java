package com.example.michael.the_one;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class WeatherTab extends Fragment {

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_weather,container,false);

        //Check out how to implement rounded view with Image at top of card
        //CardView cardView = (CardView) getActivity().findViewById(R.id.card_view);
        //cardView.setPreventCornerOverlap(false); //it is very important to get image at top properly
        return v;
    }

}
