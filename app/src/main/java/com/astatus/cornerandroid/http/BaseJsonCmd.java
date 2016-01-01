package com.astatus.cornerandroid.http;

/**
 * Created by AstaTus on 2016/1/1.
 */

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.astatus.cornerandroid.application.CornerApplication;

import java.util.concurrent.ConcurrentHashMap;

public class BaseJsonCmd {
    private JsonObjectRequest mRequest;

    public BaseJsonCmd(String baseUrl, String url, int methond, ConcurrentHashMap params){

    }

    public void excute(){
        RequestQueue queue = CornerApplication.getSingleton().getRequestQueue();
        queue.add(mRequest);
    }
}
