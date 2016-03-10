package com.astatus.cornerandroid.http.okhttp;

import com.astatus.cornerandroid.message.ArticleMsg;

import java.math.BigInteger;
import java.util.Date;

import okhttp3.HttpUrl;

/**
 * Created by AstaTus on 2016/2/24.
 */
public class ArticleCmd extends GetCmd<ArticleMsg>{

    public static final int REQUEST_ARTICLE_MAX_COUNT = 3;

    public static final int REQUEST_TYPE_PERSONAL = 1;
    public static final int REQUEST_DIRECTION_UP = 1;
    public static final int REQUEST_DIRECTION_DOWN = 2;


    private static final String CMD_METHOD = "app/article";


    //上拉 获取所有的项,如果超过时间间隔-时间间隔服务器定,则本地数据要清空,
    static public ArticleCmd create(CmdListener listener, BigInteger articleUserGuid, BigInteger articleGuid, int type, int direction){

        try {
            HttpUrl url = HttpUrl.parse(HttpDef.SERVER_HOST_URL + CMD_METHOD)
                    .newBuilder()
                    .addQueryParameter("articleUserGuid", articleUserGuid.toString())
                    .addQueryParameter("articleGuid", articleGuid.toString())
                    .addQueryParameter("type", String.valueOf(type))
                    .addQueryParameter("direction", String.valueOf(direction))
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
