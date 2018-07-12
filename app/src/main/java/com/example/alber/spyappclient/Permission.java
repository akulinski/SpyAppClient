package com.example.alber.spyappclient;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;


public class Permission {
    private ArrayList<String> permissions;
    private Activity activity;
    private Context context;

    public Permission(Activity activity, Context context) {
        this.activity = activity;
        this.context = context;
        permissions = new ArrayList<String>() {{
            add(android.Manifest.permission.ACCESS_FINE_LOCATION);
            add(android.Manifest.permission.READ_EXTERNAL_STORAGE);
            add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
            add(android.Manifest.permission.CAMERA);
            add(android.Manifest.permission.RECORD_AUDIO);
        }};
    }

    public void request() {
        ActivityCompat.requestPermissions(activity, permissions.toArray(new String[permissions.size()]), 1);
    }

    public boolean checkPermissions() {
        int location = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);
        int read = ContextCompat.checkSelfPermission(context,Manifest.permission.READ_EXTERNAL_STORAGE);
        int write = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int camera = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA);
        int audio = ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO);

        return location == PackageManager.PERMISSION_GRANTED &&
                read == PackageManager.PERMISSION_GRANTED &&
                write == PackageManager.PERMISSION_GRANTED &&
                camera == PackageManager.PERMISSION_GRANTED &&
                audio == PackageManager.PERMISSION_GRANTED;
    }

    public boolean checkPhotoPermission() {
        int camera = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA);
        return camera == PackageManager.PERMISSION_GRANTED;
    }

    public boolean checkLocationPermission() {
        int location = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);
        return location == PackageManager.PERMISSION_GRANTED;
    }
}

