package com.astatus.cornerandroid.util;

import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

/**
 * Created by AstaTus on 2016/2/8.
 */
public class PathUtil {
    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }
}
