package com.astatus.cornerandroid.cornerandroid.http.volley;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response.Listener;
import com.android.volley.Response.ErrorListener;
import com.astatus.cornerandroid.http.volley.*;

import java.util.Map;

/**
 * Created by AstaTus on 2016/1/4.
 */
public class NormalRequest<T> extends com.astatus.cornerandroid.http.volley.BaseRequest<T> {
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
