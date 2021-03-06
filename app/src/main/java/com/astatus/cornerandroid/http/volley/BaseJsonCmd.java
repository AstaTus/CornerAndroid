package com.astatus.cornerandroid.http.volley;

/**
 * Created by AstaTus on 2016/1/1.
 */

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.astatus.cornerandroid.application.CornerApplication;

import java.util.HashMap;

public class BaseJsonCmd<T> {
    private NormalRequest mRequest;

    public BaseJsonCmd(String baseUrl, String url, int methond,
                       Listener response, Class<T> responseClass, ErrorListener error,
                       HashMap<String, String> params){

        mRequest = new NormalRequest(baseUrl,
                url, methond, response, responseClass, error, params, true, false);

    }

    public BaseJsonCmd(String baseUrl, String url, int methond,
                       Listener response, Class<T> responseClass, ErrorListener error,
                       HashMap<String, String> params, boolean addCookie, boolean checkCookie){

        mRequest = new NormalRequest(baseUrl,
                url, methond, response, responseClass, error, params, addCookie, checkCookie);

    }

    public void excute(){
        /*CmdManager mgr = CornerApplication.getSingleton().getCmdMgr();
        mgr.addRequest(mRequest);*/
    }
}
