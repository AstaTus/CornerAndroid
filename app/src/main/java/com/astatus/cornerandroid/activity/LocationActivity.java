package com.astatus.cornerandroid.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.astatus.cornerandroid.R;
import com.astatus.cornerandroid.adapder.LocationRecyclerAdapter;
import com.astatus.cornerandroid.entity.CornerEntity;
import com.astatus.cornerandroid.presenter.LocationPresenter;
import com.astatus.cornerandroid.view.ILocationView;
import com.astatus.cornerandroid.widget.HeadFootRecyclerView;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.math.BigInteger;
import java.util.List;

public class LocationActivity extends AppCompatActivity
        implements ILocationView {


    private LocationPresenter mPresenter;
    private HeadFootRecyclerView mRecyclerView;
    private LocationRecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        init();
        mPresenter = new LocationPresenter(this, this);
        mPresenter.startLocation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    private void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.location_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        // Enable the Up button

        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.widget_location_head);

        mRecyclerView = (HeadFootRecyclerView) findViewById(R.id.location_recyclerView);
        mRecyclerView.setOnLoadMoreListener(new HeadFootRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mPresenter.queryCornerNextPage();
            }
        });

        mAdapter = new LocationRecyclerAdapter(this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CornerEntity corner = (CornerEntity)v.getTag();
                if (corner != null){
                    //返回
                    Intent in = new Intent();
                    in.putExtra("guid", corner.mGuid.toString());
                    in.putExtra("name", corner.mName);
                    setResult(RESULT_OK, in);
                    finish();
                }
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }


    @Override
    public void showFinishLocation() {

    }

    @Override
    public void showLocationFailed() {

    }

    @Override
    public void showNextPageCorner() {

    }


    @Override
    public void showLoadNearbyCornerFailed() {

    }

    @Override
    public void showFinishCreateCorner() {

    }

    @Override
    public void showCreateCornerFailed() {

    }

    @Override
    public void notifyCreateCornerSuccess() {

    }

    @Override
    public void changeRecyclerViewFoot(int style) {

    }

    @Override
    public void bindCornerData(List<CornerEntity> list) {

    }
}

