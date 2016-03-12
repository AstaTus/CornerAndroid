package com.astatus.cornerandroid.message;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by AstaTus on 2016/3/12.
 */
public class CommentBlock {

    public BigInteger mGuid;
    public BigInteger mArticleGuid;
    public BigInteger mReplyGuid;
    public String mReplyName;
    public String mHeadUrl;

    public BigInteger mTargetGuid;
    public String mTargetName;
    public Date mTime;
    public String mText;
}
