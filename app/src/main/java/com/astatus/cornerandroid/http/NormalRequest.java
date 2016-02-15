package com.astatus.cornerandroid.http;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.NetworkResponse;

import com.astatus.cornerandroid.application.CornerApplication;
import com.astatus.cornerandroid.message.LoginMsg;
import com.astatus.cornerandroid.message.MessagePacket;
import com.astatus.cornerandroid.message.RegisterMsg;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonObject;
import com.google.gson.JsonStreamParser;
import com.google.gson.reflect.TypeToken;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by AstaTus on 2016/1/4.
 */
public class NormalRequest<T> extends BaseRequest<T> {
    private Map<String, String> mParams;

    public NormalRequest(String baseUrl, String url, int method, Listener listener,
                         Class<T> responseClass, ErrorListener errorListener,
                         Map<String, String> params,
                         boolean addCookie, boolean checkCookie){

        super(baseUrl, url, method, listener, responseClass, errorListener, addCookie, checkCookie);

        mParams = params;

        setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mParams;
    }
}
