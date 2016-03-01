package com.astatus.cornerandroid.cornerandroid.http.volley;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.astatus.cornerandroid.application.CornerApplication;
import com.astatus.cornerandroid.message.MessagePacket;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by AstaTus on 2016/2/14.
 */
public class BaseRequest<T> extends Request<MessagePacket> {
    protected static final String SET_COOKIE_KEY = "Set-Cookie";
    protected static final String COOKIE_KEY = "Cookie";
    protected static final String SESSION_COOKIE = "sessionid";
    protected static final Gson mGson = new Gson();

    private final Class<T> mResponseClass;
    private Response.Listener mListener;
    private String mUrl;
    private static String mCookie;


    private boolean mIsCheckCookie = false;
    private boolean mIsAddCookie = true;

    public BaseRequest(String baseUrl, String url, int method, Response.Listener listener,
                         Class<T> responseClass, Response.ErrorListener errorListener,
                         boolean addCookie, boolean checkCookie){

        super(method, baseUrl + url, errorListener);

        mListener = listener;
        mResponseClass = responseClass;
        mUrl = baseUrl;

        mIsCheckCookie = checkCookie;
        mIsAddCookie = addCookie;

        setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = super.getHeaders();

        if (headers == null || headers.equals(Collections.EMPTY_MAP)){
            headers = new HashMap<String, String>();
        }

        if (mIsAddCookie){
            addSessionCookie(headers);
        }

        return  headers;
    }

    @Override
    protected void deliverResponse(MessagePacket response){
        if (response.result == MessagePacket.RESULT_SUCCESS){
            mListener.onResponse(response.msg);
        }else{
            CornerApplication.getSingleton().onServerErrorResponseListener(response);
        }

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
    protected Response<MessagePacket> parseNetworkResponse(NetworkResponse response){

        if (mIsCheckCookie){
            checkSessionCookie(response.headers);
        }

        try {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers, "utf-8"));

            MessagePacket packet = new MessagePacket<T>();
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(jsonString);
            JsonObject obj = element.getAsJsonObject();
            packet.result = obj.get("result").getAsBoolean();
            packet.resultCode = obj.get("resultCode").getAsInt();
            packet.msg = mGson.fromJson(obj.get("msg"), mResponseClass);

            return Response.success(packet,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (Exception ce) {
            return Response.error(new ParseError(ce));
        }
    }

    protected void checkSessionCookie(Map<String, String> headers){
        try{
            String headerStr = headers.toString();

            Pattern pattern = Pattern.compile("set-cookie.*?;");
            Matcher matcher = pattern.matcher(headerStr);
            String cookie = "";
            if (matcher.find()){
                cookie = matcher.group();

                mCookie = cookie.substring(11, cookie.length() - 1);

                //add Cookie
            }
        }catch (Exception e){

        }
    }

    protected void addSessionCookie(Map<String, String> header){
        //get cookie
        String cookie = "";

        if (mCookie.length() > 0){
            header.put("Cookie", mCookie);
        }
    }
}
