package com.astatus.cornerandroid.http;

/**
 * Created by AstaTus on 2016/1/1.
 */

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonObjectRequest;
import com.astatus.cornerandroid.application.CornerApplication;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class BaseJsonCmd {
    private JsonObjectRequest mRequest;

    public BaseJsonCmd(String baseUrl, String url, int methond,
                       Listener<JSONObject> response, ErrorListener error,
                       HashMap<String, String> params){

        mRequest = new NormalRequest(baseUrl, url, response, error, params, true, false);

    }

    public void excute(){
        RequestQueue queue = CornerApplication.getSingleton().getRequestQueue();
        queue.add(mRequest);
    }
}
