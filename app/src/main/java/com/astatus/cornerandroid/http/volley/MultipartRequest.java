package com.astatus.cornerandroid.http.volley;

/**
 * Created by AstaTus on 2016/2/13.
 */
import com.android.volley.AuthFailureError;
import com.android.volley.Response;

class MultipartRequest<T> extends BaseRequest<T> {

    private MultipartParam mParam;

    public MultipartRequest(String baseUrl, String url, int method, Response.Listener listener,
                         Class<T> responseClass, Response.ErrorListener errorListener,
                         MultipartParam param,
                         boolean addCookie, boolean checkCookie){

        super(baseUrl, url, method,
                listener, responseClass, errorListener, addCookie, checkCookie);

        mParam = param;

    }

    @Override
    public String getBodyContentType() {
        return mParam.getMimeType();
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        return mParam.getBody();
    }
}