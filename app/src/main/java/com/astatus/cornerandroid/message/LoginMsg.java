package com.astatus.cornerandroid.message;

/**
 * Created by AstaTus on 2016/1/10.
 */
public class LoginMsg {
    public boolean mResult;
    public int mCode;

    public static final int CODE_ACCOUNT_OR_PASSWORD_ERROR = 1;
    public static final int CODE_ACCOUNT_NOT_EXIST = 2;
}
