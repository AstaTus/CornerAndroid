package com.astatus.cornerandroid.cornerandroid.util;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.astatus.cornerandroid.application.CornerApplication;

/**
 * Created by AstaTus on 2016/2/8.
 */
public class PathUtil {

    public static String ConvertUriToPath(Uri uri, String type)
    {
        String[] projection = { type };
        ContentResolver resolver = CornerApplication.getSingleton().getContentResolver();
        Cursor cursor = resolver.query(uri, projection, null, null, null);
        if (cursor == null)
            return "";
        int column_index = cursor.getColumnIndexOrThrow(type);
        cursor.moveToFirst();
        String s=cursor.getString(column_index);
        cursor.close();
        return s;
    }
}
