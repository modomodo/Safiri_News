package network;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.michael.the_one.MyApplication;

/**
 * Created by Michael on 07/04/2015.
 */
public class VolleySingleton {

    private static VolleySingleton sInstance=null; //Reference to class object VolleySingleton
    private RequestQueue mRequestQueue; //Instance variable of type requestqueue
    private VolleySingleton(){

        //Initialise requestqueue using the context of the app which is handled by custom class MyApplication
        mRequestQueue = new Volley().newRequestQueue(MyApplication.getAppContext());
    }
    public static VolleySingleton getsInstance(){
        //Static method that returns instance of class VolleySingleton
        if(sInstance == null){
            sInstance = new VolleySingleton();
        }
        return sInstance;
    }
    //Method that returns the request queue for other classes to use
    public RequestQueue getmRequestQueue(){
        return mRequestQueue;
    }


}
