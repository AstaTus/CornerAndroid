package com.astatus.cornerandroid.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.opengl.GLES10;
import android.os.Build;
import android.view.Display;
import android.view.WindowManager;
import android.opengl.EGL14;
import android.opengl.EGLConfig;
import android.opengl.EGLContext;
import android.opengl.EGLDisplay;
import android.opengl.EGLSurface;
import android.opengl.GLES20;

import com.astatus.cornerandroid.application.CornerApplication;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by AstaTus on 2016/2/6.
 */
public class ImageUtil {
    /*只针对本地图片
    * */
    //最大  系统支持的最大大小
    public final static int SHOW_SYSTEM_MAX_IMAGE_SIZE = 0;
    //大 全部显示,根据屏幕的宽高进行缩放,屏幕宽边和图片的对应边缩放至一样
    public final static int SHOW_ALL_IMAGE_SIZE = 1;
    //中 在列表中显示，图片的最短边根据屏幕宽的1/3或者1/4
    public final static int SHOW_LIST_IMAGE_SIZE = 2;
    //小 缩略图 图片的最短边为100
    public final static int SHOW_SAMPLE_IMAGE_SIZE = 3;


    private static int sMaxTextureSize = 0;

    private static Point sScreenSize = null;
    private static Point sThirdScreenSize = null;
    public static int GetMaxTextureSize(){
        if (sMaxTextureSize == 0){

            int[] maxSize = new int[1];
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                EGLDisplay dpy = EGL14.eglGetDisplay(EGL14.EGL_DEFAULT_DISPLAY);
                int[] vers = new int[2];
                EGL14.eglInitialize(dpy, vers, 0, vers, 1);
                int[] configAttr = {
                        EGL14.EGL_COLOR_BUFFER_TYPE, EGL14.EGL_RGB_BUFFER,
                        EGL14.EGL_LEVEL, 0,
                        EGL14.EGL_RENDERABLE_TYPE, EGL14.EGL_OPENGL_ES2_BIT,
                        EGL14.EGL_SURFACE_TYPE, EGL14.EGL_PBUFFER_BIT,
                        EGL14.EGL_NONE
                };
                EGLConfig[] configs = new EGLConfig[1];
                int[] numConfig = new int[1];
                EGL14.eglChooseConfig(dpy, configAttr, 0,
                        configs, 0, 1, numConfig, 0);
                if (numConfig[0] == 0) {
                    // TROUBLE! No config found.
                }
                EGLConfig config = configs[0];

                int[] surfAttr = {
                        EGL14.EGL_WIDTH, 64,
                        EGL14.EGL_HEIGHT, 64,
                        EGL14.EGL_NONE
                };
                EGLSurface surf = EGL14.eglCreatePbufferSurface(dpy, config, surfAttr, 0);

                int[] ctxAttrib = {
                        EGL14.EGL_CONTEXT_CLIENT_VERSION, 2,
                        EGL14.EGL_NONE
                };
                EGLContext ctx = EGL14.eglCreateContext(dpy, config, EGL14.EGL_NO_CONTEXT, ctxAttrib, 0);
                EGL14.eglMakeCurrent(dpy, surf, surf, ctx);

                GLES20.glGetIntegerv(GLES20.GL_MAX_TEXTURE_SIZE, maxSize, 0);
                EGL14.eglMakeCurrent(dpy, EGL14.EGL_NO_SURFACE, EGL14.EGL_NO_SURFACE,
                        EGL14.EGL_NO_CONTEXT);
                EGL14.eglDestroySurface(dpy, surf);
                EGL14.eglDestroyContext(dpy, ctx);
                EGL14.eglTerminate(dpy);
            }else {
                GLES10.glGetIntegerv(GL10.GL_MAX_TEXTURE_SIZE, maxSize, 0);
            }

            sMaxTextureSize = maxSize[0];
        }

        return sMaxTextureSize;
    }

    public static Bitmap decodeBitmapFromFile(Uri uri, int type) throws IOException {
        Context context = CornerApplication.getSingleton().getBaseContext();
        InputStream is = context.getContentResolver().openInputStream(uri);
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeStream(is, null, options);
        is.close();
        options.inSampleSize = calculateInSampleSize(options, type);
        options.inJustDecodeBounds = false;
        is = context.getContentResolver().openInputStream(uri);
        bitmap = BitmapFactory.decodeStream(is, null, options);
        is.close();



        return bitmap;

        /*final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(uri, options);
        options.inSampleSize = calculateInSampleSize(options, type);
        options.inJustDecodeBounds = false;
        Bitmap src = BitmapFactory.decodeFile(uri, options);
        return src;*/
    }

    private static Bitmap createScaleBitmap(Bitmap src, int dstWidth, int dstHeight, int inSampleSize) {
        //如果inSampleSize是2的倍数，也就说这个src已经是我们想要的缩略图了，直接返回即可。
        if (inSampleSize % 2 == 0) {
            return src;
        }
        // 如果是放大图片，filter决定是否平滑，如果是缩小图片，filter无影响，我们这里是缩小图片，所以直接设置为false
        Bitmap dst = Bitmap.createScaledBitmap(src, dstWidth, dstHeight, false);
        if (src != dst) { // 如果没有缩放，那么不回收
            src.recycle(); // 释放Bitmap的native像素数组
        }
        return dst;
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int type) {
        // 源图片的高度和宽度
        final int height = options.outHeight;
        final int width = options.outWidth;
        switch (type){
            case SHOW_SYSTEM_MAX_IMAGE_SIZE:
                return getSystemMaxImageSampleSize(height, width);
            case SHOW_ALL_IMAGE_SIZE:
                return getShowAllImageSampleSize(height, width);
            case SHOW_SAMPLE_IMAGE_SIZE:
                //return get
        }

        return 1;

    }

    private static int getSystemMaxImageSampleSize(int height, int width){
        int maxEdge = getMaxEdge(height, width);
        int inSampleSize = 1;

        while (maxEdge / inSampleSize > GetMaxTextureSize()){
            inSampleSize *= 2;
        }

        return inSampleSize;
    }

    /*private static int getShowListImageSampleSize(int height, int width){

        if (sThirdScreenSize == null){
            Point size = getScreenSize();
            sThirdScreenSize.x = size.x / 3;
            sThirdScreenSize.y = size.y / 3;
        }


    }*/


    private static int getShowAllImageSampleSize(int height, int width){
        Point screenSize = getScreenSize();
        int inSampleSize = 1;
        while ((height / inSampleSize) > screenSize.y && (width / inSampleSize) > screenSize.x) {
            inSampleSize *= 2;
        }

        return inSampleSize;
    }

    private static Point getScreenSize(){
        if (sScreenSize == null){
            Context context = CornerApplication.getSingleton().getBaseContext();
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();

            sScreenSize = new Point();
            display.getSize(sScreenSize);
        }

        return sScreenSize;
    }

    private static int getMaxEdge(int height, int width){

        int max;
        if (height > width){
            max = height;
        }else{
            max = width;
        }
        return max;
    }
}
