package com.astatus.cornerandroid.presenter;

import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.cloud.CloudItem;
import com.amap.api.services.cloud.CloudItemDetail;
import com.amap.api.services.cloud.CloudResult;
import com.amap.api.services.cloud.CloudSearch;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.astatus.cornerandroid.entity.CornerEntity;
import com.astatus.cornerandroid.http.okhttp.CmdListener;
import com.astatus.cornerandroid.http.okhttp.HomeCmd;
import com.astatus.cornerandroid.message.HomeMsg;
import com.astatus.cornerandroid.model.CornerModel;
import com.astatus.cornerandroid.model.HomeModel;
import com.astatus.cornerandroid.util.AmapUtil;
import com.astatus.cornerandroid.view.IHomeView;
import com.astatus.cornerandroid.view.ILocationView;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by AstaTus on 2016/3/30.
 */
public class HomePresenter implements AMapLocationListener,
        CloudSearch.OnCloudSearchListener{

    private static final int RELOCATION_INTERVAL = 30000;
    private Context mContext;
    private AMapLocationClient mLocationClient = null;
    private AMapLocationClientOption mLocationOption = null;
    private CloudSearch mCloudSearch;
    private CloudSearch.Query mQuery;
    private HomeModel mModel;
    private IHomeView mHomeView;
    private LatLonPoint mCurrentLoc;

    //重新定位算法:根据时间间隔来确定是否重新定位


    private long mPrevLocationTime;

    public HomePresenter(Context context, IHomeView homeView){
        mContext = context;
        mHomeView = homeView;
        mModel = new HomeModel();

        mHomeView.bindCornerData(mModel.getHomeArticles());
    }

    private void init(){
        mLocationClient = new AMapLocationClient(mContext);
        mLocationOption = new AMapLocationClientOption();
        // 设置定位模式为低功耗模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        mLocationOption.setOnceLocation(true);
        // 设置定位监听
        mLocationClient.setLocationListener(this);
        mLocationClient.setLocationOption(mLocationOption);

        //云搜索
        mCloudSearch = new CloudSearch(mContext);// 初始化查询类
        mCloudSearch.setOnCloudSearchListener(this);// 设置回调函数
    }

    public void startLocation(){
        mLocationClient.startLocation();
    }

    public void stopLocation(){
        mLocationClient.stopLocation();
    }

    public void loadNewArticles(){
        //HomeCmd cmd = HomeCmd.create(new LoadHomeAritcleListener(), );
    }

    public void loadNextArticles(){

    }

    public void querySearchCorner(String key) throws AMapException {

        // 设置中心点及检索范围
        CloudSearch.SearchBound bound = new CloudSearch.SearchBound(new LatLonPoint(
                mCurrentLoc.getLatitude(), mCurrentLoc.getLongitude()), 4000);
        //设置查询条件 mTableID是将数据存储到数据管理台后获得。
        mQuery = new CloudSearch.Query(AmapUtil.TABLE_ID, key, bound);
        mCloudSearch.searchCloudAsyn(mQuery);
    }

    @Override
    public void onLocationChanged(AMapLocation location) {
        //定位成功
        if (location.getErrorCode() == AMapLocation.LOCATION_SUCCESS){

            //mLocationView.showFinishLocation();
            try {
                mCurrentLoc = new LatLonPoint(location.getLatitude(), location.getLongitude());
                querySearchCorner("");
            } catch (AMapException e) {
                e.printStackTrace();
            }

        }else{
            mHomeView.showLocationFailed();
        }
    }

    @Override
    public void onCloudSearched(CloudResult cloudResult, int rCode) {
        if (rCode == 1000){

            CloudItem item;
            List<CloudItem> items = cloudResult.getClouds();
            ArrayList<BigInteger> cornerGuids = new ArrayList<BigInteger>();
            for (int k = 0; k < items.size(); ++k){
                item = items.get(k);

                cornerGuids.add(BigInteger.valueOf(Long.valueOf(item.getID())));
            }

            mHomeView.showFinishSearchCorner();
        }

        mHomeView.showSearchCornerFailed();
    }

    @Override
    public void onCloudItemDetailSearched(CloudItemDetail cloudItemDetail, int i) {

    }

    //是否重新定位
    public boolean isReLocation(){
        if(System.currentTimeMillis() - mPrevLocationTime > RELOCATION_INTERVAL){
            mPrevLocationTime = System.currentTimeMillis();
            return true;
        }else{
            return false;
        }
    }

    class LoadHomeAritcleListener implements CmdListener<HomeMsg>{

        @Override
        public void onSuccess(HomeMsg result) {

        }

        @Override
        public void onFailed() {

        }

        @Override
        public void onResponseFailed(int code) {

        }
    }
}
