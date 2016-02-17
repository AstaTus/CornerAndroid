package com.astatus.cornerandroid.http.volley;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;

import org.apache.http.entity.mime.MultipartEntity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by AstaTus on 2016/2/16.
 */
public class NewMultipartRequest<T> extends BaseRequest<T> {

    private MultipartEntity mEntity;

    public NewMultipartRequest(String baseUrl, String url, int method, Response.Listener listener, Class<T> responseClass, Response.ErrorListener errorListener, boolean addCookie, boolean checkCookie) {
        super(baseUrl, url, method, listener, responseClass, errorListener, addCookie, checkCookie);
    }

    public NewMultipartRequest(String baseUrl, String url, int method, Response.Listener listener,
                            Class<T> responseClass, Response.ErrorListener errorListener,
                               NewMultipartParam param,
                            boolean addCookie, boolean checkCookie){

        super(baseUrl, url, method,
                listener, responseClass, errorListener, addCookie, checkCookie);

        mEntity = param.getBody();

    }

    @Override
    public String getBodyContentType() {
        return mEntity.getContentType().toString();
    }

    @Override
    public byte[] getBody() throws AuthFailureError {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            mEntity.writeTo(bos);
        } catch (IOException e) {
            Log.e("error", "getBody: ");
        }
        return bos.toByteArray();
    }
}
