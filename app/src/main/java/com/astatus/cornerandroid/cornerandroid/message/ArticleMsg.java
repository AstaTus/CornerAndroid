package com.astatus.cornerandroid.cornerandroid.message;

import com.astatus.cornerandroid.entity.ArticleEntity;

import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by AstaTus on 2016/2/24.
 */
public class ArticleMsg {
    public boolean mIsTimeOut;
    public ArrayList<BigInteger> mGuids;
    public ArrayList<String> mUserGuids;
    public ArrayList<String> mUserNames;
    public ArrayList<String> mHeadUrls;
    public ArrayList<Date> mTimes;
    public ArrayList<String> mImageUrls;
    public ArrayList<String> mFeelTexts;
    public ArrayList<Integer> mUpCount;
    public ArrayList<Integer> mReadCount;
    public ArrayList<ArrayList<String>> mComments;
    public ArrayList<BigInteger> mLocationGuid;
    public ArrayList<String> mLocationName;
    public ArrayList<Boolean> mIsUps;
}
