package com.astatus.cornerandroid.entity;

import java.math.BigInteger;
import java.util.Date;

/**
 * Created by AstaTus on 2016/3/1.
 */
public class CommentEntity {
    public BigInteger mGuid;
    public BigInteger mReplyGuid;
    public String mReplyHeadUrl;
    public String mReplyName;

    public String mTargetName;

    public Date mTime;
    public String mFeelText;

}
