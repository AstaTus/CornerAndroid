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
import com.astatus.cornerandroid.http.okhttp.CornerAddCmd;
import com.astatus.cornerandroid.message.CornerAddMsg;
import com.astatus.cornerandroid.model.CornerModel;
import com.astatus.cornerandroid.util.AmapUtil;
import com.astatus.cornerandroid.view.ILocationView;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by AstaTus on 2016/3/16.
 */
public class LocationPresenter implements AMapLocationListener,
        CloudSearch.OnCloudSearchListener {

    private Context mContext;
    private ILocationView mLocationView;
    private AMapLocationClient mLocationClient = null;
    private AMapLocationClientOption mLocationOption = null;
    private CloudSearch mCloudSearch;
    private CloudSearch.Query mQuery;
    private CornerModel mModel;

    private LatLonPoint mCurrentLoc;

    public LocationPresenter(Context context, ILocationView view){

        mContext = context;
        mLocationView = view;

        init();
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

    public boolean isLocation(){
        if (mCurrentLoc == null){
            return false;
        }

        return true;
    }

    public void bindArticleListData(){
        mLocationView.bindCornerData(mModel.getCornerList());
    }


    public void querySearchCorner(String key) throws AMapException {

        // 设置中心点及检索范围
        CloudSearch.SearchBound bound = new CloudSearch.SearchBound(new LatLonPoint(
                mCurrentLoc.getLatitude(), mCurrentLoc.getLongitude()), 4000);
        //设置查询条件 mTableID是将数据存储到数据管理台后获得。
        mQuery = new CloudSearch.Query(AmapUtil.TABLE_ID, key, bound);
        mCloudSearch.searchCloudAsyn(mQuery);
    }

    public void queryCornerNextPage(){
        if (mQuery.getPageNum() < mQuery.getPageSize() - 1){
            mQuery.setPageNum(mQuery.getPageNum() + 1);
            mCloudSearch.searchCloudAsyn(mQuery);
        }
    }

    public void createNewCorner(String name){

        CornerAddCmd cmd = CornerAddCmd.create(
                new CornerAddCmdListener()
                , name
                , mCurrentLoc.toString());
        cmd.excute();
    }

    private class CornerAddCmdListener implements CmdListener<CornerAddMsg>{

        @Override
        public void onSuccess(CornerAddMsg result) {
            mLocationView.notifyCreateCornerSuccess();
        }

        @Override
        public void onFailed() {
            mLocationView.showCreateCornerFailed();
        }
    }

    public void destroy(){
        if (null != mLocationClient) {

            mLocationClient.onDestroy();
            mLocationClient = null;
            mLocationOption = null;
        }
    }

    @Override
    public void onLocationChanged(AMapLocation location) {

        //定位成功
        if (location.getErrorCode() == AMapLocation.LOCATION_SUCCESS){

            mLocationView.showFinishLocation();
            try {
                querySearchCorner("");
            } catch (AMapException e) {
                e.printStackTrace();
            }

        }else{
            mLocationView.showLocationFailed();
        }
    }

    @Override
    public void onCloudSearched(CloudResult cloudResult, int i) {

        CornerEntity entity;
        CloudItem item;
        List<CloudItem> items = cloudResult.getClouds();
        for (int k = 0; k < items.size(); ++k){

            entity = new CornerEntity();
            item = items.get(k);

            entity.mGuid = BigInteger.valueOf(Long.valueOf(item.getID()));
            entity.mName = item.getTitle();
            mModel.addCorner(entity);
        }

        if (mQuery.getPageNum() == 0){
            mLocationView.changeRecyclerViewFoot(3);
        }else if (mQuery.getPageNum() == mQuery.getPageSize() - 1){
            mLocationView.changeRecyclerViewFoot(2);
        }else{
            mLocationView.changeRecyclerViewFoot(0);
        }

        mLocationView.showNextPageCorner();
    }

    @Override
    public void onCloudItemDetailSearched(CloudItemDetail cloudItemDetail, int i) {

    }
}
