package com.astatus.cornerandroid.http.okhttp;

import com.astatus.cornerandroid.message.UpChangeStateMsg;

import java.math.BigInteger;
import java.util.HashMap;

/**
 * Created by AstaTus on 2016/3/10.
 */
public class UpChangeStateCmd extends PostCmd<UpChangeStateMsg> {

    private static final String CMD_METHOD = "app/Up/ChangeState";

    static public UpChangeStateCmd create(CmdListener listener, BigInteger articleGuid){

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("articleGuid", articleGuid.toString());

        return new UpChangeStateCmd(listener, params);
    }

    public UpChangeStateCmd(CmdListener listener,
                            HashMap<String, String> params) {

        super(HttpDef.SERVER_HOST_URL, CMD_METHOD, listener, UpChangeStateMsg.class, params);
    }
}
