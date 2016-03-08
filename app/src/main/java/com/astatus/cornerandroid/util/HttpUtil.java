package com.astatus.cornerandroid.util;

import com.astatus.cornerandroid.BuildConfig;

/**
 * Created by AstaTus on 2016/3/7.
 */
public class HttpUtil {

    public static final int IMAGE_TYPE_DETAIL = 1;
    public static final int IMAGE_TYPE_PREVIEW = 2;

    public static String getImageUrl(String path, int imageType){
        return BuildConfig.IMAGE_ADDRESS + path
                + (imageType == IMAGE_TYPE_DETAIL ? "@!image_detail" : "@!image_preview");

    }
}
