package com.astatus.cornerandroid.http;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.NetworkResponse;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by AstaTus on 2016/1/4.
 */
public class NormalRequest extends Request<JSONObject> {
    protected static final String SET_COOKIE_KEY = "Set-Cookie";
    protected static final String COOKIE_KEY = "Cookie";
    protected static final String SESSION_COOKIE = "sessionid";

    private Map<String, String> mParams;
    private Listener<JSONObject> mListener;
    private String mUrl;

    private boolean mIsCheckCookie = false;
    private boolean mIsAddCookie = true;

    public NormalRequest(String baseUrl, String url, int method, Listener<JSONObject> listener,
                         ErrorListener errorListener, Map<String, String> params,
                         boolean addCookie, boolean checkCookie){

        super(method, baseUrl + url, errorListener);

        mListener = listener;
        mParams = params;
        mUrl = baseUrl;

        mIsCheckCookie = checkCookie;
        mIsAddCookie = addCookie;

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mParams;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = super.getHeaders();

        byte[] body = super.getBody();
        if (headers == null || headers.equals(Collections.EMPTY_MAP)){
            headers = new HashMap<String, String>();
        }

        if (mIsAddCookie){
            addSessionCookie(headers);
        }

        return  headers;
    }

    @Override
    protected void deliverResponse(JSONObject response){
        mListener.onResponse(response);
    }

    @Override
    public void deliverError(VolleyError error) {
        super.deliverError(error);

        if (error != null){
            //cookie 过期 跳转到登陆界面

        }
    }

    @Override
    protected VolleyError parseNetworkError(VolleyError volleyError) {
        return super.parseNetworkError(volleyError);
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response){

        if (mIsAddCookie){
            checkSessionCookie(response.headers);
        }

        try {
            String jsonStr = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(new JSONObject(jsonStr), HttpHeaderParser.parseCacheHeaders(response));
        }catch (UnsupportedEncodingException e){
            return Response.error(new ParseError(e));
        }catch (JSONException e){
            return Response.error(new ParseError(e));
        }
    }

    protected void checkSessionCookie(Map<String, String> headers){
        try{
            String headerStr = headers.toString();

            Pattern pattern = Pattern.compile("Set-Cookie.*?;");
            Matcher matcher = pattern.matcher(headerStr);
            String cookie = "";
            if (matcher.find()){
                cookie = matcher.group();

                cookie = cookie.substring(11, cookie.length() - 1);

                //add Cookie
            }
        }catch (Exception e){

        }
    }

    protected void addSessionCookie(Map<String, String> header){
        //get cookie
        String cookie = "";


        if (cookie.length() > 0){
            header.put("Cookie", cookie);
        }
    }
}
