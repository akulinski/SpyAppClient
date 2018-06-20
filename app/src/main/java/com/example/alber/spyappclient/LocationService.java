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
    private Executor executor;
    private GPSTracker mGPS;

    LocationService(Context context) {
        super(LocationService.class.getSimpleName());
        this.executor= Executors.newFixedThreadPool(20);
        this.mGPS= new GPSTracker(this);

    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }

    protected void getLoc() {
        Log.d("while","running");
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append(mGPS.getLongitude());
        stringBuilder.append(mGPS.getLatitude());
        Log.d("loc",stringBuilder.toString());
    }

    public void onStart(Intent intent, int startId) {
        // TODO Auto-generated method stub
        super.onStart(intent, startId);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    getLoc();

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).run();
      //  this.stopSelf();
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }



}
