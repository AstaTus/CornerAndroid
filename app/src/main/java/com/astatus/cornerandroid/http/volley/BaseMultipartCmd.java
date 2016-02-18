package com.astatus.cornerandroid.http.volley;

import com.android.volley.Request;
import com.android.volley.Response;
import com.astatus.cornerandroid.application.CornerApplication;

/**
 * Created by AstaTus on 2016/2/13.
 */
public class BaseMultipartCmd<T> {
    private NewMultipartRequest mRequest;


    public BaseMultipartCmd(String baseUrl, String url,
                       Response.Listener response, Class<T> responseClass,
                            NewMultipartParam param,
                            Response.ErrorListener error){

        mRequest = new NewMultipartRequest(baseUrl,
                url, Request.Method.POST, response, responseClass, error, param, true, false);
    }

    public BaseMultipartCmd(String baseUrl, String url, int methond,
                       Response.Listener response, Class<T> responseClass, Response.ErrorListener error,
                       NewMultipartParam param, boolean addCookie, boolean checkCookie){

        mRequest = new NewMultipartRequest(baseUrl,
                url, methond, response, responseClass,
                error, param, addCookie, checkCookie);

    }

    public void excute(){
        /*CmdManager mgr = CornerApplication.getSingleton().getCmdMgr();
        mgr.addRequest(mRequest);*/
    }
}
