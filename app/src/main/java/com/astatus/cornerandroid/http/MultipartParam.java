package com.astatus.cornerandroid.http;

import android.graphics.Bitmap;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by AstaTus on 2016/2/14.
 */
public class MultipartParam {
    private static final String TWO_HYPHENS = "--";
    private static final String LINE_END = "\r\n";
    ///分界字符
    private static final String BOUNDARY = "apiclient-" + System.currentTimeMillis();
    private static final String MIME_TYPE = "multipart/form-data;boundary=" + BOUNDARY;

    private ByteArrayOutputStream mbyteArrOS = new ByteArrayOutputStream();
    private DataOutputStream mdataOS = new DataOutputStream(mbyteArrOS);

    public void buildImagePart(String key, Bitmap bitmap) throws IOException {
        byte[] imageData = compressBitmap(bitmap);
        buildFilePart(key, imageData);
    }

    public void buildFilePart(String key, InputStream stream) throws IOException {
        mdataOS.writeBytes(TWO_HYPHENS + BOUNDARY + LINE_END);
        mdataOS.writeBytes("Content-Disposition: form-data; name=\"" + key + "\"" + LINE_END);
        mdataOS.writeBytes("Content-Type: image/jpeg;" + LINE_END);
        mdataOS.writeBytes(LINE_END);

        int bytesAvailable = stream.available();

        int maxBufferSize = 1024 * 1024;
        int bufferSize = Math.min(bytesAvailable, maxBufferSize);
        byte[] buffer = new byte[bufferSize];

        // read file and write it into form...
        int bytesRead = stream.read(buffer, 0, bufferSize);

        while (bytesRead > 0) {
            mdataOS.write(buffer, 0, bufferSize);
            bytesAvailable = stream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            bytesRead = stream.read(buffer, 0, bufferSize);
        }

        mdataOS.writeBytes(LINE_END);
    }

    public void buildFilePart(String key, byte[] fileData) throws IOException {
        mdataOS.writeBytes(TWO_HYPHENS + BOUNDARY + LINE_END);
        mdataOS.writeBytes("Content-Disposition: form-data; name=\"" + key + "\"" + LINE_END);
        mdataOS.writeBytes("Content-Type: image/jpeg;" + LINE_END);
        mdataOS.writeBytes(LINE_END);

        ByteArrayInputStream fileInputStream = new ByteArrayInputStream(fileData);
        int bytesAvailable = fileInputStream.available();

        int maxBufferSize = 1024 * 1024;
        int bufferSize = Math.min(bytesAvailable, maxBufferSize);
        byte[] buffer = new byte[bufferSize];

        // read file and write it into form...
        int bytesRead = fileInputStream.read(buffer, 0, bufferSize);

        while (bytesRead > 0) {
            mdataOS.write(buffer, 0, bufferSize);
            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);
        }

        mdataOS.writeBytes(LINE_END);
    }

    public void buildTextPart(String key, String value) throws IOException {
        mdataOS.writeBytes(TWO_HYPHENS + BOUNDARY + LINE_END);
        mdataOS.writeBytes("Content-Disposition: form-data; name=\"" + key + "\"" + LINE_END);
        mdataOS.writeBytes("Content-Type: text/plain; charset=UTF-8" + LINE_END);
        mdataOS.writeBytes(LINE_END);
        mdataOS.writeBytes(value + LINE_END);
    }


    private byte[] compressBitmap(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public String getMimeType(){
        return MIME_TYPE;
    }

    public byte[] getBody(){
        return mbyteArrOS.toByteArray();
    }
}
