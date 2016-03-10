package com.astatus.cornerandroid.http.okhttp;

import com.astatus.cornerandroid.message.CommentDeleteMsg;

import java.math.BigInteger;
import java.util.HashMap;

/**
 * Created by AstaTus on 2016/3/10.
 */
public class CommentDeleteCmd extends PostCmd<CommentDeleteMsg> {

    private static final String CMD_METHOD = "app/Comment/Delete";
    static public CommentDeleteCmd create(CmdListener listener,
                                       BigInteger commentGuid){

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("commentGuid", commentGuid.toString());

        return new CommentDeleteCmd(listener, params);
    }

    public CommentDeleteCmd(CmdListener listener, HashMap<String, String> params) {

        super(HttpDef.SERVER_HOST_URL, CMD_METHOD, listener, CommentDeleteMsg.class, params);
    }
}