package com.astatus.cornerandroid.http.volley;

import com.android.volley.Request;
import com.android.volley.Response;
import com.astatus.cornerandroid.message.LoginMsg;

import java.util.HashMap;

/**
 * Created by AstaTus on 2016/1/4.
 */
public class LoginCmd extends BaseJsonCmd<LoginMsg> {
    private static final String CMD_METHOD = "login";

    static public LoginCmd create(Response.Listener response,
                                  Response.ErrorListener error, String email,
                                  String password){

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("email", email);
        params.put("password", password);

        return new LoginCmd(response, error, params);
    }

    protected LoginCmd(Response.Listener response, Response.ErrorListener error, HashMap<String, String> params){
        super(HttpDef.SERVER_URL, CMD_METHOD, Request.Method.POST, response, LoginMsg.class, error, params, false, true);
    }
}
