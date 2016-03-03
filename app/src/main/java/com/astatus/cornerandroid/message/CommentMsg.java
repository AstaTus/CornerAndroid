package com.astatus.cornerandroid.message;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by AstaTus on 2016/3/2.
 */
public class CommentMsg {
    public BigInteger mArticleGuid;
    public ArrayList<BigInteger> mGuids;
    public ArrayList<BigInteger> mReplyGuids;
    public ArrayList<String> mReplyNames;
    public ArrayList<String> mHeadUrls;

    public ArrayList<BigInteger> mTargetGuids;
    public ArrayList<String> mTargetNames;
    public ArrayList<Date> mTimes;
    public ArrayList<String> mTexts;

}

