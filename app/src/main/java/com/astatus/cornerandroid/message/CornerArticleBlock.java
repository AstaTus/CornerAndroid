package com.astatus.cornerandroid.message;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by AstaTus on 2016/3/30.
 */
public class CornerArticleBlock {
////角落guid
//public BigInteger mGuid;
////角落名字
//public String mName;
//文章的guid
    public BigInteger mGuid;
    public CornerBlock mCorner;

    public String mImagePath;
    public String mFeelText;
    public Integer mBeenToCount;
    public Integer mWantToCount;
    public ArrayList<CommentBlock> mComments;

    //想去,去过状态
    public Integer mState;
}
