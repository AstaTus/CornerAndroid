/**
 * Created by AstaTus on 2016/1/1.
 */

package com.astatus.cornerandroid.http;

import com.android.volley.Request;
import java.util.concurrent.ConcurrentHashMap;


public class RegisterCmd extends BaseJsonCmd{

    private static final String METHOND_URL = "register";

    private RegisterCmd(ConcurrentHashMap params){
        super(HttpDef.SERVER_URL, METHOND_URL, Request.Method.POST, params);
    }

    public static RegisterCmd create(String email, String password, String nickname, String sex, String birth){
        ConcurrentHashMap<String, String> params = new ConcurrentHashMap<String, String>();
        params.put("email", email);
        params.put("password", password);
        params.put("nickname", nickname);
        params.put("sex", sex);
        params.put("birth", birth);


        return new RegisterCmd(params);
    }
}
