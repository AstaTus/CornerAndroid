package com.astatus.cornerandroid.http.okhttp;

import com.astatus.cornerandroid.message.ArticleMsg;
import com.astatus.cornerandroid.message.HomeMsg;

import java.math.BigInteger;
import java.util.ArrayList;

import okhttp3.HttpUrl;

/**
 * Created by AstaTus on 2016/3/30.
 */
public class HomeCmd extends GetCmd<HomeMsg>{

    private static final String CMD_METHOD = "app/Home";

    static public HomeCmd create(CmdListener listener, ArrayList<BigInteger> locationGuids, int direction){

        try {
            HttpUrl.Builder builder = HttpUrl.parse(HttpDef.SERVER_HOST_URL + CMD_METHOD)
                    .newBuilder();

            builder.addQueryParameter("direction", String.valueOf(direction));

            for (int i = 0; i < locationGuids.size(); ++i){
                builder.addQueryParameter("locationGuids", locationGuids.get(i).toString());
            }
            HttpUrl url = builder.build();

            return new HomeCmd(url, listener);
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public HomeCmd(HttpUrl url, CmdListener listener) {
        super(url, listener, HomeMsg.class);
    }
}
