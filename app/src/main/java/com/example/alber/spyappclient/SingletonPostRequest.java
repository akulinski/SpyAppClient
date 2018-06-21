package com.example.alber.spyappclient;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


public class SingletonPostRequest {
    private RequestQueue mRequestQueue;
    private static Context mCtx;
    private static SingletonPostRequest singletonPostRequest;

    private SingletonPostRequest(Context context){
        mCtx=context;
        mRequestQueue=getRequestQueue();
    }

    public static synchronized SingletonPostRequest getInstance(Context context){

        if(singletonPostRequest==null){
            singletonPostRequest=new SingletonPostRequest(context);
        }

        return singletonPostRequest;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req){
        getRequestQueue().add(req);
    }

}