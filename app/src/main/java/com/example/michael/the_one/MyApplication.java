package com.example.michael.the_one;

import android.app.Application;
import android.content.Context;

/**
 * Created by Michael on 07/04/2015.
 * Used to get the context for the app as a whole for the use of the Volley as a Singleton pattern
 */
public class MyApplication extends Application {

     public static final String API_KEY_FAROO= "L@vyVTxqbPo4JUJw4fUkcyC-r1w_";
    private static MyApplication sInstance; //Variable of the type myapplication

    @Override
    public void onCreate(){
        super.onCreate();
        sInstance=this; //Variable initialised onCreate() which is called right before anything runs in the app
    }

    //Method used to return the instance
    public static MyApplication getInstance(){
        return sInstance;
    }

    //Method used to return the application context using the instance, returned as the type: Context
    public static Context getAppContext(){
        return sInstance.getApplicationContext();
    }
}
