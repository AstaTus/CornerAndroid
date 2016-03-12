package com.astatus.cornerandroid.message;

import java.math.BigInteger;

/**
 * Created by AstaTus on 2016/3/10.
 */
public class UpChangeStateMsg {
    public BigInteger mArticleGuid;
    public boolean mIsUp;

    public static final boolean CODE_UP_STATE = true;
    public static final boolean CODE_UNUP_STATE = false;
}
