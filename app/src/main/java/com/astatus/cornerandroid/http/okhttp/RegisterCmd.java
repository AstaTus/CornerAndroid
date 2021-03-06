/**
 * Created by AstaTus on 2016/1/1.
 */

package com.astatus.cornerandroid.http.okhttp;

import com.astatus.cornerandroid.message.RegisterMsg;

import java.io.IOException;
import java.util.HashMap;


public class RegisterCmd extends PostCmd<RegisterMsg>{

    private static final String CMD_METHOD = "app/Register";

    static public RegisterCmd create(CmdListener listener,
                                    String email, String password,
                                    String nickname, String birthday,
                                    int sex) throws IOException {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("email", email);
        params.put("password", password);
        params.put("nickname", nickname);
        params.put("birthday", birthday);
        params.put("sex", String.valueOf(sex));

        return new RegisterCmd(listener, params);
    }

    public RegisterCmd(CmdListener listener, HashMap<String, String> params) {
        super(HttpDef.SERVER_HOST_URL, CMD_METHOD, listener, RegisterMsg.class, params);
    }
}
