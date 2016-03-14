package com.astatus.cornerandroid.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by AstaTus on 2016/1/1.
 */

import com.astatus.cornerandroid.cache.UserCache;
import com.astatus.cornerandroid.http.okhttp.CmdManager;
import com.astatus.cornerandroid.message.MessagePacket;
import com.astatus.cornerandroid.model.SharedPreferenceDef;

public class CornerApplication extends Application {

    private static CornerApplication sSingleton;

    private CmdManager mCmdMgr = null;
    private SharedPreferences mSharedPreferences = null;

    private UserCache mUserCache;

    public synchronized static CornerApplication getSingleton() {
        return sSingleton;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());
        sSingleton = this;
    }

    public CmdManager getCmdMgr(){
        if (mCmdMgr == null){
            mCmdMgr = new CmdManager();
            mCmdMgr.init(this);
        }

        return mCmdMgr;
    }

    public UserCache getUserCache(){
        if (mUserCache == null){
            mUserCache = new UserCache();

        }

        return mUserCache;
    }


    public SharedPreferences getSharedPreferences(){
        if (mSharedPreferences == null){
            mSharedPreferences = this.getSharedPreferences(
                    SharedPreferenceDef.PREFERENCE_NAME, Activity.MODE_PRIVATE);
        }
        return mSharedPreferences;
    }
}
