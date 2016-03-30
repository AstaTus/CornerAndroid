package com.astatus.cornerandroid.http.okhttp;

import com.astatus.cornerandroid.message.ArticleMsg;
import com.google.gson.Gson;

import java.math.BigInteger;
import java.util.List;

import okhttp3.HttpUrl;

/**
 * Created by AstaTus on 2016/3/24.
 */
public class ArticleHomeObtainCmd extends GetCmd<ArticleMsg>{
    public static final int REQUEST_ARTICLE_MAX_COUNT = 3;
    public static final int REQUEST_TYPE_PERSONAL = 1;
    public static final int REQUEST_DIRECTION_UP = 1;
    public static final int REQUEST_DIRECTION_DOWN = 2;


    private static final String CMD_METHOD = "app/Article/Obtain/Home";


    //上拉 获取所有的项,如果超过时间间隔-时间间隔服务器定,则本地数据要清空,
    static public ArticleHomeObtainCmd create(CmdListener listener, List<BigInteger> cornerGuids,
                                              BigInteger articleGuid, int direction){

        try {

            Gson gson = new Gson();
            String cornerGuidsJson = gson.toJson(cornerGuids);
            HttpUrl url = HttpUrl.parse(HttpDef.SERVER_HOST_URL + CMD_METHOD)
                    .newBuilder()
                    .addQueryParameter("cornerGuids", cornerGuidsJson)
                    .addQueryParameter("articleGuid", articleGuid.toString())
                    .addQueryParameter("direction", String.valueOf(direction))
                    .build();

            return new ArticleHomeObtainCmd(url, listener);
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public ArticleHomeObtainCmd(HttpUrl url, CmdListener listener) {
        super(url, listener, ArticleMsg.class);
    }
}
