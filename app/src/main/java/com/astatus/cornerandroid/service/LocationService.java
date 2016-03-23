package com.astatus.cornerandroid.service;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;

/**
 * Created by AstaTus on 2016/3/23.
 */
public class LocationService extends IntentService implements AMapLocationListener {

    private static final long LOCATION_INTERVAL = 1000 * 60 * 5;

    private AMapLocationClient mLocationClient = null;
    private AMapLocationClientOption mLocationOption = null;

    private LatLonPoint mCurrentLoc;

    public LocationService() {
        super("aaa");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mLocationClient = new AMapLocationClient(this);
        mLocationOption = new AMapLocationClientOption();
        // 设置定位模式为低功耗模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        mLocationOption.setOnceLocation(false);
        mLocationOption.setInterval(LOCATION_INTERVAL);
        // 设置定位监听
        mLocationClient.setLocationListener(this);
        mLocationClient.setLocationOption(mLocationOption);

        //开始监听
        mLocationClient.startLocation();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    protected void onHandleIntent(Intent intent) {

    }

    @Override
    public void onLocationChanged(AMapLocation location) {
        //定位成功
        if (location.getErrorCode() == AMapLocation.LOCATION_SUCCESS){
            mCurrentLoc = new LatLonPoint(location.getLatitude(), location.getLongitude());
        }
    }
}
