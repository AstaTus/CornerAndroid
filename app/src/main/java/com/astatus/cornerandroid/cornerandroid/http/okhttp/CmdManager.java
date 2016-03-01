package com.astatus.cornerandroid.cornerandroid.http.okhttp;

import android.content.Context;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

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

    public void init(Context context){
        ClearableCookieJar cookieJar = new PersistentCookieJar(
                    new SetCookieCache(),
                new SharedPrefsCookiePersistor(context));

        mOkHttpClient = new OkHttpClient.Builder()
                .cookieJar(cookieJar)
                .build();

        //mOkHttpClient.coo();
    }

    public Call addRequest(Request request, Callback callback) throws IOException {
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(callback);
        return call;
    }
}
