package com.astatus.cornerandroid.message;

import java.math.BigInteger;

/**
 * Created by AstaTus on 2016/3/24.
 */
public class UserFllowChangeMsg {
    public BigInteger mUserGuid;
    public int mState;

    public static final int CODE_STATE_FLLOW = 1;
    public static final int CODE_STATE_UNFLLOW = 2;
}
