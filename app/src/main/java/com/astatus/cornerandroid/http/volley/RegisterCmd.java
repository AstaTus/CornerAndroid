/**
 * Created by AstaTus on 2016/1/1.
 */

package com.astatus.cornerandroid.http.volley;

import com.android.volley.Request;
import com.android.volley.Response;
import com.astatus.cornerandroid.message.RegisterMsg;

import java.util.HashMap;


public class RegisterCmd extends BaseJsonCmd<RegisterMsg>{

    private static final String CMD_METHOD = "register";


    static public RegisterCmd create(Response.Listener response,
                                  Response.ErrorListener error, String email,
                                  String password, String nickname, String birthday,
                                  int sex){

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("email", email);
        params.put("password", password);
        params.put("nickname", nickname);
        params.put("birthday", birthday);
        params.put("sex", String.valueOf(sex));

        return new RegisterCmd(response, error, params);
    }

    protected RegisterCmd(Response.Listener response, Response.ErrorListener error, HashMap<String, String> params){
        super(HttpDef.SERVER_URL, CMD_METHOD, Request.Method.POST, response, RegisterMsg.class, error, params, false, true);
    }
}
