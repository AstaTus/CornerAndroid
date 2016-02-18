package com.astatus.cornerandroid.http.okhttp;

import com.astatus.cornerandroid.application.CornerApplication;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by AstaTus on 2016/2/17.
 */
public class BaseCmd<T> {
    protected Request mRequest;
    private Call mCall;
    private CmdListener mListener;
    private final Class<T> mResponseClass;

    public BaseCmd(String baseUrl, String url, CmdListener listener,
                   Class<T> responseClass) {

        mResponseClass = responseClass;
        mListener = listener;
    }

    public void excute()  {
        com.astatus.cornerandroid.http.okhttp.CmdManager mgr = CornerApplication.getSingleton().getCmdMgr();

        try {
            mCall = mgr.addRequest(mRequest, new CommonCallback<T>(mListener, mResponseClass));
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void cancel() {
        mCall.cancel();
    }
}