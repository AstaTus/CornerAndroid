package com.astatus.cornerandroid.entity;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by AstaTus on 2016/2/23.
 */
public class ArticleEntity {
    public BigInteger mGuid;
    public String mUserName;
    public String mHeadUrl;
    public Date mTime;
    public String mImagePath;
    public String mFeelText;
    public int mUpCount;
    public int mReadCount;
    public ArrayList<CommentEntity> mComments;
    public BigInteger mLocationGuid;
    public String mLocationName;
    public boolean mIsUp;

    //用于Up的更新
    public boolean mNeedUpdateUp = false;
}
