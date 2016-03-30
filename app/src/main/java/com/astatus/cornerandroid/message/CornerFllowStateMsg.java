package com.astatus.cornerandroid.message;

import java.math.BigInteger;

/**
 * Created by AstaTus on 2016/3/24.
 */
public class CornerFllowStateMsg {
    public BigInteger mCornerGuid;
    public int mState;

    public static final int CODE_STATE_BEEN_TO = 1;
    public static final int CODE_STATE_WANT_TO = 2;
}
