package com.astatus.cornerandroid.http.okhttp;

import com.android.volley.toolbox.HttpHeaderParser;
import com.astatus.cornerandroid.message.MessagePacket;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by AstaTus on 2016/2/17.
 */
public class CommonCallback<T> implements Callback {
    private CmdListener mListener;
    private final Class<T> mResponseClass;
    protected static final Gson mGson = new Gson();

    public CommonCallback(CmdListener listener, Class<T> responseClass){

        mResponseClass = responseClass;
    }
    @Override
    public void onFailure(Call call, IOException e) {

        mListener.onFailed();
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {

        String jsonString = response.body().toString();

        MessagePacket packet = new MessagePacket<T>();
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(jsonString);
        JsonObject obj = element.getAsJsonObject();
        packet.result = obj.get("result").getAsBoolean();
        packet.resultCode = obj.get("resultCode").getAsInt();
        packet.msg = mGson.fromJson(obj.get("msg"), mResponseClass);

        mListener.onSuccess(packet);
    }
}
