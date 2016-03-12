package com.astatus.cornerandroid.http.okhttp;

import com.astatus.cornerandroid.message.CommentRemoveMsg;

import java.math.BigInteger;
import java.util.HashMap;

/**
 * Created by AstaTus on 2016/3/10.
 */
public class CommentRemoveCmd extends PostCmd<CommentRemoveMsg> {

    private static final String CMD_METHOD = "app/Comment/Delete";
    static public CommentRemoveCmd create(CmdListener listener,
                                       BigInteger commentGuid){

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("commentGuid", commentGuid.toString());

        return new CommentRemoveCmd(listener, params);
    }

    public CommentRemoveCmd(CmdListener listener, HashMap<String, String> params) {

        super(HttpDef.SERVER_HOST_URL, CMD_METHOD, listener, CommentRemoveMsg.class, params);
    }
}