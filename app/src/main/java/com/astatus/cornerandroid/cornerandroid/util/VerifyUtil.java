package com.astatus.cornerandroid.cornerandroid.util;

import android.text.TextUtils;

import java.util.regex.Pattern;

/**
 * Created by AstaTus on 2016/1/8.
 */
public class VerifyUtil {

    public final static boolean VerifyEmail(String target){
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public final static boolean VerifyPassword(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return Pattern.matches("^[a-zA-Z0-9]{6,16}$", target);
        }
    }

    public final static boolean VerifyNickname(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return true;
        }
    }

    public final static boolean VerifyBirthday(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return true;
        }
    }
}
