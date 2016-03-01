package com.astatus.cornerandroid.cornerandroid.http.okhttp;

import java.io.File;
import java.util.HashMap;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by AstaTus on 2016/2/17.
 */
public class MultipartParam {

    private static final MediaType MEDIA_TYPE = MediaType.parse("image/jpeg");
    private MultipartBody.Builder mBuilder;

    public MultipartParam(){
        mBuilder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
    }

    public void addFilePart(String fileName, File file){
        if(file.exists()){
            mBuilder.addFormDataPart("image", fileName,
                    RequestBody.create(MEDIA_TYPE, file));
        }

    }

    public void addParamPart(String key, String value){
        mBuilder.addFormDataPart(key, value);
    }


    public MultipartBody getBody(){
        return mBuilder.build();
    }
}
