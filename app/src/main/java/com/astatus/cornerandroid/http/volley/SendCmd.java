package com.astatus.cornerandroid.http.volley;

import com.android.volley.Request;
import com.android.volley.Response;
import com.astatus.cornerandroid.message.SendMsg;

import java.io.File;
import java.io.IOException;


/**
 * Created by AstaTus on 2016/2/13.
 */
public class SendCmd extends BaseMultipartCmd<SendMsg> {
    private static final String CMD_METHOD = "send";

    static public SendCmd create(Response.Listener response,
                                 Response.ErrorListener error,
                                 File image,
                                 String text,
                                 String location) throws IOException {

        NewMultipartParam param = new NewMultipartParam();
        param.addFilePart("image", image);
        param.addTextPart("text", text);
        param.addTextPart("location", location);

        return new SendCmd(response, error, param);
    }

    protected SendCmd(Response.Listener response, Response.ErrorListener error, NewMultipartParam param){
        super(HttpDef.SERVER_URL, CMD_METHOD, Request.Method.POST, response, SendMsg.class, error, param, false, true);
    }
}
