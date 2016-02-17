package com.astatus.cornerandroid.http.okhttp;

import com.astatus.cornerandroid.application.CornerApplication;

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
public class GetCmd<T> extends BaseCmd<T> {
    public GetCmd(String baseUrl, String url, CmdListener listener,
                  Class<T> responseClass){

        super(baseUrl, url, listener, responseClass);
        mRequest = new Request.Builder()
                .url(baseUrl + url)
                .build();
    }
}
