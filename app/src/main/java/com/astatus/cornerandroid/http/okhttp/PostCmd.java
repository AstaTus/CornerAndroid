package com.astatus.cornerandroid.http.okhttp;

import com.android.volley.Response;
import com.astatus.cornerandroid.application.CornerApplication;
import com.astatus.cornerandroid.http.volley.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by AstaTus on 2016/2/17.
 */
public class PostCmd<T> extends BaseCmd<T> {

    public PostCmd(String baseUrl, String url, CmdListener listener,
                   Class<T> responseClass, HashMap<String, String> params){

        super(baseUrl, url, listener, responseClass);

        FormBody.Builder builder = new FormBody.Builder();
        if (null != params) {
            Iterator iter = params.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                builder.add(entry.getKey().toString(), entry.getValue().toString());
            }
        }else{
            /*throw new Exception("params is null");*/
        }

        RequestBody body = builder.build();
        mRequest = new Request.Builder()
                .url(baseUrl + url)
                .post(body)
                .build();
    }
}
