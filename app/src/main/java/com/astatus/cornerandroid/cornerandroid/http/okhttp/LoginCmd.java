package com.astatus.cornerandroid.cornerandroid.http.okhttp;

import com.astatus.cornerandroid.http.okhttp.*;
import com.astatus.cornerandroid.http.okhttp.CmdListener;
import com.astatus.cornerandroid.http.okhttp.HttpDef;
import com.astatus.cornerandroid.message.LoginMsg;

import java.util.HashMap;

/**
 * Created by AstaTus on 2016/2/17.
 */
public class LoginCmd extends com.astatus.cornerandroid.http.okhttp.PostCmd<LoginMsg> {

    private static final String CMD_METHOD = "app/login";

    static public LoginCmd create(com.astatus.cornerandroid.http.okhttp.CmdListener listener, String email, String password){

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("email", email);
        params.put("password", password);

        return new LoginCmd(listener, params);
    }

    public LoginCmd(CmdListener listener,
                    HashMap<String, String> params) {

        super(HttpDef.SERVER_HOST_URL, CMD_METHOD, listener, LoginMsg.class, params);
    }
}
