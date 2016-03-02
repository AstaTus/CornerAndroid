package com.astatus.cornerandroid.cache;

import java.math.BigInteger;

/**
 * Created by AstaTus on 2016/3/2.
 */
public class UserCache {
    private BigInteger mMainUserGuid;

    public void setMainUserGuid(BigInteger guid){
        mMainUserGuid = guid;
    }

    public BigInteger getMainUserGuid(){
        return mMainUserGuid;
    }

}
