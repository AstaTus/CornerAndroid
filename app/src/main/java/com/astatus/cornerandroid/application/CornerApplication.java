package com.astatus.cornerandroid.application;

import android.app.Application;

/**
 * Created by AstaTus on 2016/1/1.
 */

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class CornerApplication extends Application {

    private static CornerApplication sSingleton;

    private RequestQueue mRequestQueue;

    @Override
    public void onCreate() {
        super.onCreate();

        mRequestQueue = Volley.newRequestQueue(this);

        sSingleton = this;
    }

    public synchronized static CornerApplication getSingleton() {
        return sSingleton;
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }
}
