package com.astatus.cornerandroid.cornerandroid.http.okhttp;

import com.astatus.cornerandroid.http.okhttp.*;
import com.astatus.cornerandroid.http.okhttp.CmdListener;
import com.astatus.cornerandroid.http.okhttp.HttpDef;
import com.astatus.cornerandroid.message.ArticleMsg;

import java.util.Date;

import okhttp3.HttpUrl;

/**
 * Created by AstaTus on 2016/2/24.
 */
public class ArticleCmd extends com.astatus.cornerandroid.http.okhttp.GetCmd<ArticleMsg> {

    public static final int REQUEST_TYPE_PERSONAL = 1;
    public static final int REQUEST_DIRECTION_UP = 1;
    public static final int REQUEST_DIRECTION_DOWN = 2;


    private static final String CMD_METHOD = "app/article";


    //上拉 获取所有的项,如果超过时间间隔-时间间隔服务器定,则本地数据要清空,
    static public ArticleCmd create(com.astatus.cornerandroid.http.okhttp.CmdListener listener, int type, int direction, Date time){

        try {
            HttpUrl url = HttpUrl.parse(HttpDef.SERVER_HOST_URL + CMD_METHOD)
                    .newBuilder()
                    .addQueryParameter("type", String.valueOf(type))
                    .addQueryParameter("direction", String.valueOf(direction))
                    .addQueryParameter("time", time == null ? "" : time.toString())
                    .build();

            return new ArticleCmd(url, listener);
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public ArticleCmd(HttpUrl url, CmdListener listener) {
        super(url, listener, ArticleMsg.class);
    }
}
