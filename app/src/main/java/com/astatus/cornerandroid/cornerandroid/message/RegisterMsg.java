package com.astatus.cornerandroid.cornerandroid.message;

/**
 * Created by AstaTus on 2016/1/9.
 */
public class RegisterMsg {
    public boolean mResult;
    public int mCode;

    public static final int CODE_EMAIL_REAPT = 1;
    public static final int CODE_NICKNAME_REPEAT = 2;
    public static final int PASSWORD_FORMAT_ERROR = 3;

}
