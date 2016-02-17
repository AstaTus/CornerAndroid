package com.astatus.cornerandroid.http.volley;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.net.CookieManager;
import java.net.CookiePolicy;

import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.CookieJar;
/**
 * Created by AstaTus on 2016/1/1.
 */
public class CmdManager {

    private RequestQueue mRequestQueue;

    protected RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    protected void addRequest(Request<JSONObject> request){
        mRequestQueue.add(request);
    }
    public void init(Context context){

        mRequestQueue = Volley.newRequestQueue(context);
        mRequestQueue.start();
    }

    public void CancelRequests(){
        if (mRequestQueue != null){
            mRequestQueue.cancelAll(new Object());
        }
    }
}
