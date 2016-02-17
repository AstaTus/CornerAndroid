package com.astatus.cornerandroid.http.okhttp;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by AstaTus on 2016/2/17.
 */
public class CmdManager {

    private OkHttpClient mOkHttpClient;

    public void init(){
        CookieManager cookieManager = new CookieManager(null, CookiePolicy.ACCEPT_ORIGINAL_SERVER);

        mOkHttpClient = new OkHttpClient.Builder()
                .cookieJar(new JavaNetCookieJar(cookieManager))
                .build();

        //mOkHttpClient.coo();
    }

    public Call addRequest(Request request, Callback callback) throws IOException {
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(callback);
        return call;
    }
}
