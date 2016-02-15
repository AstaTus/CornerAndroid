package com.astatus.cornerandroid.http;

import android.graphics.Bitmap;

import com.android.volley.Request;
import com.android.volley.Response;
import com.astatus.cornerandroid.message.SendMsg;

import java.io.IOException;
import java.io.InputStream;


/**
 * Created by AstaTus on 2016/2/13.
 */
public class SendCmd extends BaseMultipartCmd<SendMsg> {
    private static final String CMD_METHOD = "send";

    static public SendCmd create(Response.Listener response,
                                 Response.ErrorListener error,
                                 InputStream image,
                                 String text,
                                 String location) throws IOException {

        MultipartParam param = new MultipartParam();
        param.buildFilePart("image", image);
        param.buildTextPart("text", text);
        param.buildTextPart("location", location);

        return new SendCmd(response, error, param);
    }

    protected SendCmd(Response.Listener response, Response.ErrorListener error, MultipartParam param){
        super(HttpDef.SERVER_URL, CMD_METHOD, Request.Method.POST, response, SendMsg.class, error, param, false, true);
    }
}
