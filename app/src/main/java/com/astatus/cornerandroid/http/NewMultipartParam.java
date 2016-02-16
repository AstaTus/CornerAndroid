package com.astatus.cornerandroid.http;

import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

import java.io.File;
import java.io.IOException;

/**
 * Created by AstaTus on 2016/2/16.
 */
public class NewMultipartParam {

    private MultipartEntityBuilder mBuilder;
    private HttpEntity mEntity;

    public NewMultipartParam(){
        mBuilder = MultipartEntityBuilder.create();
    }

    public void addFilePart(String key, File file) throws IOException {
        FileBody fileBody = new FileBody(file);
        mBuilder.addPart(key, fileBody);
    }

    public void addTextPart(String key, String text) throws IOException {
        StringBody stringBody = new StringBody(text, ContentType.TEXT_PLAIN);
        mBuilder.addPart(key, stringBody);
    }

    public HttpEntity getBody(){
        try {
            return mBuilder.build();
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }


}
