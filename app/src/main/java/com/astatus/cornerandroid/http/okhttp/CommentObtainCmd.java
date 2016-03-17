package com.astatus.cornerandroid.http.okhttp;

import com.astatus.cornerandroid.message.CommentObtainMsg;

import java.math.BigInteger;

import okhttp3.HttpUrl;

/**
 * Created by AstaTus on 2016/3/10.
 */
public class CommentObtainCmd extends GetCmd<CommentObtainMsg> {

    private static final String CMD_METHOD = "app/Comment/Obtain";

    public static final int REQUEST_COMMENT_MAX_COUNT = 15;

    public static final int REQUEST_DIRECTION_UP = 1;
    public static final int REQUEST_DIRECTION_DOWN = 2;

    //上拉 获取所有的项,如果超过时间间隔-时间间隔服务器定,则本地数据要清空,
    static public CommentObtainCmd create(CmdListener listener, BigInteger articleGuid, BigInteger commentGuid, int direction){

        try {
            HttpUrl url = HttpUrl.parse(HttpDef.SERVER_HOST_URL + CMD_METHOD)
                    .newBuilder()
                    .addQueryParameter("articleGuid", articleGuid.toString())
                    .addQueryParameter("direction", String.valueOf(direction))
                    .addQueryParameter("commentGuid", commentGuid.toString())
                    .build();

            return new CommentObtainCmd(url, listener);
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public CommentObtainCmd(HttpUrl url, CmdListener listener) {
        super(url, listener, CommentObtainMsg.class);
    }
}
