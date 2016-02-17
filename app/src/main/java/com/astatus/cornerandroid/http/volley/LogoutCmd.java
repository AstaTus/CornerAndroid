package com.astatus.cornerandroid.http.volley;

import com.android.volley.Request;
import com.android.volley.Response;
import com.astatus.cornerandroid.message.LogoutMsg;

import java.util.HashMap;

/**
 * Created by AstaTus on 2016/1/12.
 */
public class LogoutCmd extends BaseJsonCmd<LogoutMsg> {
    private static final String CMD_METHOD = "logout";

    static public LogoutCmd create(Response.Listener response,
                                  Response.ErrorListener error){

        return new LogoutCmd(response, error, new HashMap<String, String>());
    }

    protected LogoutCmd(Response.Listener response, Response.ErrorListener error, HashMap<String, String> params){
        super(HttpDef.SERVER_URL, CMD_METHOD, Request.Method.POST, response, LogoutMsg.class, error, params);
    }
}
