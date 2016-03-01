package com.astatus.cornerandroid.cornerandroid.message;

/**
 * Created by AstaTus on 2016/1/11.
 */
public class MessagePacket<T> {

    ///result type
    public static final boolean RESULT_FAILED = false;
    public static final boolean RESULT_SUCCESS = true;

    ///result_code type
    public static final int RESULT_CODE_UNDEFINED = 0;
    public static final int RESULT_CODE_USER_NOT_VERIFY = 1;
    public static final int RESULT_CODE_SERVER_SESSION_ERROR = 2;

    public boolean result = RESULT_SUCCESS;
    public int resultCode = RESULT_CODE_UNDEFINED;
    public T msg = null;
}