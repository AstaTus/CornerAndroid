package com.astatus.cornerandroid.message;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by AstaTus on 2016/3/30.
 */
public class UserArticleBlock {
    public BigInteger mGuid;
    public Date mTime;
    public String mImagePath;
    public String mFeelText;
    public Integer mUpCount;
    public Integer mReadCount;
    public ArrayList<CommentBlock> mComments;
    public Boolean mIsUp;

    public CornerBlock mCorner;
    public UserBlock mUser;
//public String mUserGuid;
//public String mUserName;
//public String mHeadUrl;
}
