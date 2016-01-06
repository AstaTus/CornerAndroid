package com.astatus.cornerandroid.application;

import android.app.Application;

/**
 * Created by AstaTus on 2016/1/1.
 */

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.astatus.cornerandroid.http.CmdManager;

import java.util.Objects;

public class CornerApplication extends Application {

    private static CornerApplication sSingleton;

    private CmdManager mCmdMgr;

    public synchronized static CornerApplication getSingleton() {
        return sSingleton;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mCmdMgr = new CmdManager();
        mCmdMgr.init(this);
        sSingleton = this;
    }

    public CmdManager getCmdMgr(){
        return mCmdMgr;
    }





}
