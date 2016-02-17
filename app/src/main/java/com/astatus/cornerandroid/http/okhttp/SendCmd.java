package com.astatus.cornerandroid.http.okhttp;

import com.android.volley.Response;
import com.astatus.cornerandroid.http.okhttp.MultipartParam;
import com.astatus.cornerandroid.message.SendMsg;

import java.io.File;
import java.io.IOException;

/**
 * Created by AstaTus on 2016/2/17.
 */
public class SendCmd extends MultipartCmd<SendMsg>{
    private static final String CMD_METHOD = "send";

    static public SendCmd create(CmdListener listener,
                                 File image,
                                 String text,
                                 String location) throws IOException {

        MultipartParam param = new MultipartParam();
        param.addFilePart("test", image);
        param.addParamPart("text", text);
        param.addParamPart("location", location);

        return new SendCmd(listener, param);
    }

    public SendCmd(CmdListener listener, MultipartParam param) {
        super(HttpDef.SERVER_URL, CMD_METHOD, listener, SendMsg.class, param);
    }
}
