package com.astatus.cornerandroid.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.astatus.cornerandroid.R;
import com.astatus.cornerandroid.util.ImageUtil;

public class SendActivity extends AppCompatActivity {

    private ImageView mImageView;
    private TextView mLocationView;
    private EditText mEditText;

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

        try {
            Intent intent = getIntent();
            String uriStr = intent.getStringExtra("uri");
            Uri uri = Uri.parse(uriStr);
            Bitmap image = ImageUtil.decodeBitmapFromFile(uri, ImageUtil.SHOW_ALL_IMAGE_SIZE);
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


                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
