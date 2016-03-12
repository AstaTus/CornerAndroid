package com.astatus.cornerandroid.http.okhttp;

import com.astatus.cornerandroid.message.CommentAddMsg;

import java.math.BigInteger;
import java.util.HashMap;

/**
 * Created by AstaTus on 2016/3/10.
 */
public class CommentAddCmd extends PostCmd<CommentAddMsg> {

    private static final String CMD_METHOD = "app/Comment/Add";
    static public CommentAddCmd create(CmdListener listener,
                                       BigInteger articleGuid,
                                       BigInteger targetGuid,
                                       String text){

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("articleGuid", articleGuid.toString());
        params.put("targetGuid", targetGuid.toString());
        params.put("text", text);

        return new CommentAddCmd(listener, params);
    }

    public CommentAddCmd(CmdListener listener, HashMap<String, String> params) {

        super(HttpDef.SERVER_HOST_URL, CMD_METHOD, listener, CommentAddMsg.class, params);
    }
}
