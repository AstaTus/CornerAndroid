package com.astatus.cornerandroid.message;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;

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
    public ArrayList<String> mImagePaths;
    public ArrayList<String> mFeelTexts;
    public ArrayList<Integer> mUpCounts;
    public ArrayList<Integer> mReadCounts;
    public ArrayList<CommentObtainMsg> mComments;
    public ArrayList<BigInteger> mLocationGuids;
    public ArrayList<String> mLocationNames;
    public ArrayList<Boolean> mIsUps;
}
