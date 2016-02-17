package com.astatus.cornerandroid.http.volley;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.Key;

/**
 * Created by AstaTus on 2016/2/16.
 */
public class NewMultipartParam {
    private MultipartEntity mEntity;

    public NewMultipartParam(){
        mEntity = new MultipartEntity();
    }

    public void addFilePart(String key, File file) {
        FileBody fileBody = new FileBody(file);
        mEntity.addPart(key, fileBody);

    }

    public void addTextPart(String key, String text) throws UnsupportedEncodingException {
        StringBody stringBody = new StringBody(text, Charset.forName("UTF-8"));
        mEntity.addPart(key, stringBody);
    }

    public MultipartEntity getBody(){
        return mEntity;
    }

}
