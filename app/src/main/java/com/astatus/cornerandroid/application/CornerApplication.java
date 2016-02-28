package com.astatus.cornerandroid.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by AstaTus on 2016/1/1.
 */

import com.astatus.cornerandroid.http.okhttp.CmdManager;
import com.astatus.cornerandroid.message.MessagePacket;
import com.astatus.cornerandroid.model.SharedPreferenceDef;

public class CornerApplication extends Application {

    private static CornerApplication sSingleton;

    private CmdManager mCmdMgr = null;
    private SharedPreferences mSharedPreferences = null;

    public synchronized static CornerApplication getSingleton() {
        return sSingleton;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sSingleton = this;
    }

    public CmdManager getCmdMgr(){
        if (mCmdMgr == null){
            mCmdMgr = new CmdManager();
            mCmdMgr.init(this);
        }

        return mCmdMgr;
    }


    public SharedPreferences getSharedPreferences(){
        if (mSharedPreferences == null){
            mSharedPreferences = this.getSharedPreferences(
                    SharedPreferenceDef.PREFERENCE_NAME, Activity.MODE_PRIVATE);
        }
        return mSharedPreferences;
    }

    /*public ImageLoader getImageLoader(){
        if (mImageLoader == null){
            initImageLoader(this);
        }
        return mImageLoader;
    }*/


    public void onServerErrorResponseListener(MessagePacket message){
        if (message.resultCode == MessagePacket.RESULT_CODE_SERVER_SESSION_ERROR){

        }
    }

    /*private void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app

        // Initialize ImageLoader with configuration.
        mImageLoader = ImageLoader.getInstance();
        mImageLoader.init(config.build());
    }*/
}
