package com.astatus.cornerandroid.util;

/**
 * Created by AstaTus on 2016/2/1.
 */
public class NumberUtil {
    public static boolean IsFloatEqual(float val1, float val2){
        final float error = 0.000001f * val2;
        if ((val1 >= val2 - error) || (val1 <= val2 + error)) {
            return true;
        }

        return false;
    }
}
