package com.astatus.cornerandroid.http.okhttp;

import com.astatus.cornerandroid.message.MessagePacket;

/**
 * Created by AstaTus on 2016/2/17.
 */
public interface CmdListener<T> {
    public void onSuccess(T result);
    public void onFailed();
    public void onResponseFailed(int code);
}
