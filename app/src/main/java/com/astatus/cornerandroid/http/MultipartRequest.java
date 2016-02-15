package com.astatus.cornerandroid.http;

/**
 * Created by AstaTus on 2016/2/13.
 */
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.astatus.cornerandroid.message.MessagePacket;

import java.util.Map;

class MultipartRequest<T> extends BaseRequest<T> {

    private MultipartParam mParam;

    public MultipartRequest(String baseUrl, String url, int method, Response.Listener listener,
                         Class<T> responseClass, Response.ErrorListener errorListener,
                         MultipartParam param,
                         boolean addCookie, boolean checkCookie){

        super(baseUrl, url, method,
                listener, responseClass, errorListener, addCookie, checkCookie);

        mParam = param;

    }

    @Override
    public String getBodyContentType() {
        return mParam.getMimeType();
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        return mParam.getBody();
    }
}