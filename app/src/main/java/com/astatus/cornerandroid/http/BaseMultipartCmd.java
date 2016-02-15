package com.astatus.cornerandroid.http;

import android.graphics.Bitmap;

import com.android.volley.Request;
import com.android.volley.Response;
import com.astatus.cornerandroid.application.CornerApplication;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by AstaTus on 2016/2/13.
 */
public class BaseMultipartCmd<T> {
    private MultipartRequest mRequest;


    public BaseMultipartCmd(String baseUrl, String url,
                       Response.Listener response, Class<T> responseClass,
                            MultipartParam param,
                            Response.ErrorListener error){

        mRequest = new MultipartRequest(baseUrl,
                url, Request.Method.POST, response, responseClass, error, param, true, false);
    }

    public BaseMultipartCmd(String baseUrl, String url, int methond,
                       Response.Listener response, Class<T> responseClass, Response.ErrorListener error,
                       MultipartParam param, boolean addCookie, boolean checkCookie){

        mRequest = new MultipartRequest(baseUrl,
                url, methond, response, responseClass,
                error, param, addCookie, checkCookie);

    }

    public void excute(){
        CmdManager mgr = CornerApplication.getSingleton().getCmdMgr();
        mgr.addRequest(mRequest);
    }
}
