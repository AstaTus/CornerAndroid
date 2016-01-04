package com.astatus.cornerandroid.http;

import com.android.volley.Request;
import com.android.volley.Response;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by AstaTus on 2016/1/4.
 */
public class LoginCmd extends BaseJsonCmd {
    private static final String CMD_METHOD = "app/login";

    static public LoginCmd create(Response.Listener<JSONObject> response,
                                  Response.ErrorListener error, String email,
                                  String password){

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("email", email);
        params.put("password", password);

        return new LoginCmd(response, error, params);
    }

    protected LoginCmd(Response.Listener<JSONObject> response, Response.ErrorListener error, HashMap<String, String> params){
        super(HttpDef.SERVER_URL, CMD_METHOD, Request.Method.POST, response, error, params);
    }
}
