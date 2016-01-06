/**
 * Created by AstaTus on 2016/1/1.
 */

package com.astatus.cornerandroid.http;

import com.android.volley.Request;
import com.android.volley.Response;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;


public class RegisterCmd extends BaseJsonCmd{

    private static final String CMD_METHOD = "register";


    static public RegisterCmd create(Response.Listener<JSONObject> response,
                                  Response.ErrorListener error, String email,
                                  String password){

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("email", email);
        params.put("password", password);

        return new RegisterCmd(response, error, params);
    }

    protected RegisterCmd(Response.Listener<JSONObject> response, Response.ErrorListener error, HashMap<String, String> params){
        super(HttpDef.SERVER_URL, CMD_METHOD, Request.Method.POST, response, error, params);
    }
}
