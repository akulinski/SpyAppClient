package com.example.alber.spyappclient;
import android.util.Log;

import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class PhotoUploader {
    private Cloudinary cloudinary;
    private Map uploadResult;
    private String photoUrl;


    PhotoUploader(){
        cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "sapkapp",
                "api_key", "165862785764238",
                "api_secret", "Rt4-IkkKffonRATFtNU3Lo_iwZI"));
    }

    void upload(File file){
        try {
            uploadResult = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String getPhotoUrl(){
        return (String) uploadResult.get("url");
    }
}
