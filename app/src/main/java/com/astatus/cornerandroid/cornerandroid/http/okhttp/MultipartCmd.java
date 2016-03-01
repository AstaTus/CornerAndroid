package com.astatus.cornerandroid.cornerandroid.http.okhttp;

import com.astatus.cornerandroid.application.CornerApplication;
import com.astatus.cornerandroid.http.okhttp.*;
import com.astatus.cornerandroid.http.okhttp.CmdListener;
import com.astatus.cornerandroid.http.okhttp.MultipartParam;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by AstaTus on 2016/2/17.
 */
public class MultipartCmd<T> extends com.astatus.cornerandroid.http.okhttp.BaseCmd<T> {

    public MultipartCmd(String baseUrl, String url, CmdListener listener,
                        Class<T> responseClass, MultipartParam param){
        super(listener, responseClass);

        RequestBody body = param.getBody();
        mRequest = new Request.Builder()
                .url(baseUrl + url)
                .post(body)
                .build();
    }
}
