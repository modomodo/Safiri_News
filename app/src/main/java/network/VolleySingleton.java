package network;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.example.michael.the_one.MyApplication;

/**
 * Created by Michael on 07/04/2015.
 */
public class VolleySingleton {

    private static VolleySingleton sInstance=null; //Reference to class object VolleySingleton
    private ImageLoader mImageLoader;
    private RequestQueue mRequestQueue; //Instance variable of type requestqueue

    private VolleySingleton(){

        //Initialise requestqueue using the context of the app which is handled by custom class MyApplication
        mRequestQueue = new Volley().newRequestQueue(MyApplication.getAppContext());
        mImageLoader = new ImageLoader(mRequestQueue,new ImageLoader.ImageCache() {

            //Use LRUCache which has properties of a string and bitmap, and the memory allocated as the maxmemory of the JVM in Android. This is automatically calculated from the method
            private LruCache<String, Bitmap> cache=new LruCache<>((int) ((Runtime.getRuntime().maxMemory()/1024)/8));

            //Method to retrieve the image in cache
            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url); //Return the entry for the cache, the url of the image
            }

            //Method to store the image inside the cache
            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
        });
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

    //Method to return the instance of Image Loader so that other activities and fragments can have access
    public ImageLoader getImageLoader(){
        return mImageLoader;
    }


}
