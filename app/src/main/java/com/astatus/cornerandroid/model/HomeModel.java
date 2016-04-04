package com.astatus.cornerandroid.model;

import com.amap.api.services.core.LatLonPoint;

import java.math.BigInteger;
import java.util.ArrayList;

/**
 * Created by AstaTus on 2016/3/30.
 */
public class HomeModel {

    public LatLonPoint mCurrentLoc;
    private ArrayList<BigInteger> mCornerGuids;

    private ArrayList<Object> mArticles;

    public HomeModel(){

    }

    public void setCurrentLocation(LatLonPoint location){
        mCurrentLoc = location;
    }

    public LatLonPoint getCurrentLocaiton(){
        return mCurrentLoc;
    }

    public void setCornerGuids(ArrayList<BigInteger> guids){
        mCornerGuids = guids;
    }

    public ArrayList<BigInteger> getCornerGuids(){
        return mCornerGuids;
    }

    public ArrayList<Object> getHomeArticles(){
        return mArticles;
    }
}
