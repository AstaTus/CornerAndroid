package com.astatus.cornerandroid.http.okhttp;

import com.astatus.cornerandroid.message.PublishMsg;

import java.io.File;
import java.io.IOException;

/**
 * Created by AstaTus on 2016/2/17.
 */
public class PublishCmd extends MultipartCmd<PublishMsg>{
    private static final String CMD_METHOD = "app/Publish";

    static public PublishCmd create(CmdListener listener,
                                 File image,
                                 String text,
                                 String location) throws IOException {

        MultipartParam param = new MultipartParam();
        param.addFilePart("test", image);
        param.addParamPart("text", text);
        param.addParamPart("location", location);

        return new PublishCmd(listener, param);
    }

    public PublishCmd(CmdListener listener, MultipartParam param) {
        super(HttpDef.SERVER_HOST_URL, CMD_METHOD, listener, PublishMsg.class, param);
    }
}
