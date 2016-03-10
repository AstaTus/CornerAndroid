package com.astatus.cornerandroid.message;

import java.math.BigInteger;

/**
 * Created by AstaTus on 2016/3/10.
 */
public class UpChangeStateMsg {
    public BigInteger mArticleGuid;
    public int mIsUp;

    public static final int CODE_UP_STATE = 1;
    public static final int CODE_UNUP_STATE = 2;
}
