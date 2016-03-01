package com.astatus.cornerandroid.cornerandroid.entity;

import java.math.BigInteger;
import java.util.Date;

/**
 * Created by AstaTus on 2016/2/23.
 */
public class ArticleEntity {
    public BigInteger mGuid;
    public String mName;
    public String mHeadUrl;
    public Date mTime;
    public String mImageUrl;
    public String mFeelText;
    public int mUpCount;
    public int mReadCount;
    public String[] mComments;
    public BigInteger mLocationGuid;
    public String mLocationName;
    public boolean mIsUp;

    public int mSegment;
}
