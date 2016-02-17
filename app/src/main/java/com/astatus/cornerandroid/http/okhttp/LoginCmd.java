package com.astatus.cornerandroid.http.okhttp;

import com.astatus.cornerandroid.message.LoginMsg;
import com.astatus.cornerandroid.message.MessagePacket;

import java.util.HashMap;

/**
 * Created by AstaTus on 2016/2/17.
 */
public class LoginCmd extends PostCmd<LoginMsg> {

    private static final String CMD_METHOD = "login";

    static public LoginCmd create(CmdListener listener, String email, String password){

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("email", email);
        params.put("password", password);

        return new LoginCmd(listener, params);
    }

    public LoginCmd(CmdListener listener,
                    HashMap<String, String> params) {

        super(HttpDef.SERVER_URL, CMD_METHOD, listener, LoginMsg.class, params);
    }
}
