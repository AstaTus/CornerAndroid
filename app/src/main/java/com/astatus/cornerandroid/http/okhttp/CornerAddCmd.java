package com.astatus.cornerandroid.http.okhttp;

import com.astatus.cornerandroid.message.CornerAddMsg;

import java.util.HashMap;

/**
 * Created by AstaTus on 2016/3/16.
 */
public class CornerAddCmd extends PostCmd<CornerAddMsg> {
    private static final String CMD_METHOD = "app/Corner/Add";

    static public CornerAddCmd create(CmdListener listener, String name, String location){

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("name", name);
        params.put("location", location);

        return new CornerAddCmd(listener, params);
    }

    public CornerAddCmd(CmdListener listener,
                    HashMap<String, String> params) {

        super(HttpDef.SERVER_HOST_URL, CMD_METHOD, listener, CornerAddMsg.class, params);
    }
}


