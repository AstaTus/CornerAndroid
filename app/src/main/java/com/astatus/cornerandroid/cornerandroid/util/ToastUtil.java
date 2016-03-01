package com.astatus.cornerandroid.cornerandroid.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by AstaTus on 2016/1/7.
 */
public class ToastUtil {

    public static void showText(Context context, String text){
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
}
