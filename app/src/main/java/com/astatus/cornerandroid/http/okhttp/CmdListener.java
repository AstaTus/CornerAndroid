package com.astatus.cornerandroid.http.okhttp;

import com.astatus.cornerandroid.message.MessagePacket;

/**
 * Created by AstaTus on 2016/2/17.
 */
public interface CmdListener {
    public void onSuccess(MessagePacket result);
    public void onFailed();
}
