package com.astatus.cornerandroid.application;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;

/**
 * Created by AstaTus on 2016/1/1.
 */

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.astatus.cornerandroid.http.CmdManager;
import com.astatus.cornerandroid.model.SharedPreferenceDef;

import java.util.Objects;

public class CornerApplication extends Application {

    private static CornerApplication sSingleton;

    private CmdManager mCmdMgr;
    private SharedPreferences mSharedPreferences;

    public synchronized static CornerApplication getSingleton() {
        return sSingleton;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sSingleton = this;

        mCmdMgr = new CmdManager();
        mCmdMgr.init(this);

        mSharedPreferences = this.getSharedPreferences(
                SharedPreferenceDef.PREFERENCE_NAME, Activity.MODE_PRIVATE);
    }

    public CmdManager getCmdMgr(){
        return mCmdMgr;
    }


    public SharedPreferences getSharedPreferences(){
        return mSharedPreferences;
    }


}
