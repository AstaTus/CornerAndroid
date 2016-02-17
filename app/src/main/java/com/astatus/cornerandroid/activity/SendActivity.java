package com.astatus.cornerandroid.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.astatus.cornerandroid.R;
import com.astatus.cornerandroid.http.volley.SendCmd;
import com.astatus.cornerandroid.message.SendMsg;
import com.astatus.cornerandroid.util.ImageUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class SendActivity extends AppCompatActivity {
    private ImageView mImageView;
    private TextView mLocationView;
    private EditText mEditText;

    private Uri mUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
        initView();
    }

    private void initView(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.publish_toolbar);
        setSupportActionBar(toolbar);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar actionBar = getSupportActionBar();
        // Enable the Up button
        actionBar.setDisplayHomeAsUpEnabled(true);

        mImageView = (ImageView)findViewById(R.id.send_imageview);
        mLocationView = (TextView)findViewById(R.id.send_location_text);
        mEditText = (EditText)findViewById(R.id.send_edit_text);
        try {
            Intent intent = getIntent();
            String uriStr = intent.getStringExtra("uri");
            mUri = Uri.parse(uriStr);
            Bitmap image = ImageUtil.decodeBitmapFromFile(mUri, ImageUtil.SHOW_ALL_IMAGE_SIZE);
            mImageView.setImageBitmap(image);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_send_actionbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.send_action_finish:

                try {
                    File image = new File(mUri.getPath());
                    SendCmd cmd = SendCmd.create(new SendResponseListener(),
                            new SendErrorListener(), image,
                            mEditText.getText().toString(),
                            mLocationView.getText().toString());

                    /*InputStream image = getContentResolver().openInputStream(mUri);
                    SendCmd cmd = SendCmd.create(new SendResponseListener(),
                            new SendErrorListener(), image,
                            mEditText.getText().toString(),
                            mLocationView.getText().toString());*/
                    cmd.excute();

                } catch (FileNotFoundException e) {
                    //文件不存在
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }catch (Exception e){
                    e.printStackTrace();
                }

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class SendResponseListener implements Response.Listener<SendMsg>{
        @Override
        public void onResponse(SendMsg msg) {

            if (msg.code == 0){
                Log.i("test", "send ok");
            }else{
                Log.i("test", "send failed");
            }
        }
    }

    class SendErrorListener implements Response.ErrorListener{
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.i("test", "send error");
        }
    }
}
